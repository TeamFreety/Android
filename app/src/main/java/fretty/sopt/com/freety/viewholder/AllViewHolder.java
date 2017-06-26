package fretty.sopt.com.freety.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fretty.sopt.com.freety.R;

/**
 * Created by USER on 2017-06-25.
 */

public class AllViewHolder extends RecyclerView.ViewHolder {

    public TextView text_perm_local;
    public TextView text_perm_name;
    public ImageView img_perm_hair;

    public AllViewHolder(View itemView) {
        super(itemView);

        text_perm_local = (TextView)itemView.findViewById(R.id.text_perm_local);
        text_perm_name = (TextView)itemView.findViewById(R.id.text_perm_name);
        img_perm_hair = (ImageView)itemView.findViewById(R.id.img_perm_hair);
    }
}
