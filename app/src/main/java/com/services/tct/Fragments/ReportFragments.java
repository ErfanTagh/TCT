package com.services.tct.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.services.tct.MainActivity;
import com.services.tct.Parser.RSSFeed;
import com.services.tct.Parser.RSSItem;
import com.services.tct.R;
import com.services.tct.Utilities.LocalPersistence;

import java.util.Calendar;
import java.util.Date;

public class ReportFragments extends Fragment {

    RecyclerView recBills;
    ListAdapter adapter;
    RSSFeed _feed = new RSSFeed();


    GraphView graphView;

    public static ReportFragments newInstance() {
        ReportFragments fragmentDemo = new ReportFragments();
        return fragmentDemo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pay_list_fragment, null);
        recBills = (RecyclerView) v.findViewById(R.id.recycler_detail);
        RSSFeed _feed_temp;
        LocalPersistence localPersistence = new LocalPersistence();
        _feed_temp = (RSSFeed) localPersistence.readObjectFromFile(getActivity(), "Bill_List");


        if (_feed_temp != null) {
            for (int i = 0; i < _feed_temp.getItemCount(); i++) {
                if (_feed_temp.getItem(i).getIsPayed()) {
                    _feed.addItem(_feed_temp.getItem(i));
                }
            }
            adapter = new ListAdapter();
            recBills.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recBills.setNestedScrollingEnabled(true);
            recBills.setLayoutManager(llm);
            adapter.notifyDataSetChanged();
        }

        GraphView graphView = (GraphView) v.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(generateData());
        series.setColor(Color.BLUE);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(4);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity().getApplicationContext(),"Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        graphView.addSeries(series);
        graphView.setTitle("نمودار قبض ها");
//        graphView.setTitleColor(Color.BLACK);
//        graphView.setTitleTextSize(25f);



        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(getMaxFeed());
        graphView.getViewport().setScrollable(true);
        graphView.getGridLabelRenderer().setGridColor(Color.BLACK);

        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(_feed.getItemCount());

        graphView.getViewport().setXAxisBoundsManual(true);
//        graphView.getViewport().setMinX(0);
//        graphView.getViewport().setMaxX(40);





        graphView.getGridLabelRenderer().setHumanRounding(false);






//        ((MainActivity) getActivity()).selected_report.setVisibility(View.VISIBLE);
        return v;
    }




    public class FeedViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout main_layout;
        protected TextView phone, amount, invoice;
        ImageButton edit_more;

        public FeedViewHolder(View v) {
            super(v);
            main_layout = (RelativeLayout) v.findViewById(R.id.main_layout);
            edit_more = (ImageButton) v.findViewById(R.id.edit_dot);
            phone = (TextView) v.findViewById(R.id.tel_number);
            invoice = (TextView) v.findViewById(R.id.invoice);
            amount = (TextView) v.findViewById(R.id.amount);
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<FeedViewHolder> {

        public ListAdapter() {}

        @Override
        public int getItemCount() {
            return _feed.getItemCount();
        }

        @Override
        public void onBindViewHolder(final FeedViewHolder FeedViewHolder, final int position) {

            final RSSItem fe = _feed.getItem(position);
            FeedViewHolder.phone.setText(fe.getTelNo().toString().substring(2));
            FeedViewHolder.amount.setText(getDividedToman(fe.getAmount()) + "");
            FeedViewHolder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            FeedViewHolder.edit_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(getActivity(), FeedViewHolder.edit_more);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(
                            new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {
                                    if (item.getItemId() == R.id.one) {
                                    } else if (item.getItemId() == R.id.two) {
                                    }
                                    return true;
                                }
                            }
                    );
                    popup.show(); //showing popup menu
                }
            });
        }

        @Override
        public FeedViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_bill_report, viewGroup, false);
            return new FeedViewHolder(itemView);
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
////        ((MainActivity) getActivity()).addCard.setVisibility(View.GONE);
////        ((MainActivity) getActivity()).pay_text.setVisibility(View.VISIBLE);
//        ((MainActivity) getActivity()).gift.setVisibility(View.VISIBLE);
//        ((MainActivity) getActivity()).selected_report.setVisibility(View.GONE);
//    }



    private DataPoint[] generateData() {

        int count = _feed.getItemCount();
        Calendar calendar = Calendar.getInstance();
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<_feed.getItemCount(); i++) {
            Date x = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            double y = _feed.getItem(i).getAmount();
            DataPoint v = new DataPoint(x, y);
            values[i] = v;

        }
        return values;
    }

    public double getMaxFeed(){


//        int count = _feed.getItemCount();
        double max=0;
        for (int i=0; i<_feed.getItemCount(); i++) {

            if (_feed.getItem(i).getAmount()>max)
            {
                max=_feed.getItem(i).getAmount();
            }
        }
        return max;
    }

    private String getDividedToman(Long price) {
        if (price == 0) {
            return price+"";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < price.toString().length(); i+=3) {
            try {
                if (i==0)
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 3 - i, price.toString().length() - i));
                else
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 3 - i, price.toString().length()-i)+",");
            } catch (Exception e) {
                try {
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 2 - i, price.toString().length()-i)+",");
                } catch (Exception e1) {
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 1 - i, price.toString().length()-i)+",");
                }
            }

        }
        return stringBuilder.toString();
    }



}
