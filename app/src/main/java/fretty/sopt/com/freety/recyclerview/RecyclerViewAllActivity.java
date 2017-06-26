package fretty.sopt.com.freety.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fretty.sopt.com.freety.R;
import fretty.sopt.com.freety.itemdata.ItemDataPerm;

public class RecyclerViewAllActivity extends AppCompatActivity {

    private RecyclerView rv_all;
    private RVAllAdapter rvAllAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ImageView img_perm_hair;
    private TextView text_perm_local;
    private TextView text_perm_name;
    ArrayList<ItemDataPerm> itemDataPerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_all);

        img_perm_hair = (ImageView)findViewById(R.id.img_perm_hair);
        text_perm_local = (TextView)findViewById(R.id.text_perm_local);
        text_perm_name = (TextView)findViewById(R.id.text_perm_name);

        rv_all = (RecyclerView)findViewById(R.id.rv_all);
        rv_all.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_all.setLayoutManager(linearLayoutManager);

        itemDataPerms = new ArrayList<ItemDataPerm>();

        rvAllAdapter = new RVAllAdapter(itemDataPerms, clickEvent);
        rv_all.setAdapter(rvAllAdapter);

    }
    public View.OnClickListener clickEvent = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toast.makeText(RecyclerViewAllActivity.this, "hi", Toast.LENGTH_SHORT).show();
        }
    };

}
