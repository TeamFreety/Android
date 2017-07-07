package com.sopt.freety.freety.view.wirte;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.OnlyMsgResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.util.DateParser;
import com.sopt.freety.freety.util.util.FormatChecker;
import com.sopt.freety.freety.view.property.ScreenClickable;
import com.sopt.freety.freety.view.recruit.MapPopupActivity;
import com.sopt.freety.freety.view.wirte.data.NaverResultData;
import com.sopt.freety.freety.view.wirte.data.WritePostResultData;
import com.sopt.freety.freety.view.wirte.data.WriteRequestData;
import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity implements ScreenClickable {

    private static final String TAG = "WriteActivity";

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(WriteActivity.this);
            intent.setMaxSelectCount(5);
            intent.setSelectCheckBox(true);
            intent.setMaxGrideItemCount(3);
            startActivityForResult(intent, Consts.WRITE_PICTURE_CODE);
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        }
    };

    @OnClick({R.id.write_type_btn1, R.id.write_type_btn2, R.id.write_type_btn3, R.id.write_type_btn4})
    public void onHairTypeClick(Button button) {
        String hairTypeString = button.getText().toString();
        if (hairTypeSet.contains(hairTypeString)) {
            hairTypeSet.remove(hairTypeString);
            button.setTextColor(Color.parseColor("#95989A"));
            button.setBackgroundResource(R.drawable.hair_type_btn_unselected);
        } else {
            hairTypeSet.add(hairTypeString);
            button.setTextColor(Color.WHITE);
            button.setBackgroundResource(R.drawable.hair_type_btn_selected);
        }
    }

    @BindView(R.id.write_date_btn)
    ImageButton writeDateBtn;

    @OnClick(R.id.write_date_btn)
    public void onDateBtn() {
        new DatePickerDialog(WriteActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @BindView(R.id.write_date_text)
    TextView writeDateText;

    @OnClick(R.id.write_map_search_btn)
    public void onMapSearchBtn() {
        if (!isPopup) {
            isPopup = true;
            startActivityForResult(new Intent(WriteActivity.this, MapPopupActivity.class), Consts.MAP_POPUP_CODE);
            AppController.getInstance().pushPageStack();
        }
    }

    @OnClick(R.id.write_picture_btn)
    public void onPictureBtn() {
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 외부 저장소에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
                .check();
    }

    @BindViews({R.id.write_place_text1, R.id.write_place_text2})
    List<EditText> placeTextList;

    @BindViews({R.id.img_write_selected_first, R.id.img_write_selected_second, R.id.img_write_selected_third, R.id.img_write_selected_fourth, R.id.img_write_selected_fifth})
    List<ImageView> writeSelectedImageList;

    @BindView(R.id.edit_write_cost)
    EditText priceEditText;

    @BindView(R.id.edit_write_title)
    EditText writeTitleEdit;

    @BindView(R.id.edit_write_content)
    EditText writeContentEdit;

    @BindView(R.id.btn_write_register)
    Button writeRegisterBtn;

    @BindView(R.id.write_content_counter_text)
    TextView contentCounterText;

    @OnClick(R.id.btn_write_back)
    public void onBackBtn() {
        onBackPressed();
    }

    private Set<String> hairTypeSet = new HashSet<>();
    private GregorianCalendar calendar = new GregorianCalendar();
    private boolean isPopup;
    private ProgressDialog progressDialog;
    private List<MultipartBody.Part> imageBodyList = new ArrayList<>();
    private LatLng latLng;
    private String sido;
    private String sigugun;
    private String fullAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);
        isPopup = false;
        writeContentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contentCounterText.setText(String.format("%d/300", s.length()));
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        progressDialog = new ProgressDialog(WriteActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("업로드 중 입니다...");
        progressDialog.setIndeterminate(true);
    }

    @Override
    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(writeTitleEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(writeContentEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(placeTextList.get(0).getWindowToken(), 0);
        imm.hideSoftInputFromWindow(placeTextList.get(1).getWindowToken(), 0);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            writeDateText.setText(String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth));
            new TimePickerDialog(WriteActivity.this, timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String dateString = writeDateText.getText().toString();
            String viewString;
            if (hourOfDay > 12) {
                viewString = dateString + String.format(" 오후 %d시", hourOfDay - 12);
            } else {
                viewString = dateString + String.format(" 오후 %d시", hourOfDay);
            }
            writeDateText.setText(viewString);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isPopup = false;
        if (requestCode == Consts.MAP_POPUP_CODE) {
            if (resultCode == RESULT_OK) {
                String addressString = data.getStringExtra("address");
                double lat = data.getDoubleExtra("lat", 0);
                double lng = data.getDoubleExtra("lng", 0);
                latLng = new LatLng(lat, lng);
                int subIndex;

                if (addressString == null) {
                    return;
                }

                for (subIndex = 0; subIndex < addressString.length(); subIndex++) {
                    if (subIndex >= 8 && addressString.charAt(subIndex) == ' ') {
                        break;
                    }
                }

                placeTextList.get(0).setText(addressString.substring(0, subIndex));
                placeTextList.get(1).setText(addressString.substring(subIndex));

                Call<NaverResultData> call = AppController.getInstance().getNaverNetworkService()
                        .getSigugun(String.valueOf(lng) + "," + String.valueOf(lat));
                call.enqueue(new Callback<NaverResultData>() {
                    @Override
                    public void onResponse(Call<NaverResultData> call, Response<NaverResultData> response) {
                        if (response.isSuccessful()) {
                            sido = response.body().getSido();
                            sigugun = response.body().getSigugun();
                            fullAddress = response.body().getFullAddress();
                        } else {
                            Log.i(TAG, "onResponse: 포맷이 다름: " + sigugun);
                        }
                    }

                    @Override
                    public void onFailure(Call<NaverResultData> call, Throwable t) {
                        Log.i(TAG, "onResponse: 전송실패");
                    }
                });
            }
        } else if (requestCode == Consts.WRITE_PICTURE_CODE){
            List<String> photos = null;
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    for (int i = 0; i < writeSelectedImageList.size(); i++) {
                        writeSelectedImageList.get(i).setImageResource(0);
                    }
                    imageBodyList.clear();
                    for (int i = 0; i < photos.size(); i++) {
                        Log.i(TAG, "onActivityResult: " + photos.get(i));
                        File file = new File(photos.get(i));
                        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), fileBody);
                        imageBodyList.add(body);
                        Glide.with(WriteActivity.this).load(photos.get(i)).override(100, 100).thumbnail(0.3f).into(writeSelectedImageList.get(i));
                    }
                }
            }
        }
    }

    private boolean checkPermission() {
        if (hairTypeSet.size() <= 0) {
            Toast.makeText(this, "시술 유형을 최소 1가지 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (writeDateText.getText().toString().length() <= 0) {
            Toast.makeText(this, "시술 시간을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (placeTextList.get(0).getText().toString().length() <= 0) {
            Toast.makeText(this, "시술 시간을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (priceEditText.getText().length() <= 0 || !FormatChecker.isDecimal(priceEditText.getText().toString())) {
            Toast.makeText(this, "가격을 설정하세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (imageBodyList.size() <= 0) {
            Toast.makeText(this, "사진을 한 장 이상 등록해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (writeTitleEdit.getText().toString().length() <= 0) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (writeContentEdit.getText().toString().length() <= 0) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

       @OnClick(R.id.btn_write_register)
    public void onRegisterBtn() {
        if (checkPermission()) {
            progressDialog.show();
            WriteRequestData writeRequestData = new WriteRequestData
                    .Builder(writeTitleEdit.getText().toString(), writeContentEdit.getText().toString(), DateParser.toDateTimeFormat(writeDateText.getText().toString()))
                    .setPrice(Integer.parseInt(priceEditText.getText().toString()))
                    .setTypeCut(hairTypeSet.contains(Consts.HAIR_CUT))
                    .setTypeDye(hairTypeSet.contains(Consts.HAIR_DYE))
                    .setTypePerm(hairTypeSet.contains(Consts.HAIR_PERM))
                    .setTypeEct(hairTypeSet.contains(Consts.HAIR_ECT))
                    .setLatitude(latLng.latitude)
                    .setLongitude(latLng.longitude)
                    .setSigugun(sigugun)
                    .setSido(sido)
                    .setFullAddress(fullAddress)
                    .build();

            final NetworkService networkService = AppController.getInstance().getNetworkService();
            Call<WritePostResultData> call = networkService.writePostData(SharedAccessor.getToken(WriteActivity.this), writeRequestData);
            call.enqueue(new Callback<WritePostResultData>() {
                @Override
                public void onResponse(Call<WritePostResultData> call, Response<WritePostResultData> response) {
                    if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                        final int postId = response.body().getPostId();
                        final AtomicInteger imageCounter = new AtomicInteger(0);
                        for (int i = 0; i < imageBodyList.size(); i++) {
                            RequestBody postBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(postId));
                            Call<OnlyMsgResultData> photoCall = networkService.uploadPhoto(SharedAccessor.getToken(WriteActivity.this),
                                    postBody, imageBodyList.get(i));
                            Log.i(TAG, "onResponse: " + String.valueOf(i) + " 번째 전송" );
                            photoCall.enqueue(new Callback<OnlyMsgResultData>() {
                                @Override
                                public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                                    if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                                        int currentUploadedCount = imageCounter.incrementAndGet();
                                        Log.i(TAG, "onResponse: current uploaded count / total size : (" + currentUploadedCount + ", " + imageBodyList.size() + ")");
                                        progressDialog.setProgress(currentUploadedCount * (100 / imageBodyList.size()));
                                        if (currentUploadedCount == imageBodyList.size()) {
                                            progressDialog.dismiss();
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(WriteActivity.this, "사진 전송 오류", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {
                                    Toast.makeText(WriteActivity.this, "사진 전송 오류 failure", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(WriteActivity.this, "만들기 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WritePostResultData> call, Throwable t) {
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        int result = AppController.getInstance().popPageStack();
        if (result == 0) {
            Toast.makeText(this, "한 번 더 터치하시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }  else if (result < 0) {
            ActivityCompat.finishAffinity(this);
        } else {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
    }
}
