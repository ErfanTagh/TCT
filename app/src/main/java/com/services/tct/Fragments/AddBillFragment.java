package com.services.tct.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.services.tct.MainActivity;
import com.services.tct.Parser.DOMParser;
import com.services.tct.Parser.RSSFeed;
import com.services.tct.Parser.RSSItem;
import com.services.tct.R;
import com.services.tct.Utilities.LocalPersistence;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class AddBillFragment extends Fragment implements View.OnClickListener {

    TextView amountBoard, amount_toman, amount_count;
    RecyclerView recBills;
    ListAdapter adapter;
    RSSFeed _feed;
    long payId = 0, billId = 0, price = 0;
    int count = 0, pos = 0;

    public static AddBillFragment newInstance() {
        AddBillFragment fragmentDemo = new AddBillFragment();
        return fragmentDemo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_bill_fragment, null);
        recBills = (RecyclerView) v.findViewById(R.id.recycler_detail);
        amount_toman = (TextView) v.findViewById(R.id.amount_toman);
        amount_count = (TextView) v.findViewById(R.id.amount_count);
        amountBoard = (TextView) v.findViewById(R.id.amount_board);

        v.findViewById(R.id.actionBar_bill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 00000000");
                FragmentTransaction ft;
                ((MainActivity) getActivity()).isFrag = 3;
                ((MainActivity) getActivity()).darkDialog.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).getPhoneFragment = GetPhoneFragment.newInstance();
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_right);
                ft.add(android.R.id.content, ((MainActivity) getActivity()).getPhoneFragment).commit();


            }
        });

        v.findViewById(R.id.amount_layout).setOnClickListener(this);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "IRANSansMobile(FaNum)_Light.ttf");
        amountBoard.setTypeface(typeface);

        LocalPersistence localPersistence = new LocalPersistence();
        if (localPersistence.readObjectFromFile(getActivity(), "Bill_List") == null) {
            _feed = new RSSFeed();
        } else
            _feed = (RSSFeed) localPersistence.readObjectFromFile(getActivity(), "Bill_List");

        updatePrices();

        if (_feed != null) {
            adapter = new ListAdapter();
            recBills.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recBills.setNestedScrollingEnabled(true);
            recBills.setLayoutManager(llm);
            adapter.notifyDataSetChanged();
        }

        return v;

    }


    public class FeedViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout main_layout;
        protected TextView phone, amount, pay_label, isPayLabel;
        ImageButton edit_more;
        Button paymentButton;

        public FeedViewHolder(View v) {
            super(v);
            phone = (TextView) v.findViewById(R.id.tel_number);
            amount = (TextView) v.findViewById(R.id.amount);
            pay_label = (TextView) v.findViewById(R.id.pay_lable);
            main_layout = (RelativeLayout) v.findViewById(R.id.main_layout);
            edit_more = (ImageButton) v.findViewById(R.id.edit_dot);
            paymentButton = (Button) v.findViewById(R.id.paymentButton);
            isPayLabel = (TextView) v.findViewById(R.id.is_pay_label);
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<FeedViewHolder> {

        public ListAdapter() {
        }

        @Override
        public int getItemCount() {
            return _feed.getItemCount();
        }

        @Override
        public void onBindViewHolder(final FeedViewHolder FeedViewHolder, final int position) {

            final RSSItem fe = _feed.getItem(position);
            FeedViewHolder.phone.setText(fe.getTelNo().toString().substring(2));
            FeedViewHolder.amount.setText(getDividedToman(fe.getAmount()) + "");

            if (fe.getIsPayed())
                FeedViewHolder.isPayLabel.setVisibility(View.VISIBLE);
            else
                FeedViewHolder.isPayLabel.setVisibility(View.GONE);


            if (fe.getIsPayed() || fe.getAmount() == 0) {
                FeedViewHolder.paymentButton.setVisibility(View.GONE);
            }

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
                                        _feed.removeItem(position);
                                        adapter.notifyDataSetChanged();
                                        updatePrices();
                                        new LocalPersistence().writeObjectToFile(getActivity(), _feed, "Bill_List");
                                    } else if (item.getItemId() == R.id.two) {
                                        if (fe.getIsPayed() || fe.getAmount() == 0) {
                                            Toast.makeText(getActivity(), "قبض قبلا پرداخت شده!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            fe.setIsSelected(true);
                                            billId = fe.getBillId();
                                            payId = fe.getPayId();
                                            pos = position;
                                        }
                                    }
                                    return true;
                                }
                            }
                    );
                    popup.show(); //showing popup menu
                }
            });

            FeedViewHolder.paymentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //----------
                    pos = position;
                    ((MainActivity) getActivity()).payBill("00");

                }
            });


        }

        @Override
        public FeedViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_bill, viewGroup, false);
            return new FeedViewHolder(itemView);
        }

    }

    private class getBillInfo extends AsyncTask<String, Void, RSSItem> {

        boolean repeat = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((MainActivity) getActivity()).waitingDialog.setVisibility(View.VISIBLE);
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(((MainActivity) getActivity()).imageView);
            Glide.with(getActivity()).load(R.drawable.gif_loading).into(imageViewTarget);

        }

        @Override
        protected RSSItem doInBackground(String... params) {
            DOMParser domParser = new DOMParser();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isNumberExsict(params[0])) {
                return domParser.getBillInfo(params[0]);
            } else {
                repeat = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(RSSItem result) {
            ((MainActivity) getActivity()).waitingDialog.setVisibility(View.GONE);
            if (result != null) {
                if (result.getAmount().toString().length() > 1) {
                    result.setAmount(Long.valueOf(result.getAmount().toString().substring(0, result.getAmount().toString().length() - 1)));
                }
                Log.e("55555555555", "onPostExecute: " + result.getAmount());
                _feed.addItem(result);
                updatePrices();

                adapter.notifyDataSetChanged();
                new LocalPersistence().writeObjectToFile(getActivity(), _feed, "Bill_List");

            } else if (!repeat) {
                Toast.makeText(getActivity(), "مشکل در برقراری ارتباط!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "شماره تکراری است!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class payBill extends AsyncTask<String, Void, String> {

        String card = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((MainActivity) getActivity()).waitingDialog.setVisibility(View.VISIBLE);
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(((MainActivity) getActivity()).imageView);
            Glide.with(getActivity()).load(R.drawable.gif_loading).into(imageViewTarget);
        }

        @Override
        protected String doInBackground(String... params) {
            DOMParser domParser = new DOMParser();
            card = params[0];
            return domParser.payBill("09127764165", payId, billId, params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            ((MainActivity) getActivity()).waitingDialog.setVisibility(View.GONE);
            if (result != null) {
                if (result.contains("Successful")) {
                    _feed.getItem(pos).setIsPayed(true);
                    _feed.getItem(pos).setPayDate(/*date*/);
                    new LocalPersistence().writeObjectToFile(getActivity(), _feed, "Bill_List");
                    ((MainActivity) getActivity()).sharedPreferences.edit().putString("card", card).apply();
                    updatePrices();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "قبض پرداخت شد", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "مشکل در برقراری ارتباط!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updatePrices() {
        count = 0;
        price = 0;
        for (int i = 0; i < _feed.getItemCount(); i++) {
            if (_feed.getItem(i).getAmount() != 0 && !_feed.getItem(i).getIsPayed()) {
                price += _feed.getItem(i).getAmount();
                count++;
            }
        }

        if (price == 0) {
            amountBoard.setText("شما هیچ قبضی ندارید!");
            amountBoard.setTextSize(20);
            amount_toman.setVisibility(View.GONE);
            amount_count.setVisibility(View.GONE);

        } else {
            amountBoard.setText(getDividedToman(price));
            amountBoard.setTextSize(50);
            amount_toman.setVisibility(View.VISIBLE);
            amount_count.setVisibility(View.VISIBLE);
            amount_count.setText(count + " " + "قبض");
        }
    }

    private String getDividedToman(Long price) {
        if (price == 0) {
            return price + "";
        }
        Log.e("00000000", "getDividedToman: " + price);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < price.toString().length(); i += 3) {
            try {
                if (i == 0)
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 3 - i, price.toString().length() - i));
                else
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 3 - i, price.toString().length() - i) + ",");
            } catch (Exception e) {
                try {
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 2 - i, price.toString().length() - i) + ",");
                } catch (Exception e1) {
                    stringBuilder.insert(0, price.toString().substring(price.toString().length() - 1 - i, price.toString().length() - i) + ",");
                }
            }

            Log.e("00000000", "getDividedToman: " + stringBuilder);
        }
        return stringBuilder.toString();
    }

//    public void removeAllCheck(RSSFeed rssFeed) {
//        for (int i = 0; i <rssFeed.getItemCount() ; i++) {
//            rssFeed.getItem(i).setIsSelected(false);
//        }
//    }

    private boolean isNumberExsict(String tel) {
        for (int i = 0; i < _feed.getItemCount(); i++) {
            if (_feed.getItem(i).getTelNo().toString().equals("21" + tel)) { //Why False ?
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft;
        Log.e(TAG, "onClick: 00000000");
    }

    public void sendPhoneNumber(String phoneNumber) {
        new getBillInfo().execute(phoneNumber);
    }

    public void sendPay(String card, String pin) {
        new payBill().execute(card, pin);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
