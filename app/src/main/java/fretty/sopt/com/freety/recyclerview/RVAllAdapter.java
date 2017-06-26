package fretty.sopt.com.freety.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fretty.sopt.com.freety.R;
import fretty.sopt.com.freety.itemdata.ItemDataPerm;
import fretty.sopt.com.freety.viewholder.AllViewHolder;

/**
 * Created by USER on 2017-06-25.
 */

public class RVAllAdapter extends RecyclerView.Adapter<AllViewHolder> {

    List<ItemDataPerm> itemDataPerms;
    int ITEM_COUNT = 50;


    public RVAllAdapter() {
    super();
        itemDataPerms = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; ++i){
            itemDataPerms.add(new ItemDataPerm(R.drawable.hair, "사는곳" + i, "시술명" + i));
        }
    }


    @Override
    public AllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_perm,parent,false);
        return new AllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllViewHolder holder, int position) {
        final ItemDataPerm itemDataPerm = itemDataPerms.get(position);

        holder.text_perm_local.setText(itemDataPerm.getText_perm_local());
        holder.text_perm_name.setText(itemDataPerm.getText_perm_name());
        holder.img_perm_hair.setImageResource(itemDataPerm.getImg_perm_hair());
    }

    @Override
    public int getItemCount() {
        return itemDataPerms.size();
    }
}
