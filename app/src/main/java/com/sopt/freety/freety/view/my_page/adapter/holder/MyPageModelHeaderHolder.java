package com.sopt.freety.freety.view.my_page.adapter.holder;

import android.Manifest;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.Consts;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sopt.freety.freety.R.id.parent;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageModelHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.front_picture) ImageView frontPicture;
    @BindView(R.id.back_picture) ImageView backPicture;
    @BindView(R.id.side_picture) ImageView sidePicture;

    public MyPageModelHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public ImageView getFrontPicture() {
        return frontPicture;
    }

    public ImageView getBackPicture() {
        return backPicture;
    }

    public ImageView getSidePicture() {
        return sidePicture;
    }

}
