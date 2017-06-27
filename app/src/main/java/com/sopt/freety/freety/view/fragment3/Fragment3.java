package com.sopt.freety.freety.view.fragment3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class Fragment3 extends Fragment{


    @BindView(R.id.example_text)
    TextView text;

    public Fragment3() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.example_layout, container, false);
        ButterKnife.bind(this, view);
        text.setText("3");
        return view;
    }

}