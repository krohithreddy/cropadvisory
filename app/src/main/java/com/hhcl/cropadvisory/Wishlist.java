package com.hhcl.cropadvisory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Karthik Kumar K on 16-11-2017.
 */

public class Wishlist extends android.support.v4.app.Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<NewsModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    ArrayList<DBModel> dbmodel;
    DBHelper database;
    private boolean isFragmentLoaded = false;
    Typeface font, datefont;
    int screenWidth, screenHeight;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded) {
            // Load your data here or do network operations here
            database = new DBHelper(getActivity());
            dbmodel = new ArrayList<DBModel>();
            dbmodel = database.getAllContacts2();
            adapter = new WishListAdapter(dbmodel, getActivity());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dbmodel = database.getAllContacts2();
        adapter = new WishListAdapter(dbmodel, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wishlist, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        database = new DBHelper(getActivity());
        dbmodel = new ArrayList<DBModel>();
        dbmodel = database.getAllContacts2();
        adapter = new WishListAdapter(dbmodel, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        font = Typeface.createFromAsset(getContext().getAssets(), "ramabhadra.ttf");
        datefont = Typeface.createFromAsset(getContext().getAssets(), "Arial Bold.ttf");
        if (adapter.getItemCount() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                recyclerView.setBackground(getResources().getDrawable(R.drawable.emptyscreen));
            }
        }
        return view;
    }

    private class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolde> {
        private ArrayList<DBModel> dataSet;
        Context context;

        public WishListAdapter(ArrayList<DBModel> dbmodel, FragmentActivity activity) {
            this.dataSet = dbmodel;
            this.context = activity;
        }

        @Override
        public WishListAdapter.MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview, parent, false);
            WishListAdapter.MyViewHolde myViewHolder = new WishListAdapter.MyViewHolde(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final WishListAdapter.MyViewHolde holder, final int listPosition) {
            TextView textViewName = holder.textViewName;
            TextView textViewVersion = holder.textViewVersion;
            final ImageView imageView = holder.imageViewIcon;
            TextView datetime = holder.datetime;

            textViewName.setTypeface(font);
            textViewVersion.setTypeface(font);
            datetime.setTypeface(datefont);
            datetime.setAlpha(0.5f);
            datetime.setText(dataSet.get(listPosition).getTstamp());
            textViewName.setText(dataSet.get(listPosition).getTt_name());
            textViewVersion.setText(dataSet.get(listPosition).getMsg());
            Log.d("updatestars", dbmodel.get(listPosition).getStar());
            ArrayList<DBModel> dd = new ArrayList<DBModel>();
            dd = database.getAllContacts2();
            Log.d("updategetallstars", dd.toString());
            Display display = ((WindowManager) getActivity().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
            if (dbmodel.get(listPosition).getStar().equals("1")) {
                holder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_filled));
            } else {
                holder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_unfilled));
            }
            Picasso.with(context).load(dataSet.get(listPosition).getUrl()).resize(screenWidth, screenWidth * 250 / 400).into(imageView);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storenotmsgPref();
                    DBHelper database = new DBHelper(context);
                    database.updateContact2(new DBModel(dataSet.get(listPosition).getTt_name(), dataSet.get(listPosition).getMsg(), dataSet.get(listPosition).getUrl(), dataSet.get(listPosition).getImgtype(), dataSet.get(listPosition).getImgfull(), "dsfcsf", dataSet.get(listPosition).getTstamp(), "0", "1"));
                    // database.deleteContact(dataSet.get(listPosition).getImgfull());
                    // Log.d("alldetails",dataSet.get(listPosition).getImgfull()+dataSet.get(listPosition).getNo_id()+dataSet.get(listPosition).getImgtype()+dataSet.get(listPosition).getTt_name()+dataSet.get(listPosition).getUrl());
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

        private void storenotmsgPref() {
            Intent intent = new Intent("YourAction1");
            Bundle bundle = new Bundle();
            //bundle.put... // put extras you want to pass with broadcast. This is optional
            bundle.putString("wishdel", "yes");
// bundle.putDouble("doubleName", speed);
            intent.putExtras(bundle);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
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
