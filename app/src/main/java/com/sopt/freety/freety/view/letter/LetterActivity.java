package com.sopt.freety.freety.view.letter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.OnlyMsgResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.util.DateParser;
import com.sopt.freety.freety.view.letter.adapter.LetterRecyclerAdapter;
import com.sopt.freety.freety.view.letter.data.LetterData;
import com.sopt.freety.freety.view.letter.data.LetterListResultData;
import com.sopt.freety.freety.view.letter.data.PushRequestData;
import com.sopt.freety.freety.view.letter.data.RPerson;
import com.sopt.freety.freety.view.letter.data.RealmLetter;
import com.sopt.freety.freety.view.my_page.DesignerToModelMypageActivity;
import com.sopt.freety.freety.view.property.ScreenClickable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LetterActivity extends AppCompatActivity implements ScreenClickable, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "LetterActivity";

    @BindView(R.id.btn_to_model_mypage)
    ImageButton myPageBtn;

    @OnClick(R.id.btn_to_model_mypage)
    public void onMypageBtn() {
        Intent intent = new Intent(getApplicationContext(), DesignerToModelMypageActivity.class);
        intent.putExtra("memberId", memberId);
        AppController.getInstance().pushPageStack();
        startActivity(intent);
    }

    @OnClick(R.id.btn_letter_back)
    public void onBackBtn() {
        onBackPressed();
    }

    @BindView(R.id.letter_edit_text)
    EditText letterEditText;

    @BindView(R.id.letter_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.letter_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private Realm realm;
    private LetterRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private NetworkService networkService;
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        networkService = AppController.getInstance().getNetworkService();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        memberId = getIntent().getIntExtra("memberId", -1);
        String imageURL = getIntent().getStringExtra("memberURL");
        adapter = new LetterRecyclerAdapter(this, Collections.<LetterData>emptyList());
        recyclerView.setAdapter(adapter);
        if (memberId != -1) {
            updateByMemberId(memberId);
        } else {
            Toast.makeText(this, "네트워크 오류", Toast.LENGTH_SHORT).show();
        }
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    recyclerView.smoothScrollToPosition(Math.max(adapter.getItemCount() -1, 0));
                }
            }
        });

        if (!SharedAccessor.isDesigner(this)) {
            myPageBtn.setVisibility(View.INVISIBLE);
        }
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
        final String content = letterEditText.getText().toString();

        // check member is exist
        RPerson checkPerson = realm.where(RPerson.class).equalTo("memberId", memberId).findFirst();
        if (checkPerson == null) {
            Log.i(TAG, "onResponse: " + "person is null create new one");
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RPerson newPerson = realm.createObject(RPerson.class);
                    newPerson.setMemberId(memberId);
                    newPerson.setMemberName(getIntent().getStringExtra("memberName"));
                    newPerson.setImageURL(getIntent().getStringExtra("memberImageURL"));
                }
            });
        }

        if (memberId != -1) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RPerson person = realm.where(RPerson.class).equalTo("memberId", memberId).findFirst();
                    RealmLetter realmLetter = realm.createObject(RealmLetter.class);
                    realmLetter.setOtherId(memberId);
                    realmLetter.setOtherName(person.getMemberName());
                    realmLetter.setMyMsg(true);
                    realmLetter.setDate(DateParser.to());
                    realmLetter.setContent(content);
                    person.getLetterList().add(realmLetter);
                }
            });
        }
        letterEditText.setText("");

        Call<OnlyMsgResultData> call = networkService.pushLetter(SharedAccessor.getToken(LetterActivity.this), new PushRequestData(memberId, content, DateParser.to()));
        call.enqueue(new Callback<OnlyMsgResultData>() {
            @Override
            public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                Log.i(TAG, "onResponse: message : " + response.body().getMessage());
                if (response.isSuccessful() && response.body().getMessage().equals("Complete")) {
                    Log.i(TAG, "onResponse: " + "메세지 전송 성공");
                } else {
                    Toast.makeText(LetterActivity.this, "메세지 전송 실패", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onResponse: 메세지 전송 실패");
                }
            }

            @Override
            public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {
                Toast.makeText(LetterActivity.this, "메세지 전송 실패, on failure", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onResponse: 메세지 전송 실패 on failure");
            }
        });
        updateByMemberId(memberId);
    }

    public void updateByMemberId(final int memberId) {
        Log.i(TAG, "현재 시간: " + DateParser.to());
        final String memberURL = getIntent().getStringExtra("memberURL");
        Call<LetterListResultData> call = networkService.getLetterDatas(SharedAccessor.getToken(this), memberId);
        call.enqueue(new Callback<LetterListResultData>() {
            @Override
            public void onResponse(Call<LetterListResultData> call, final Response<LetterListResultData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success loading message list")) {
                        final List<LetterData> letterDataList = response.body().getLetterDataList();
                        RPerson checkPerson = realm.where(RPerson.class).equalTo("memberId", response.body().getOtherId()).findFirst();
                        if (checkPerson == null) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RPerson newPerson = realm.createObject(RPerson.class);
                                    newPerson.setMemberId(response.body().getOtherId());
                                    newPerson.setMemberName(response.body().getOtherName());
                                    newPerson.setImageURL(response.body().getImageURL());
                                }
                            });
                        }

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RPerson person = realm.where(RPerson.class).equalTo("memberId", response.body().getOtherId()).findFirst();
                                for (int i = 0; i < letterDataList.size(); i++) {
                                    LetterData data = letterDataList.get(i);
                                    RealmLetter realmLetter = realm.createObject(RealmLetter.class);
                                    realmLetter.setOtherId(response.body().getOtherId());
                                    realmLetter.setOtherName(response.body().getOtherName());
                                    realmLetter.setMyMsg(false);
                                    realmLetter.setDate(data.getDate());
                                    realmLetter.setContent(data.getContent());
                                    person.getLetterList().add(realmLetter);
                                }
                            }
                        });

                    }
                    List<LetterData> letterRecyclerDatas = new ArrayList<>();
                    RealmResults<RealmLetter> realmLetters = realm.where(RealmLetter.class).equalTo("otherId", memberId).findAll();
                    for (RealmLetter realmLetter : realmLetters) {
                        letterRecyclerDatas.add(realmLetter.getLetterData(memberURL));
                    }

                    adapter.updateLetterDataList(letterRecyclerDatas);
                    recyclerView.smoothScrollToPosition(Math.max(letterRecyclerDatas.size() - 1, 0));
                    refreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<LetterListResultData> call, Throwable t) {
                List<LetterData> letterRecyclerDatas = new ArrayList<>();
                RealmResults<RealmLetter> realmLetters = realm.where(RealmLetter.class).equalTo("otherId", memberId).findAll();
                for (RealmLetter realmLetter : realmLetters) {
                    letterRecyclerDatas.add(realmLetter.getLetterData(getIntent().getStringExtra("memberURL")));
                }

                recyclerView.smoothScrollToPosition(Math.max(letterRecyclerDatas.size() - 1, 0));
                adapter.updateLetterDataList(letterRecyclerDatas);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (memberId != -1) {
            updateByMemberId(memberId);
        } else {
            Toast.makeText(this, "네트워크 오류", Toast.LENGTH_SHORT).show();
        }

    }
}
