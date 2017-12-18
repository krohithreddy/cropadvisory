package com.hhcl.cropadvisory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.afollestad.sectionedrecyclerview.SectionedViewHolder;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import static java.util.stream.Collectors.toMap;

/**
 * Created by Karthik Kumar K on 17-11-2017.
 */

public class HomeFragment extends Fragment {
    HomeFragmentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    ArrayList<DBModel> dbmodel;
    Typeface font;
    private List<String> selectedIds = new ArrayList<>();
    MenuItem delitem;
    MenuItem item;
    public ArrayList<Integer> selectedItems = new ArrayList<Integer>();
    private boolean isMultiSelect = false;
    DBHelper database;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String s = "invisible";
    String longsele = "no";
    private ActionMode actionMode;
    String formatedate,finalString,ttstamp;
    ArrayList<DBModel> finalarray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        View view = inflater.inflate(R.layout.homefragment, container, false);
        finalarray = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        database = new DBHelper(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setHasOptionsMenu(true);

        dbmodel = new ArrayList<DBModel>();
        dbmodel = database.getAllafterDelete();

        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        String msgregId = pref.getString("msgg", "");

        font = Typeface.createFromAsset(getContext().getAssets(), "ramabhadra.ttf");
        adapter = new HomeFragmentAdapter(dbmodel, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerviewTouchlistener(getActivity(), recyclerView, new RecyclerviewTouchlistener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                if (isMultiSelect) {
                    //if multiple selection is enabled then select item on single click else perform normal click on item.
                    //multiSelect(position);
                }

                DBModel movie = dbmodel.get(position);
                // Toast.makeText(getActivity(), movie.getTt_name() + " is selected!", Toast.LENGTH_SHORT).show();
                DBHelper database = new DBHelper(getActivity());
                ArrayList<DBModel> deletelist = new ArrayList<DBModel>();
                DBModel dmode = new DBModel();
                Fragment fragment = new Detailedfragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
                Bundle args = new Bundle();
                args.putSerializable("YourKey", deletelist);
                args.putInt("position", position);
                Log.d("positionint1", String.valueOf(position));
                fragment.setArguments(args);

            }

            @Override
            public void onLongClick(View view, int position) {

                longsele = "selected";
                item.setVisible(false);
                delitem.setVisible(true);

                CardView cardd = (CardView) view.findViewById(R.id.card_view);
                ImageView yesiconn = (ImageView) view.findViewById(R.id.imageVi);

                DBModel movie = dbmodel.get(position);
                //Toast.makeText(getActivity(),  movie.getTt_name(), Toast.LENGTH_SHORT).show();

                if (!isMultiSelect) {
                    selectedIds = new ArrayList<>();
                    isMultiSelect = true;
                }
                String id = movie.getImgfull();
                if (selectedIds.contains(id)) {
                    //if item is selected then,set foreground color of FrameLayout.
                    selectedIds.remove(id);
                    cardd.setBackgroundColor(getResources().getColor(android.R.color.white));
                    yesiconn.setVisibility(view.GONE);
                } else {
                    selectedIds.add(movie.getImgfull());
                    yesiconn.setVisibility(view.VISIBLE);
                    cardd.setBackgroundColor(getResources().getColor(R.color.backrec));
                }
                Log.d("selectedid", selectedIds.toString());
                if (selectedIds == null) {
                    yesiconn.setVisibility(view.VISIBLE);
                    delitem.setVisible(false);
                }
            }
        }));

        Calendar currentdate = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        formatedate = df.format(currentdate.getTime());
        Log.d("newtimestamp1", formatedate);

        if (adapter.getItemCount() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                recyclerView.setBackground(getResources().getDrawable(R.drawable.emptyscreen));
            } else {
                recyclerView.setBackground(null);
            }
        }
        return view;
    }

    private class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolde> {
        private ArrayList<DBModel> dataSet;
        ArrayList<DBModel> mAllData = new ArrayList<DBModel>();
        Context context;
        private List<String> selectedIds = new ArrayList<>();


        public HomeFragmentAdapter(ArrayList<DBModel> dbmodel, FragmentActivity activity) {
            this.dataSet = dbmodel;
            this.context = activity;
            this.mAllData.addAll(dataSet);
        }

        @Override
        public HomeFragmentAdapter.MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.homecardview, parent, false);


            final HomeFragmentAdapter.MyViewHolde myViewHolder = new HomeFragmentAdapter.MyViewHolde(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final HomeFragmentAdapter.MyViewHolde holder, final int listPosition) {
            TextView textViewName = holder.textViewName;
            TextView textViewVersion = holder.textViewVersion;
            ImageView imageView = holder.imageViewIcon;
            final DBModel model = dataSet.get(listPosition);
            textViewName.setText(dataSet.get(listPosition).getTt_name());
            textViewVersion.setText(dataSet.get(listPosition).getMsg());
            String tstamp = dataSet.get(listPosition).getTstamp();


            holder.star.setClickable(false);
            textViewName.setTypeface(font);
            textViewVersion.setTypeface(font);
            Picasso.with(context).load(model.getUrl()).resize(120, 60).into(imageView);
            String id = dataSet.get(listPosition).getImgfull();

            if (model.getStar().equals("1")) {
                holder.star.setVisibility(View.VISIBLE);
                holder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            }

            if (model.getTt_name().equalsIgnoreCase("") && model.getMsg().equalsIgnoreCase("")) {
                Log.d("nullll", model.getMsg() + model.getTt_name());
                holder.rr.setVisibility(View.GONE);
//                holder.itemView.setMinimumHeight(0);
                holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));

