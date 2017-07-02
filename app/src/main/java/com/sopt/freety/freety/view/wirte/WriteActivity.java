package com.sopt.freety.freety.view.wirte;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.property.ScreenClickable;
import com.sopt.freety.freety.view.recruit.MapPopupActivity;

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
        startActivityForResult(new Intent(WriteActivity.this, MapPopupActivity.class), MAP_POPUP_CODE);
    }

    @BindViews({R.id.write_map_text1, R.id.write_map_text2})
    List<TextView> mapTextList;

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

    @OnClick(R.id.btn_write_back)
    public void onBackBtn() {
        finish();
    }

    private Set<String> hairTypeSet = new HashSet<>();
    private GregorianCalendar calendar = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);
    }

    @Override
    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(writeTitleEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(writeContentEdit.getWindowToken(), 0);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            writeDateText.setText(String.format("%d년 %d월 %d일", year, month, dayOfMonth));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        data.getStringExtra("address");
        mapTextList
    }
}
