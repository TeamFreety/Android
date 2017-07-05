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
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.view.letter.adapter.LetterRecyclerAdapter;
import com.sopt.freety.freety.view.letter.data.LetterData;
import com.sopt.freety.freety.view.letter.data.LetterListResultData;
import com.sopt.freety.freety.view.letter.data.RealmLetter;
import com.sopt.freety.freety.view.letter.data.RealmPerson;
import com.sopt.freety.freety.view.property.ScreenClickable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LetterActivity extends AppCompatActivity implements ScreenClickable {

    @OnClick(R.id.btn_letter_back)
    public void onBackBtn() {
        onBackPressed();
    }

    @BindView(R.id.letter_edit_text)
    EditText letterEditText;

    @BindView(R.id.letter_recyclerview)
    RecyclerView recyclerView;


    private Realm realm;
    private LetterRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        realm = Realm.getDefaultInstance();
        networkService = AppController.getInstance().getNetworkService();
        layoutManager = new LinearLayoutManager(this);
        int postId = getIntent().getIntExtra("postId", -1);
        int memberId = getIntent().getIntExtra("memberId", -1);
        if (postId != -1) {
            updateByPostId(postId);
        } else if (memberId != -1) {
            updateByMemberId(memberId);
        } else {
            Toast.makeText(this, "네트워크 오류", Toast.LENGTH_SHORT).show();
        }
        adapter = new LetterRecyclerAdapter(this, Collections.<LetterData>emptyList());
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

    @OnClick(R.id.letter_edit_btn)
    public void onPress() {
        realm.beginTransaction();
    }

    public void updateByPostId(int postId) {
    }

    public void updateByMemberId(int memberId) {
        Call<LetterListResultData> call = networkService.getLetterDatas(SharedAccessor.getToken(this), memberId);
        call.enqueue(new Callback<LetterListResultData>() {
            @Override
            public void onResponse(Call<LetterListResultData> call, Response<LetterListResultData> response) {
                if (response.isSuccessful() &&
                        response.body().getMessage().equals("success loading message list")) {
                    List<LetterData> letterDatas = new ArrayList<>();
                    String imageURL = response.body().getImageURL();
                    int otherId = response.body().getMemberId();
                    String memberName = response.body().getMemberName();
                    List<LetterData> newLetterDataList = response.body().getLetterDataList();
                    RealmResults<RealmLetter> letterList = tryGetRealmLetterList(otherId, imageURL, memberName);
                    for (RealmLetter letter : letterList) {
                        letterDatas.add(letter.getLetterData(imageURL));
                    }
                    letterDatas.addAll(newLetterDataList);
                    adapter.updateLetterDataList(letterDatas);

                    for (LetterData letterData : newLetterDataList) {
                        insertLetterData(letterData, otherId);
                    }
                }
            }
            @Override
            public void onFailure(Call<LetterListResultData> call, Throwable t) {
            }
        });
    }

    private void insertLetterData(LetterData letterData, int otherId) {
        realm.beginTransaction();
        RealmLetter realmLetter = realm.createObject(RealmLetter.class);
        realmLetter.setOtherName(letterData.getName());
        realmLetter.setContent(letterData.getContent());
        realmLetter.setDate(letterData.getDate());
        realmLetter.setOtherId(otherId);
        realmLetter.setMyMsg(false);
        realm.commitTransaction();
    }

    private RealmResults<RealmLetter> tryGetRealmLetterList(int memberId, String imageURL, String memberName) {
        RealmResults<RealmPerson> personList = realm.where(RealmPerson.class).equalTo("memberId", memberId).findAll();
        if (personList.size() == 0) {
            realm.beginTransaction();
            RealmPerson realmPerson = realm.createObject(RealmPerson.class);
            realmPerson.setMemberId(memberId);
            realmPerson.setImageURL(imageURL);
            realmPerson.setMemberName(memberName);
            realm.commitTransaction();
        }
        return realm.where(RealmLetter.class).equalTo("letterList.otherId", memberId).findAll();
    }
}
