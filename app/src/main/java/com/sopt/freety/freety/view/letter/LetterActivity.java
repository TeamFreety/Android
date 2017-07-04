package com.sopt.freety.freety.view.letter;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.view.letter.adapter.LetterRecyclerAdapter;
import com.sopt.freety.freety.view.letter.data.LetterData;
import com.sopt.freety.freety.view.letter.data.RealmLetter;
import com.sopt.freety.freety.view.property.ScreenClickable;

import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class LetterActivity extends AppCompatActivity implements ScreenClickable {

    @OnClick(R.id.btn_letter_back)
    public void onBackBtn() {
        onBackPressed();
    }

    @BindView(R.id.letter_edit_text)
    EditText letterEditText;

    @BindView(R.id.letter_recyclerview)
    RecyclerView recyclerView;

    @OnClick(R.id.letter_edit_btn)
    public void onPress() {
    }

    private Realm realm;
    private LetterRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        realm = Realm.getDefaultInstance();
        RealmResults<RealmLetter> letters = realm.where(RealmLetter.class).equalTo("senderName", "이지은").findAll();
        layoutManager = new LinearLayoutManager(this);
        adapter = new LetterRecyclerAdapter(this, Collections.<LetterData>emptyList());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(letterEditText.getWindowToken(), 0);
    }
}
