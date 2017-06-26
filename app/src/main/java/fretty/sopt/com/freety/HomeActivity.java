package fretty.sopt.com.freety;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fretty.sopt.com.freety.fragment.AllFragment;
import fretty.sopt.com.freety.fragment.CutFragment;
import fretty.sopt.com.freety.fragment.DyeFragment;
import fretty.sopt.com.freety.fragment.EtcFragment;
import fretty.sopt.com.freety.fragment.PermFragment;

public class HomeActivity extends AppCompatActivity {

    ViewPager vp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vp2 = (ViewPager)findViewById(R.id.vp2);
        Button btn_all = (Button)findViewById(R.id.btn_all);
        Button btn_perm = (Button)findViewById(R.id.btn_perm);
        Button btn_dye = (Button)findViewById(R.id.btn_dye);
        Button btn_cut = (Button)findViewById(R.id.btn_cut);
        Button btn_etc = (Button)findViewById(R.id.btn_etc);

        vp2.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp2.setCurrentItem(0);

        btn_all.setOnClickListener(movePageListener);
        btn_all.setTag(0);
        btn_perm.setOnClickListener(movePageListener);
        btn_perm.setTag(1);
        btn_dye.setOnClickListener(movePageListener);
        btn_dye.setTag(2);
        btn_cut.setOnClickListener(movePageListener);
        btn_cut.setTag(3);
        btn_etc.setOnClickListener(movePageListener);
        btn_etc.setTag(4);
    }

    View.OnClickListener movePageListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int tag = (int)v.getTag();
            vp2.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter{
        public pagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new AllFragment();
                case 1:
                    return new PermFragment();
                case 2:
                    return new DyeFragment();
                case 3:
                    return new CutFragment();
                case 4:
                    return new EtcFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
