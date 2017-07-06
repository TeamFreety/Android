package com.sopt.freety.freety.view.my_page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageModelHeaderHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPagePickHolder;
import com.sopt.freety.freety.view.my_page.data.MyPageModelHeaderData;
import com.sopt.freety.freety.view.my_page.data.MyPagePickData;
import java.util.List;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageModelRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_HEADER_HEIGHT = 60;
    public static int TYPE_HEADER = 0;
    private List<MyPageModelHeaderData> myPageModelHeaderDataList;  //Header
    private List<MyPagePickData> myPagePickDataList;    //recyclerView
    private Context context;

    public MyPageModelRecyclerAdapter(final List<MyPageModelHeaderData> myPageModelHeaderDataList,
                                      final List<MyPagePickData> myPagePickDataList,
                                      final Context context) {
        this.context = context;
        this.myPagePickDataList = myPagePickDataList;
        this.myPageModelHeaderDataList = myPageModelHeaderDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_HEADER) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_model_header, parent, false);
            return new MyPageModelHeaderHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_pick_elem, parent, false);
            return new MyPagePickHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyPageModelHeaderHolder) {
            MyPageModelHeaderHolder myPageModelHeaderHolder = (MyPageModelHeaderHolder) holder;
            Glide.with(context).load(myPageModelHeaderDataList.get(0).getImageURL())
                    .override(200, 200).thumbnail(0.2f).into(myPageModelHeaderHolder.getFrontPicture());
            Glide.with(context).load(myPageModelHeaderDataList.get(1).getImageURL())
                    .override(200, 200).thumbnail(0.2f).into(myPageModelHeaderHolder.getBackPicture());
            Glide.with(context).load(myPageModelHeaderDataList.get(2).getImageURL())
                    .override(200, 200).thumbnail(0.2f).into(myPageModelHeaderHolder.getSidePicture());
        } else {

            MyPagePickHolder castedHolder = (MyPagePickHolder) holder;
            Glide.with(context).load(myPagePickDataList.get(position - 1).getMockSource()).override(164,187).centerCrop().thumbnail(0.001f).into(castedHolder.getPostImage());
            castedHolder.getAddressText().setText(myPagePickDataList.get(position - 1).getMockAddress());
            castedHolder.getStyleText().setText(myPagePickDataList.get(position - 1).getMockStyle());
        }
    }

  /*  public void onPictureBtn() {
        new TedPermission(context)
                .setPermissionListener(permissionListener)
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 외부 저장소에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
                .check();
    }*/

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myPagePickDataList.size() + 1;
    }

    public void updatePicture(int position, String imageURL) {
        myPageModelHeaderDataList.get(position).setImageURL(imageURL);
        // network
    }
}