//                adapter.notifyDataSetChanged();
                //holder.card.setCardBackgroundColor(getResources().getColor(R.color.black));
                // adapter.notifyDataSetChanged();
            }
            Log.d("newtimestamp2", dbmodel.get(listPosition).getTstamp());
             ttstamp = dbmodel.get(listPosition).getTstamp();

            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

            //Date date = null;
            try {
               Date date = (Date)formatter.parse(ttstamp);
                Log.d("newtimestamp33", date.toString());
                DateFormat newFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.UK);
                 finalString = newFormat.format(date);
                Log.d("newtimestamp33", finalString);

            } catch (ParseException e) {
                e.printStackTrace();
            }




        }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            dataSet.clear();
            if (charText.length() == 0) {
                dataSet.addAll(mAllData);
            } else {
                for (DBModel wp : mAllData) {
                    if (wp.getTt_name().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        dataSet.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public void setSelectedIds(List<String> selectedIds) {
            this.selectedIds = selectedIds;
            notifyDataSetChanged();
        }


        public class MyViewHolde extends RecyclerView.ViewHolder {
            TextView textViewName;
            TextView textViewVersion, headdate;
            ImageView imageViewIcon, yesicon, star;
            CardView card;
            RelativeLayout rr;

            public MyViewHolde(final View itemView) {
                super(itemView);
                this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
                this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
                this.yesicon = (ImageView) itemView.findViewById(R.id.imageVi);
                this.card = (CardView) itemView.findViewById(R.id.card_view);
                this.headdate = (TextView) itemView.findViewById(R.id.ddate);
                this.star = (ImageView) itemView.findViewById(R.id.star);
                this.rr = (RelativeLayout) itemView.findViewById(R.id.rel);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        // if (s.equals("")){
        delitem = menu.findItem(R.id.action_delete);

        delitem.setVisible(false);


        delitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String[] ids = {String.valueOf(selectedIds)};
                if (selectedIds != null) {
                    deleteAll(selectedIds);
                }

                return false;
            }
        });
        item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        final EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.parseColor("#000000"));
        searchEditText.setHintTextColor(Color.parseColor("#000000"));
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  adapter.getFilter(s);
                String text = searchEditText.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void deleteAll(List<String> selectedIds) {
        for (int i = 0; i < selectedIds.size(); i++) {
            database.updateContact(selectedIds.get(i));
        }
        delitem.setVisible(false);
        item.setVisible(true);
        dbmodel = database.getAllafterDelete();
        adapter = new HomeFragmentAdapter(dbmodel, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();

        mRegistrationBroadcastReceiver = new MyBroadcastReceiver();
        final IntentFilter intentFilter = new IntentFilter("YourAction");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver, intentFilter);

        adapter.notifyDataSetChanged();
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            String yourValue = b.getString("valueName");
            //Toast.makeText(getActivity(),yourValue,10).show();
            DBHelper database = new DBHelper(getActivity());
            dbmodel = database.getAllafterDelete();
            database = new DBHelper(getActivity());
            adapter = new HomeFragmentAdapter(dbmodel, getActivity());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //  recyclerView.setBackground(null);
        }
    }


}
