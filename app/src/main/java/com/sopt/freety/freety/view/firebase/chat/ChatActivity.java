package com.sopt.freety.freety.view.firebase.chat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.firebase.chat.data.ChatData;
import com.sopt.freety.freety.view.firebase.chat.data.ChatListFirebaseData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chat_list_view) ListView listView;
    @BindView(R.id.chat_edit) EditText editText;
    @BindView(R.id.chat_btn) Button sendBtn;

    private static final String USER_NAME = "JunhoeKim";
    private ArrayAdapter<String> chatDataAdapter;
    private InputMethodManager imm;
    private int roomId;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        roomId = getIntent().getIntExtra("roomId", -1);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        chatDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(chatDataAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        databaseReference.child("messages").orderByChild("roomId").equalTo(roomId).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override`
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                chatDataAdapter.add(chatData.getSenderId() + ": " + chatData.getMessage());  // adapter에 추가합니다.
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    @OnClick(R.id.chat_btn)
    public void onSendBtnClick() {
        final String receiverId = getIntent().getStringExtra("otherId");
        final ChatData chatData = new ChatData(roomId, ME, receiverId,
                editText.getAddressText().toString(), "1:00");
        databaseReference.child("messages").push().setValue(chatData);
        final DatabaseReference otherChatListDataRef = databaseReference.child("rooms")
                .orderByChild("myId")
                .equalTo(receiverId)
                .orderByChild("otherId")
                .equalTo(ME)
                .getRef();
        otherChatListDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ChatActivity", String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        otherChatListDataRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ChatListFirebaseData value = mutableData.getValue(ChatListFirebaseData.class);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });

        editText.setText("");
        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }
}
*/
