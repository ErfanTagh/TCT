package com.services.tct;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.services.tct.Fragments.AddBillFragment;
import com.services.tct.Fragments.AdslLayoutFragment;
import com.services.tct.Fragments.AppLayoutFragment;
import com.services.tct.Fragments.CardListFragment;
import com.services.tct.Fragments.GetPhoneFragment;
import com.services.tct.Fragments.GetCardFragment;
import com.services.tct.Fragments.ReportFragments;
import com.services.tct.Fragments.ServicesLayoutFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public RelativeLayout darkDialog, waitingDialog;
    DrawerLayout drawer;
    public int isFrag = 0;

    public RelativeLayout pay_layout;
    public SharedPreferences sharedPreferences;
    public GetPhoneFragment getPhoneFragment;
    public GetCardFragment getCardFragment;
    public AddBillFragment addBillFragment;
    public ReportFragments reportFragments;
    public ServicesLayoutFragment servicesLayoutFragment;
    public AppLayoutFragment appLayoutFragment;
    public AdslLayoutFragment adslLayoutFragment;
    public ImageView imageView, selected_card;
    public Button gift,reportList,reportList2,reportList3,reportList4,reportList5;
    public TextView appTextView,billTextView,servicesTextView,adslTextView,accountingTextView;

    public LinearLayout appLayout,billLayout,servicesLayout,adslLayout,accountingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        darkDialog = (RelativeLayout) findViewById(R.id.dark_dialog);
        sharedPreferences = getSharedPreferences("TCT", 0);
        waitingDialog = (RelativeLayout) findViewById(R.id.wait_layout);
        gift = (Button) findViewById(R.id.gift_open);
        imageView = (ImageView) findViewById(R.id.image_view);

        appTextView=(TextView) findViewById(R.id.app_textview);
        billTextView=(TextView) findViewById(R.id.bill_textview);
        servicesTextView=(TextView) findViewById(R.id.services_textview);
        adslTextView=(TextView) findViewById(R.id.adsl_textview);
        accountingTextView = (TextView) findViewById(R.id.accounting_textview);






        appLayout=(LinearLayout) findViewById(R.id.app_layout);
        billLayout=(LinearLayout) findViewById(R.id.bill_layout);
        servicesLayout=(LinearLayout) findViewById(R.id.services_layout);
        adslLayout=(LinearLayout) findViewById(R.id.adsl_layout);
        accountingLayout=(LinearLayout) findViewById(R.id.accounting_layout);

        reportList=(Button) findViewById(R.id.report_list);
        reportList2 = (Button) findViewById(R.id.report_list2);
        reportList3 = (Button) findViewById(R.id.report_list3);
        reportList4 = (Button) findViewById(R.id.report_list4);
        reportList5 = (Button) findViewById(R.id.report_list5);

        FragmentManager fm = getFragmentManager();
        if (fm != null) { //?
            addBillFragment = new AddBillFragment().newInstance();
            fm.beginTransaction().replace(R.id.detail_fragment, addBillFragment).addToBackStack(null).commit();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_layout:
                appLayoutFragment = new AppLayoutFragment().newInstance();
                getFragmentManager().beginTransaction().replace(R.id.detail_fragment, appLayoutFragment).addToBackStack(null).commit();

                appLayout.setAlpha((float) 1);
                reportList.setBackgroundResource(R.drawable.app_sell_bottom_ic);
                appTextView.setTextColor(Color.parseColor("#673ab7"));


                reportList2.setBackgroundResource(R.drawable.bill_bottom_ic);
                billTextView.setTextColor(Color.parseColor("#000000"));
                billLayout.setAlpha((float) 0.45);


                reportList3.setBackgroundResource(R.drawable.services_bottom_ic);
                servicesTextView.setTextColor(Color.parseColor("#000000"));
                servicesLayout.setAlpha((float) 0.45);


                reportList4.setBackgroundResource(R.drawable.adsl_bottom_ic);
                adslTextView.setTextColor(Color.parseColor("#000000"));
                adslLayout.setAlpha((float) 0.45);


                reportList5.setBackgroundResource(R.drawable.accounting_bottom_ic);
                accountingTextView.setTextColor(Color.parseColor("#000000"));
                accountingLayout.setAlpha((float) 0.45);

                break;
            case R.id.bill_layout:

                addBillFragment = new AddBillFragment().newInstance();
                getFragmentManager().beginTransaction().replace(R.id.detail_fragment, addBillFragment).addToBackStack(null).commit();


                reportList.setBackgroundResource(R.drawable.app_bottom_ic);
                appTextView.setTextColor(Color.parseColor("#000000"));
                appLayout.setAlpha((float) 0.45);

                billLayout.setAlpha((float) 1);
                reportList2.setBackgroundResource(R.drawable.bill_sell_bottom_ic);
                billTextView.setTextColor(Color.parseColor("#673ab7"));


                reportList3.setBackgroundResource(R.drawable.services_bottom_ic);
                servicesTextView.setTextColor(Color.parseColor("#000000"));
                servicesLayout.setAlpha((float) 0.45);


                reportList4.setBackgroundResource(R.drawable.adsl_bottom_ic);
                adslTextView.setTextColor(Color.parseColor("#000000"));
                adslLayout.setAlpha((float) 0.45);


                reportList5.setBackgroundResource(R.drawable.accounting_bottom_ic);
                accountingTextView.setTextColor(Color.parseColor("#000000"));
                accountingLayout.setAlpha((float) 0.45);

                break;

            case R.id.services_layout:

                servicesLayoutFragment = new ServicesLayoutFragment().newInstance();
                getFragmentManager().beginTransaction().replace(R.id.detail_fragment, servicesLayoutFragment).addToBackStack(null).commit();


                reportList.setBackgroundResource(R.drawable.app_bottom_ic);
                appTextView.setTextColor(Color.parseColor("#000000"));
                appLayout.setAlpha((float) 0.45);


                reportList2.setBackgroundResource(R.drawable.bill_bottom_ic);
                billTextView.setTextColor(Color.parseColor("#000000"));
                billLayout.setAlpha((float) 0.45);

                servicesLayout.setAlpha((float) 1);
                reportList3.setBackgroundResource(R.drawable.services_sell_bottom_ic);
                servicesTextView.setTextColor(Color.parseColor("#673ab7"));


                reportList4.setBackgroundResource(R.drawable.adsl_bottom_ic);
                adslTextView.setTextColor(Color.parseColor("#000000"));
                adslLayout.setAlpha((float) 0.45);


                reportList5.setBackgroundResource(R.drawable.accounting_bottom_ic);
                accountingTextView.setTextColor(Color.parseColor("#000000"));
                accountingLayout.setAlpha((float) 0.45);


                break;

            case R.id.adsl_layout:

                adslLayoutFragment = new AdslLayoutFragment().newInstance();
                getFragmentManager().beginTransaction().replace(R.id.detail_fragment, adslLayoutFragment).addToBackStack(null).commit();


                reportList.setBackgroundResource(R.drawable.app_bottom_ic);
                appTextView.setTextColor(Color.parseColor("#000000"));
                appLayout.setAlpha((float) 0.45);

                billLayout.setAlpha((float) 0.45);
                reportList2.setBackgroundResource(R.drawable.bill_bottom_ic);
                billTextView.setTextColor(Color.parseColor("#000000"));


                reportList3.setBackgroundResource(R.drawable.services_bottom_ic);
                servicesTextView.setTextColor(Color.parseColor("#000000"));
                servicesLayout.setAlpha((float) 0.45);

                adslLayout.setAlpha((float) 1);
                reportList4.setBackgroundResource(R.drawable.adsl_sell_bottom_ic);
                adslTextView.setTextColor(Color.parseColor("#673ab7"));


                reportList5.setBackgroundResource(R.drawable.accounting_bottom_ic);
                accountingTextView.setTextColor(Color.parseColor("#000000"));
                accountingLayout.setAlpha((float) 0.45);

                break;

            case R.id.accounting_layout:

                reportFragments = new ReportFragments().newInstance();
                getFragmentManager().beginTransaction().replace(R.id.detail_fragment, reportFragments).addToBackStack(null).commit();


                reportList.setBackgroundResource(R.drawable.app_bottom_ic);
                appTextView.setTextColor(Color.parseColor("#000000"));
                appLayout.setAlpha(0.45f);


                reportList2.setBackgroundResource(R.drawable.bill_bottom_ic);
                billTextView.setTextColor(Color.parseColor("#000000"));
                billLayout.setAlpha(0.45f);


                reportList3.setBackgroundResource(R.drawable.services_bottom_ic);
                servicesTextView.setTextColor(Color.parseColor("#000000"));
                servicesLayout.setAlpha((float) 0.45);


                reportList4.setBackgroundResource(R.drawable.adsl_bottom_ic);
                adslTextView.setTextColor(Color.parseColor("#000000"));
                adslLayout.setAlpha((float) 0.45);

                accountingLayout.setAlpha((float) 1);
                reportList5.setBackgroundResource(R.drawable.accounting_sell_bottom_ic);
                accountingTextView.setTextColor(Color.parseColor("#673ab7"));

                break;

            default:
                break;

        }
    }

    public void payBill(String billId) {
        darkDialog.setVisibility(View.VISIBLE);
        getCardFragment = GetCardFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_right);
        ft.add(android.R.id.content, getCardFragment).addToBackStack(null).commit();
    }


    @Override
    public void onBackPressed() {
        Log.e("0000000000", "onBackPressed: " + isFrag);
        if (isFrag == 1 || isFrag == 4) {
            isFrag = 0;
            getFragmentManager().beginTransaction().replace(R.id.detail_fragment, addBillFragment).addToBackStack(null).commit();
        } else if (isFrag == 2) {
            isFrag = 0;
            getFragmentManager().beginTransaction().remove(getCardFragment).commit();
        } else if (isFrag == 3) {
            isFrag = 0;
            getFragmentManager().beginTransaction().remove(getPhoneFragment).commit();
        } else
            finish();
    }

}
