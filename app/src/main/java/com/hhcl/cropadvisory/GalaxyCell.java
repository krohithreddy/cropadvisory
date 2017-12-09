package com.hhcl.cropadvisory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;

/**
 * Created by Karthik Kumar K on 02-12-2017.
 */

public class GalaxyCell extends SimpleCell<DBModel,GalaxyCell.ViewHolder> {
    public GalaxyCell(@NonNull DBModel item) {
        super(item);
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.cardview;
    }
    /*
    - Return a ViewHolder instance
     */
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new ViewHolder(cellView);
    }
    /*
    - Bind data to widgets in our viewholder.
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull Context context, Object o) {
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;
      //  final DBModel model = getItem();
        textViewName.setText(getItem().getTt_name());
        textViewVersion.setText(getItem().getMsg());
        String tstamp=getItem().getTstamp();
    }
    /**
     - Our ViewHolder class.
     - Inner static class.
     * Define your view holder, which must extend SimpleViewHolder.
     * */
    static class ViewHolder extends SimpleViewHolder {
        TextView textViewName;
        TextView textViewVersion,headdate;
        ImageView imageViewIcon,yesicon,star;
        CardView card;
        ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.yesicon = (ImageView) itemView.findViewById(R.id.imageVi);
            this.card=(CardView)itemView.findViewById(R.id.card_view);
            this.headdate = (TextView) itemView.findViewById(R.id.ddate);
            this.star=(ImageView)itemView.findViewById(R.id.star);
        }
    }
}