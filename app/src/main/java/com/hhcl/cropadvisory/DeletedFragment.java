package com.hhcl.cropadvisory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Karthik Kumar K on 29-11-2017.
 */

public class DeletedFragment extends Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DBModel> dbmodel;
    Typeface font, datefont;
    int screenWidth, screenHeight;

    RelativeLayout rr;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deleted_items, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        rr = (RelativeLayout) view.findViewById(R.id.rel);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DBHelper database = new DBHelper(getActivity());
        dbmodel = new ArrayList<DBModel>();
        dbmodel = database.getAllDeleteitems();

        adapter = new DeleteListAdapter(dbmodel, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() == 0) {
            recyclerView.setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }
        font = Typeface.createFromAsset(getActivity().getAssets(), "ramabhadra.ttf");
        datefont = Typeface.createFromAsset(getActivity().getAssets(), "Arial Bold.ttf");
        Display display = ((WindowManager) getActivity().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        view.requestFocus();
        return view;
    }

    private class DeleteListAdapter extends RecyclerView.Adapter<DeleteListAdapter.MyViewHolde> {
        private ArrayList<DBModel> dataSet;
        Context context;

        public DeleteListAdapter(ArrayList<DBModel> dbmodel, FragmentActivity deletedFragment) {
            this.dataSet = dbmodel;
            this.context = deletedFragment;
        }

        @Override
        public DeleteListAdapter.MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.delete_cardview, parent, false);
            DeleteListAdapter.MyViewHolde myViewHolder = new DeleteListAdapter.MyViewHolde(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolde holder, final int listPosition) {
            TextView textViewName = holder.textViewName;
            TextView textViewVersion = holder.textViewVersion;
            final ImageView imageView = holder.imageViewIcon;
            TextView datetime = holder.datetime;
            datetime.setText(dataSet.get(listPosition).getTstamp());
            textViewName.setText(dataSet.get(listPosition).getTt_name());
            textViewVersion.setText(dataSet.get(listPosition).getMsg());
            Picasso.with(context).load(dataSet.get(listPosition).getUrl()).resize(screenWidth, screenWidth * 250 / 400).into(imageView);
            textViewName.setTypeface(font);
            textViewVersion.setTypeface(font);
            datetime.setTypeface(datefont);
            datetime.setAlpha(0.5f);

            holder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DBHelper db = new DBHelper(context);

                    String title = dataSet.get(listPosition).getTt_name();
                    String message = dataSet.get(listPosition).getMsg();
                    String imageUrl = dataSet.get(listPosition).getUrl();
                    String imagetype = dataSet.get(listPosition).getImgtype();
                    String notifyid = dataSet.get(listPosition).getImgfull();
                    String timestamp = dataSet.get(listPosition).getTstamp();
                    String starstatus = dataSet.get(listPosition).getStar();
                    db.updateContact2(new DBModel(title, message, imageUrl, imagetype, notifyid, "dsfcsf", timestamp, starstatus, "0"));


                    DBHelper database = new DBHelper(context);
                   // database.deleteitem(dataSet.get(listPosition).getImgfull());
                    dataSet.remove(listPosition);
                    notifyItemRemoved(listPosition);
                    notifyItemRangeChanged(listPosition, getItemCount());
                    if (adapter.getItemCount() == 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            recyclerView.setBackground(getResources().getDrawable(R.drawable.emptyscreen));
                        }
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public class MyViewHolde extends RecyclerView.ViewHolder {
            TextView textViewName, datetime;
            TextView textViewVersion;
            ImageView imageViewIcon, delete, star;

            public MyViewHolde(View itemView) {
                super(itemView);
                this.textViewName = (TextView) itemView.findViewById(R.id.title);
                this.textViewVersion = (TextView) itemView.findViewById(R.id.count);
                this.datetime = (TextView) itemView.findViewById(R.id.date);
                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.thumbnail);
                this.delete = (ImageView) itemView.findViewById(R.id.delete);
                this.star = (ImageView) itemView.findViewById(R.id.star);
            }
        }
    }
}
