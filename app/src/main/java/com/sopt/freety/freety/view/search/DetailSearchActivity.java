package com.sopt.freety.freety.view.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailSearchActivity extends AppCompatActivity {

    @BindView(R.id.btn_detail_cancel)
    Button detailCancelBtn;

    @BindView(R.id.btn_detail_2017_start)
    Button detail2017StartBtn;
    @BindView(R.id.spinner_detail_month_start)
    Spinner monthStartSpinner;
    @BindView(R.id.spinner_detail_day_start)
    Spinner dayStartSpinner;
    @BindView(R.id.btn_detail_2017_end)
    Button detail2017EndBtn;
    @BindView(R.id.spinner_detail_month_end)
    Spinner monthEndSpinner;
    @BindView(R.id.spinner_detail_day_end)
    Spinner dayEndSpinner;
    @BindView(R.id.btn_detail_local_default)
    Button localDefaultDetailBtn;
    @BindView(R.id.checkbtn_career_default)
    CheckBox careerDefaultCheckBtn;
    @BindView(R.id.checkbtn_career_under_1year)
    CheckBox careerUnder1yearCheckBtn;
    @BindView(R.id.checkbtn_career_1to3year)
    CheckBox career1to3yearCheckBtn;
    @BindView(R.id.checkbtn_career_3to5year)
    CheckBox career3to5yearCheckBtn;
    @BindView(R.id.checkbtn_career_over_5year)
    CheckBox careerOver5yearCheckBtn;
    @BindView(R.id.btn_detail_filter_adapt)
    Button filterAdaptDetailBtn;

    private CrystalRangeSeekbar rangeSeekbar;
    private TextView tvMin, tvMax;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        ButterKnife.bind(this);
        detailCancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar)findViewById(R.id.rangeSeekbar);

        // get min and max text view
        final TextView tvMin = (TextView)findViewById(R.id.textMin1);
        final TextView tvMax = (TextView)findViewById(R.id.textMax1);

        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


    }



    }

