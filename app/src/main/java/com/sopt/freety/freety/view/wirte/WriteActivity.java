package com.sopt.freety.freety.view.wirte;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.property.ScreenClickable;
import com.sopt.freety.freety.view.recruit.MapPopupActivity;
import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteActivity extends AppCompatActivity implements ScreenClickable {

    public static final int MAP_POPUP_CODE = 1234;
    public static final int PICTURE_CODE = 4321;

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(WriteActivity.this);
            intent.setMaxSelectCount(5);
            intent.setSelectCheckBox(true);
            intent.setMaxGrideItemCount(0);
            startActivityForResult(intent, PICTURE_CODE);
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
            startActivityForResult(new Intent(WriteActivity.this, MapPopupActivity.class), MAP_POPUP_CODE);
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

    @BindViews({R.id.write_map_text1, R.id.write_map_text2})
    List<EditText> mapTextList;

    @BindViews({R.id.img_write_selected_first, R.id.img_write_selected_second, R.id.img_write_selected_third, R.id.img_write_selected_fourth, R.id.img_write_selected_fifth})
    List<ImageView> writeSelectedImageList;

    @BindView(R.id.edit_write_title)
    EditText writeTitleEdit;

    @BindView(R.id.edit_write_content)
    EditText writeContentEdit;

    @BindView(R.id.btn_write_register)
    Button writeRegisterBtn;

    @OnClick(R.id.btn_write_back)
    public void onBackBtn() {
        finish();
    }

    private Set<String> hairTypeSet = new HashSet<>();
    private GregorianCalendar calendar = new GregorianCalendar();
    private boolean isPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);
        isPopup = false;
    }

    @Override
    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(writeTitleEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(writeContentEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mapTextList.get(0).getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mapTextList.get(1).getWindowToken(), 0);
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
        isPopup = false;
        if (requestCode == MAP_POPUP_CODE) {
            if (resultCode == MapPopupActivity.RESULT_SUCCESS) {
                String addressString = data.getStringExtra("address");
                double lat = data.getDoubleExtra("lat", 0);
                double lng = data.getDoubleExtra("lng", 0);
                int subIndex;
                for (subIndex = 0; subIndex < addressString.length(); subIndex++) {
                    if (subIndex >= 10 && addressString.charAt(subIndex) == ' ') {
                        break;
                    }
                }
                mapTextList.get(0).setText(addressString.substring(0, subIndex));
                mapTextList.get(1).setText(addressString.substring(subIndex));
            }
        } else if (requestCode == PICTURE_CODE){
            List<String> photos = null;
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    for (int i = 0; i < writeSelectedImageList.size(); i++) {
                        writeSelectedImageList.get(i).setImageResource(0);
                    }
                    for (int i = 0; i < photos.size(); i++) {
                        Glide.with(WriteActivity.this).load(photos.get(i)).override(100, 100).thumbnail(0.3f).into(writeSelectedImageList.get(i));
                    }
                }
            }
        }
    }
}
