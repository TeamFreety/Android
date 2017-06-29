package com.sopt.freety.freety.view.firebase.chat;

/*
public class ChatListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.chat_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.chat_list_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    public static final String ME = "junhoeKim";

    private List<ChatListData> chatListDatas;
    private LinearLayoutManager layoutManager;
    private ChatListAdapter adapter;
    private final DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference()
            .child("rooms");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        chatListDatas = new ArrayList<>();
        adapter = new ChatListAdapter(chatListDatas);
        final String me = "junhoeKim";
        final String other = "donghyunyou";
        Query showListQuery = roomsReference.orderByChild("myId").equalTo(me);
        showListQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chatListDataSnapShot : dataSnapshot.getChildren()) {
                    ChatListFirebaseData data = chatListDataSnapShot.getValue(ChatListFirebaseData.class);
                    Log.i("ChatListData", "data myId : " + data.getMyId() + ", data otherId : " + data.getOtherId()
                            + String.valueOf(data.getIsNew()));
                    if (data.getMyId().equals(ME)) {
                        chatListDatas.add(new ChatListData(data.getRoomId(), R.drawable.chat_list_elem,
                                data.getIsNew(),
                                "junhoe",
                                "4:23",
                                data.getLastMsg()));
                    }
                }
                adapter.setAdapter(chatListDatas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("ChatListData", "the read failed" + databaseError.getMessage());
            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(),
                recyclerView, new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent toChatActivityIntent = new Intent(getApplicationContext(), ChatActivity.class);
                ChatListData chatListData = chatListDatas.get(position);
                toChatActivityIntent.putExtra("roomId", chatListData.getRoomId());
                toChatActivityIntent.putExtra("otherId", chatListData.getOtherId());
                startActivity(toChatActivityIntent);
            }

            @Override
            public void onItemLongClick(final View v, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatListActivity.this);
                builder.setTitle("아이템 삭제 확인")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setCancelable(true)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                //TODO: implement post method to server
                                adapter.removeDataElem(v);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }));
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }
}
*/