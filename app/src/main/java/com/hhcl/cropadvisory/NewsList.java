package com.hhcl.cropadvisory;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Karthik Kumar K on 16-11-2017.
 */

public class NewsList extends android.support.v4.app.Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<NewsModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    ArrayList<DBModel> dbmodel;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    DBHelper database;
    private boolean isFragmentLoaded = true;
    Typeface font, datefont;
    int screenWidth, screenHeight;
String finalString;
    Integer value;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newslist, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        database = new DBHelper(getActivity());
        data = new ArrayList<NewsModel>();
        try {
            value = getArguments().getInt("Position");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Log.d("positionint3",value.toString());
        dbmodel = new ArrayList<DBModel>();
        dbmodel = database.getAllafterDelete();
        if (dbmodel.contains(value)) {

        }
        removedItems = new ArrayList<Integer>();
        adapter = new NewsListAdapter(dbmodel, getActivity());
        recyclerView.setAdapter(adapter);
        if (value != null) {
            recyclerView.scrollToPosition(value);
        }

        adapter.notifyDataSetChanged();
        font = Typeface.createFromAsset(getContext().getAssets(), "ramabhadra.ttf");
        datefont = Typeface.createFromAsset(getContext().getAssets(), "Arial Bold.ttf");
        Display display = ((WindowManager) getActivity().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        if (adapter.getItemCount() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                recyclerView.setBackground(getResources().getDrawable(R.drawable.emptyscreen));
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRegistrationBroadcastReceiver = new MyBroadcastReceiver();
        final IntentFilter intentFilter = new IntentFilter("YourAction");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver, intentFilter);
        final IntentFilter intentFilter1 = new IntentFilter("YourAction1");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver, intentFilter1);
        adapter.notifyDataSetChanged();
    }

    private class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolde> {
        private ArrayList<DBModel> dataSet;
        Context context;

        public NewsListAdapter(ArrayList<DBModel> dbmodel, FragmentActivity activity) {

            this.dataSet = dbmodel;
            this.context = activity;
        }

        @Override
        public NewsListAdapter.MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview, parent, false);
            NewsListAdapter.MyViewHolde myViewHolder = new NewsListAdapter.MyViewHolde(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final NewsListAdapter.MyViewHolde holder, final int listPosition) {
            TextView textViewName = holder.textViewName;
            TextView textViewVersion = holder.textViewVersion;
            final ImageView imageView = holder.imageViewIcon;
            TextView datetime = holder.datetime;
            textViewName.setTypeface(font);
            textViewVersion.setTypeface(font);
            datetime.setTypeface(datefont);
            datetime.setAlpha(0.8f);
            String sdate = dataSet.get(listPosition).getTstamp();
            Log.d("sdate", sdate);
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a",Locale.US);
            Date date = null;
            try {
                date = (Date) formatter.parse(sdate);
                Log.d("newtimestamp33", date.toString());
                 finalString = newFormat.format(date);
                Log.d("newtimestamp33", finalString);
                if (finalString.equals(""))
                {
                    datetime.setText(dataSet.get(listPosition).getTstamp());
                }else{
                    datetime.setText(finalString);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }






            textViewName.setText(dataSet.get(listPosition).getTt_name());
            textViewVersion.setText(dataSet.get(listPosition).getMsg());

            ArrayList<DBModel> dd = new ArrayList<DBModel>();
            dd = database.getAllContacts2();
            Log.d("updategetallstars", dd.toString());
            if (dataSet.get(listPosition).getTt_name().equals("null")) {
                textViewName.setText("విశాఖపట్టణం లో విడుదల చేసిన ఏకరూప ఖతులు విశాఖపట్టణం లో విడుదల చేసిన");
            } else if (textViewVersion.equals("null")) {
                textViewVersion.setText("ఒక కాకి ఎప్పుడు హంసలను చూసి కుళ్ళు కునేది. వాటి తెల్లటి రెక్కలని, అందమైన రూపాన్ని చూసి కాకి బాధ పాడేది. ఎప్పుడు “నేనూ అలా వుంటే బాగుండేది! ఇలా నల్లగా వున్నాను” అనుకుంటూ వుండేది. ఒక రోజు కాకికి ఒక మూర్ఖమైన ఆలోచన కలిగింది.");
            }

            if (dbmodel.get(listPosition).getStar().equals("1")) {
                holder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_filled));
            } else {
                holder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_unfilled));
            }

            if (dataSet.get(listPosition).getUrl().equals("null")) {
                holder.imageViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.bigimage));
            } else {
//                Picasso
//                        .with(context)
//                        .load(dataSet.eatFoodyImages[0])
//                        .fit()
//                        // call .centerInside() or .centerCrop() to avoid a stretched image
//                        .into(imageViewFit);screenWidth*250/400
                Picasso.with(context).load(dataSet.get(listPosition).getUrl()).resize(screenWidth, screenWidth * 250 / 400).into(imageView);
                // Picasso.with(context).load(dataSet.get(listPosition).getUrl()).resize(250, 200).into(imageView);

            }
            if (dataSet.get(listPosition).getMsg().equals("") && dataSet.get(listPosition).getTt_name().equalsIgnoreCase("")) {
                holder.textViewVersion.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                holder.star.setVisibility(View.GONE);
                holder.datetime.setVisibility(View.GONE);
                holder.textViewName.setVisibility(View.GONE);
                Picasso.with(context).load(dataSet.get(listPosition).getUrl()).resize(screenWidth, screenWidth * 500 / 400).into(imageView);
            }
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataSet.get(listPosition).getDeletestatus().equals("0")) {
                        Log.d("deletestatus", dataSet.get(listPosition).getDeletestatus());
                        DBHelper dbHelper = new DBHelper(context);

                        dbHelper.updateContact2(new DBModel(dataSet.get(listPosition).getTt_name(), dataSet.get(listPosition).getMsg(), dataSet.get(listPosition).getUrl(), dataSet.get(listPosition).getImgtype(), dataSet.get(listPosition).getImgfull(), "dsfcsf", dataSet.get(listPosition).getTstamp(), "0", "1"));
                        //Log.d("updatedetails3",dataSet.get(listPosition).getStar());

                        dbmodel = database.getAllafterDelete();
//                        adapter = new NewsListAdapter(dbmodel,getActivity());
//                        recyclerView.setAdapter(adapter);
                        dataSet.remove(listPosition);
                        notifyItemRemoved(listPosition);
                        notifyItemRangeChanged(listPosition, getItemCount());
                        if (adapter.getItemCount() == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                recyclerView.setBackground(getResources().getDrawable(R.drawable.emptyscreen));
                            }
                        }
                    }

                }
            });
            holder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   Log.d("updatedetails1", dataSet.get(listPosition).getStar());

                    if (dbmodel.get(listPosition).getStar().equals("1")) {
                        holder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_unfilled));
                        DBHelper dbHelper = new DBHelper(context);
                        dbHelper.updateContact2(new DBModel(dataSet.get(listPosition).getTt_name(), dataSet.get(listPosition).getMsg(), dataSet.get(listPosition).getUrl(), dataSet.get(listPosition).getImgtype(), dataSet.get(listPosition).getImgfull(), "dsfcsf", dataSet.get(listPosition).getTstamp(), "0", dataSet.get(listPosition).getDeletestatus()));
                        Log.d("updatedetails2", dataSet.get(listPosition).getStar());
                        dbmodel = database.getAllafterDelete();
                        adapter.notifyDataSetChanged();
                    } else {
                        holder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_filled));
                        DBHelper dbHelper = new DBHelper(context);

                        dbHelper.updateContact2(new DBModel(dataSet.get(listPosition).getTt_name(), dataSet.get(listPosition).getMsg(), dataSet.get(listPosition).getUrl(), dataSet.get(listPosition).getImgtype(), dataSet.get(listPosition).getImgfull(), "dsfcsf", dataSet.get(listPosition).getTstamp(), "1", dataSet.get(listPosition).getDeletestatus()));
                        Log.d("updatedetails3", dataSet.get(listPosition).getStar());

                        dbmodel = database.getAllafterDelete();
                        adapter.notifyDataSetChanged();
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

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Here you have the received broadcast
            // And if you added extras to the intent get them here too
            // this needs some null checks
            Bundle b = intent.getExtras();
            String yourValue = b.getString("valueName");
            String wish = b.getString("wishdel");

            dbmodel = database.getAllafterDelete();
            database = new DBHelper(getActivity());
            adapter = new NewsListAdapter(dbmodel, getActivity());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
recyclerView.animate();

        }
    }
}
