package com.sopt.freety.freety.view.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailSearchActivity extends AppCompatActivity {

    @BindView(R.id.btn_detail_cancel)
    Button detailCancelBtn;
    @BindView(R.id.checkbtn_sort_perm)
    CheckBox sortPermCheckBtn;
    @BindView(R.id.checkbtn_sort_dye)
    CheckBox sortDyeCheckBtn;
    @BindView(R.id.checkbtn_sort_cut)
    CheckBox sortCutCheckBtn;
    @BindView(R.id.checkbtn_sort_etc)
    CheckBox sortEtcCheckBtn;
    @BindView(R.id.btn_detail_2017_start)
    Button detail2017StartBtn;
    @BindView(R.id.spinner_month_start)
    Spinner monthStartSpinner;
    @BindView(R.id.spinner_day_start)
    Spinner dayStartSpinner;
    @BindView(R.id.btn_detail_2017_end)
    Button detail2017EndBtn;
    @BindView(R.id.spinner_month_end)
    Spinner monthEndSpinner;
    @BindView(R.id.spinner_day_end)
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

    RangeBar rangebar;



    ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        ButterKnife.bind(this);
        adapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthStartSpinner.setAdapter(adapter);
        monthStartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position)+"is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rangebar = (RangeBar) findViewById(R.id.rangebar);
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
            }
        });
        rangebar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                return null;
            }
        });

    }
}
