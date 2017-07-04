package com.sopt.freety.freety.view.search;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.util.util.Triple;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilteredSearchActivity extends AppCompatActivity {


    @OnClick({R.id.btn_sort_perm, R.id.btn_sort_dye, R.id.btn_sort_cut, R.id.btn_sort_etc})
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

    @BindView(R.id.text_search_start_day)
    TextView textSearchStartDay;

    @OnClick(R.id.text_search_start_day)
    public void onDateBtn() {
        new DatePickerDialog(FilteredSearchActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @BindView(R.id.text_search_end_day)
    TextView textSearchEndDay;

    @OnClick(R.id.text_search_end_day)
    public void onDateEndBtn() {
        new DatePickerDialog(FilteredSearchActivity.this, dateEndSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.btn_detail_cancel)
    public void onBackBtn() {
        onBackPressed();
    }

    @BindView(R.id.checkbtn_career_default)
    CheckBox checkboxCareerDefault;

    @BindView(R.id.checkbtn_career_under_1year)
    CheckBox checkboxCareerUnder1year;

    @BindView(R.id.checkbtn_career_1to3year)
    CheckBox checkboxCareer1to3year;

    @BindView(R.id.checkbtn_career_3to5year)
    CheckBox checkboxCareer3to5year;

    @BindView(R.id.checkbtn_career_over_5year)
    CheckBox checkboxCareer5to5year;

    @BindViews({R.id.checkbtn_career_default, R.id.checkbtn_career_under_1year,
            R.id.checkbtn_career_1to3year, R.id.checkbtn_career_3to5year, R.id.checkbtn_career_over_5year})
    List<CheckBox> careerCheckboxList;

    @OnClick({R.id.checkbtn_career_default, R.id.checkbtn_career_under_1year,
            R.id.checkbtn_career_1to3year, R.id.checkbtn_career_3to5year, R.id.checkbtn_career_over_5year})
    public void onCheckedBoxClicked(CheckBox checkBox) {
        for (CheckBox box : careerCheckboxList) {
            box.setChecked(false);
        }
        checkBox.setChecked(true);
    }

    private GregorianCalendar calendar = new GregorianCalendar();
    private Set<String> hairTypeSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);
        ButterKnife.bind(this);

        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar);

        // get min and max text view
        final TextView tvMin = (TextView) findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) findViewById(R.id.textMax1);

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

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            textSearchStartDay.setText(String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth));
        }
    };

    private DatePickerDialog.OnDateSetListener dateEndSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            textSearchEndDay.setText(String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth));
        }
    };

    Map<Integer, Triple<Integer, Integer, Boolean>> locationBtnMap = initLocationBtnMap();
    @BindViews({R.id.btn_detail_location_none, R.id.btn_detail_location_near,
            R.id.btn_detail_location_gangnam, R.id.btn_detail_location_hongdae,
            R.id.btn_detail_location_gundae, R.id.btn_detail_location_kyodae,
            R.id.btn_detail_location_myungdong, R.id.btn_detail_location_boondang,
            R.id.btn_detail_location_garosu, R.id.btn_detail_location_yangjae,
            R.id.btn_detail_location_edae, R.id.btn_detail_location_nowon,
            R.id.btn_detail_location_sungshin, R.id.btn_detail_location_ilsan,
            R.id.btn_detail_location_bucheon, R.id.btn_detail_location_guro,
            R.id.btn_detail_location_jamsil, R.id.btn_detail_location_mockdong,
            R.id.btn_detail_location_anyang, R.id.btn_detail_location_kyunggi, R.id.btn_detail_location_etc})
    List<ImageButton> locationBtnList;

    @OnClick({R.id.btn_detail_location_none, R.id.btn_detail_location_near,
            R.id.btn_detail_location_gangnam, R.id.btn_detail_location_hongdae,
            R.id.btn_detail_location_gundae, R.id.btn_detail_location_kyodae,
            R.id.btn_detail_location_myungdong, R.id.btn_detail_location_boondang,
            R.id.btn_detail_location_garosu, R.id.btn_detail_location_yangjae,
            R.id.btn_detail_location_edae, R.id.btn_detail_location_nowon,
            R.id.btn_detail_location_sungshin, R.id.btn_detail_location_ilsan,
            R.id.btn_detail_location_bucheon, R.id.btn_detail_location_guro,
            R.id.btn_detail_location_jamsil, R.id.btn_detail_location_mockdong,
            R.id.btn_detail_location_anyang, R.id.btn_detail_location_kyunggi, R.id.btn_detail_location_etc})
    public void onViewClicked(ImageButton imageButton) {
        for (ImageButton button : locationBtnList) {
            int btnId = button.getId();
            button.setImageResource(locationBtnMap.get(btnId).getFirst());
            locationBtnMap.get(btnId).setThird(false);
        }

        int selectedBtnId = imageButton.getId();
        if (locationBtnMap.get(selectedBtnId).getThird()) {
            imageButton.setImageResource(locationBtnMap.get(selectedBtnId).getFirst());
            locationBtnMap.get(selectedBtnId).setThird(false);
        } else {
            imageButton.setImageResource(locationBtnMap.get(selectedBtnId).getSecond());
            locationBtnMap.get(selectedBtnId).setThird(true);
        }
    }

    private Map<Integer, Triple<Integer, Integer, Boolean>> initLocationBtnMap() {
        Map<Integer, Triple<Integer, Integer, Boolean>> map = new HashMap<>();
        map.put(R.id.btn_detail_location_none, Triple.of(R.drawable.search_none, R.drawable.search_onclick_none, false));
        map.put(R.id.btn_detail_location_near, Triple.of(R.drawable.search_near, R.drawable.search_onclick_near, false));
        map.put(R.id.btn_detail_location_gangnam, Triple.of(R.drawable.search_gananam, R.drawable.search_onclick_gangnam, false));
        map.put(R.id.btn_detail_location_hongdae, Triple.of(R.drawable.search_hongdae, R.drawable.search_onclick_hongdae, false));
        map.put(R.id.btn_detail_location_gundae, Triple.of(R.drawable.search_gundae, R.drawable.search_onclick_gundae, false));
        map.put(R.id.btn_detail_location_kyodae, Triple.of(R.drawable.search_kyodae, R.drawable.search_onclick_kyodae, false));
        map.put(R.id.btn_detail_location_myungdong, Triple.of(R.drawable.search_myeongdong, R.drawable.search_onclick_myeongdong, false));
        map.put(R.id.btn_detail_location_boondang, Triple.of(R.drawable.search_boondang, R.drawable.search_onclick_boondang, false));
        map.put(R.id.btn_detail_location_garosu, Triple.of(R.drawable.search_garosu, R.drawable.search_onclick_garosu, false));
        map.put(R.id.btn_detail_location_yangjae, Triple.of(R.drawable.search_yangjae, R.drawable.search_onclick_yangjae, false));
        map.put(R.id.btn_detail_location_edae, Triple.of(R.drawable.search_edae, R.drawable.search_onclick_edae, false));
        map.put(R.id.btn_detail_location_nowon, Triple.of(R.drawable.search_nowon, R.drawable.search_onclick_nowon, false));
        map.put(R.id.btn_detail_location_sungshin, Triple.of(R.drawable.search_sungshin, R.drawable.search_onclick_sungshin, false));
        map.put(R.id.btn_detail_location_ilsan, Triple.of(R.drawable.search_ilsan, R.drawable.search_onclick_ilsan, false));
        map.put(R.id.btn_detail_location_bucheon, Triple.of(R.drawable.search_bucheon, R.drawable.search_onclick_bucheon, false));
        map.put(R.id.btn_detail_location_guro, Triple.of(R.drawable.search_guro, R.drawable.search_onclick_guro, false));
        map.put(R.id.btn_detail_location_jamsil, Triple.of(R.drawable.search_jamsil, R.drawable.search_onclick_jamsil, false));
        map.put(R.id.btn_detail_location_mockdong, Triple.of(R.drawable.search_mokdong, R.drawable.search_onclick_mokdong, false));
        map.put(R.id.btn_detail_location_anyang, Triple.of(R.drawable.search_anyang, R.drawable.search_onclick_anyang, false));
        map.put(R.id.btn_detail_location_kyunggi, Triple.of(R.drawable.search_gyeongki, R.drawable.search_onclick_kyeongki, false));
        map.put(R.id.btn_detail_location_etc, Triple.of(R.drawable.search_etc, R.drawable.search_onclick_etc, false));
        return map;
    }

    @Override
    public void onBackPressed() {
        int result = AppController.getInstance().popPageStack();
        if (result == 0) {
            Toast.makeText(this, "한 번 더 터치하시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }  else if (result < 0) {
            ActivityCompat.finishAffinity(this);
        } else {
            super.onBackPressed();
        }
    }
}

