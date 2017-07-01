package com.sopt.freety.freety.view.wirte;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WriteActivity extends AppCompatActivity {

    @BindView(R.id.btn_wirte_cancel)
    Button writeCancelBtn;

    @BindView(R.id.checkbtn_write_sort_perm)
    CheckBox writeSortPermCheckbtn;
    @BindView(R.id.checkbtn_write_sort_dye)
    CheckBox writeSortDyeCheckbtn;
    @BindView(R.id.checkbtn_write_sort_cut)
    CheckBox writeSortCutCheckbtn;
    @BindView(R.id.checkbtn_write_sort_etc)
    CheckBox writeSortEtcCheckbtn;

    @BindView(R.id.spinner_write_month)
    Spinner writeMonthSpinner;
    @BindView(R.id.spinner_write_day)
    Spinner writeDaySpinner;
    @BindView(R.id.spinner_write_time)
    Spinner writeTimeSpinner;

    @BindView(R.id.btn_write_search_map)
    Button writeSearchMapBtn;
    @BindView(R.id.text_write_location_result)
    TextView writeLocationResultText;

    @BindView(R.id.btn_write_image_add)
    Button writeImageAddBtn;
    @BindView(R.id.img_write_selected_first)
    ImageView writeSelectedFirstImg;
    @BindView(R.id.img_write_selected_second)
    ImageView writeSelectedSecondImg;
    @BindView(R.id.img_write_selected_third)
    ImageView writeSelectedThirdImg;
    @BindView(R.id.img_write_selected_fourth)
    ImageView writeSelectedFourthImg;
    @BindView(R.id.img_write_selected_fifth)
    ImageView writeSelectedFifthImg;

    @BindView(R.id.edit_write_title)
    EditText writeTitleEdit;

    @BindView(R.id.edit_write_content)
    EditText writeContentEdit;

    @BindView(R.id.btn_write_register)
    Button writeRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        writeCancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
