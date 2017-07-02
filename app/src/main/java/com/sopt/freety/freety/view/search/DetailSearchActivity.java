package com.sopt.freety.freety.view.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.util.Triple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailSearchActivity extends AppCompatActivity {

    @BindView(R.id.btn_detail_cancel)
    ImageButton detailCancelBtn;
    @BindView(R.id.btn_detail_location_none)
    ImageButton btnDetailLocationNone;
    @BindView(R.id.btn_detail_location_near)
    ImageButton btnDetailLocationNear;
    @BindView(R.id.btn_detail_location_gangnam)
    ImageButton btnDetailLocationGangnam;
    @BindView(R.id.btn_detail_location_hongdae)
    ImageButton btnDetailLocationHongdae;
    @BindView(R.id.btn_detail_location_gundae)
    ImageButton btnDetailLocationGundae;
    @BindView(R.id.btn_detail_location_kyodae)
    ImageButton btnDetailLocationKyodae;
    @BindView(R.id.btn_detail_location_myungdong)
    ImageButton btnDetailLocationMyungdong;
    @BindView(R.id.btn_detail_location_boondang)
    ImageButton btnDetailLocationBoondang;
    @BindView(R.id.btn_detail_location_garosu)
    ImageButton btnDetailLocationGarosu;
    @BindView(R.id.btn_detail_location_yangjae)
    ImageButton btnDetailLocationYangjae;
    @BindView(R.id.btn_detail_location_edae)
    ImageButton btnDetailLocationEdae;
    @BindView(R.id.btn_detail_location_nowon)
    ImageButton btnDetailLocationNowon;
    @BindView(R.id.btn_detail_location_sungshin)
    ImageButton btnDetailLocationSungshin;
    @BindView(R.id.btn_detail_location_ilsan)
    ImageButton btnDetailLocationIlsan;
    @BindView(R.id.btn_detail_location_bucheon)
    ImageButton btnDetailLocationBucheon;
    @BindView(R.id.btn_detail_location_guro)
    ImageButton btnDetailLocationGuro;
    @BindView(R.id.btn_detail_location_jamsil)
    ImageButton btnDetailLocationJamsil;
    @BindView(R.id.btn_detail_location_mockdong)
    ImageButton btnDetailLocationMockdong;
    @BindView(R.id.btn_detail_location_anyang)
    ImageButton btnDetailLocationAnyang;
    @BindView(R.id.btn_detail_location_kyunggi)
    ImageButton btnDetailLocationKyunggi;
    @BindView(R.id.btn_detail_location_etc)
    ImageButton btnDetailLocationEtc;




    private CrystalRangeSeekbar rangeSeekbar;
    private TextView tvMin, tvMax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        ButterKnife.bind(this);
        detailCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





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
            Log.i("Detail", "onViewClicked: " + locationBtnMap.get(btnId).getFirst());
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
}

