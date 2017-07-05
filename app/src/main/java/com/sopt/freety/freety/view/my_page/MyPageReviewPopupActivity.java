package com.sopt.freety.freety.view.my_page;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewData;
import com.sopt.freety.freety.view.wirte.WriteActivity;
import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyPageReviewPopupActivity extends AppCompatActivity {

    @BindView(R.id.review_popup_title_edit)
    EditText reviewTitleEdit;

    @BindView(R.id.review_popup_picture_btn)
    ImageButton pictureBtn;

    @OnClick(R.id.review_popup_picture_btn)
    public void onClick() {
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 외부 저장소에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
                .check();
    }

    @BindViews({R.id.review_popup_star_btn1, R.id.review_popup_star_btn2, R.id.review_popup_star_btn3, R.id.review_popup_star_btn4, R.id.review_popup_star_btn5})
    List<ImageButton> starButtonList;

    @OnClick({R.id.review_popup_star_btn1, R.id.review_popup_star_btn2, R.id.review_popup_star_btn3, R.id.review_popup_star_btn4, R.id.review_popup_star_btn5})
    public void onStarClick(View view) {
        int emptyRes = R.drawable.review_popup_empty_star;
        int fullRes = R.drawable.review_popup_full_star;
        int offset = 0;
        for (int index = 0; index < starButtonList.size(); index++) {
            if (view.getId() == starButtonList.get(index).getId()) {
                offset = index;
                break;
            }
        }
        for (int index = 0; index < starButtonList.size(); index++) {
            if (index <= offset) {
                starButtonList.get(index).setImageResource(fullRes);
            } else {
                starButtonList.get(index).setImageResource(emptyRes);
            }
        }
        scoreText.setText(String.valueOf(offset + 1) + ".0");
    }

    @BindView(R.id.review_popup_image)
    ImageView reviewImage;

    @BindView(R.id.review_popup_score)
    TextView scoreText;

    @BindView(R.id.review_popup_content)
    EditText contentEdit;

    private MultipartBody.Part imageBody;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_review_popup);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        getWindow().getAttributes().width = (int)(0.9f * display.getWidth());
        getWindow().getAttributes().height = (int)(0.9f * display.getHeight());
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Consts.REVIEW_PICTURE_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                List<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                String path = photos.get(0);
                File file = new File(path);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                imageBody = MultipartBody.Part.createFormData("image", file.getName(), fileBody);
                Glide.with(MyPageReviewPopupActivity.this)
                        .load(path)
                        .override(100, 100)
                        .thumbnail(0.3f)
                        .placeholder(R.drawable.placeholder_photo)
                        .into(reviewImage);
            }
        }
    }

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(MyPageReviewPopupActivity.this);
            intent.setMaxSelectCount(1);
            intent.setSelectCheckBox(true);
            intent.setMaxGrideItemCount(3);
            startActivityForResult(intent, Consts.REVIEW_PICTURE_CODE);
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        }
    };
}
