package fretty.sopt.com.freety.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fretty.sopt.com.freety.R;
import fretty.sopt.com.freety.itemdata.ItemDataPerm;
import fretty.sopt.com.freety.recyclerview.RVAllAdapter;

/**
 * Created by USER on 2017-06-25.
 */

public class AllFragment extends Fragment {
    private RecyclerView recyclerView;
    private RVAllAdapter rvAllAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ItemDataPerm> itemDataPerms;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.item_list_perm, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        rvAllAdapter = new RVAllAdapter(itemDataPerms);
        recyclerView.setAdapter(rvAllAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }


}
