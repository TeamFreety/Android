package com.sopt.freety.freety.view.my_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sopt.freety.freety.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageReviewFragment extends Fragment {

    @BindView(R.id.my_page_style_career_list_view)
    ListView listView;

    public MyPageReviewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_style_header, container, false);
        ButterKnife.bind(this, view);

        List<String> itemData = new ArrayList<>();
        itemData.add("- 퍼스널 펌 & 컬러 전문 (5년)");
        itemData.add("- 성형 디자인 전문(13년) PANAN본사헤드디렉터");

        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, itemData);
        listView.setAdapter(adapter);

        return view;

    }
}
