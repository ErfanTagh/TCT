package com.services.tct;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.services.tct.Fragments.AddBillFragment;
import com.services.tct.Fragments.CardListFragment;
import com.services.tct.Fragments.GetPhoneFragment;
import com.services.tct.Fragments.GetCardFragment;
import com.services.tct.Fragments.ReportFragments;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public RelativeLayout darkDialog, waitingDialog;
    DrawerLayout drawer;
    public int isFrag = 0;

    public RelativeLayout pay_layout;
    public SharedPreferences sharedPreferences;
    public GetPhoneFragment getPhoneFragment;
    public GetCardFragment getCardFragment;
    public AddBillFragment addBillFragment;
    public CardListFragment cardListFragment;
    public ReportFragments reportFragments;
    public ImageView imageView, selected_card, selected_report;
    public CardView addBill;
    public Button gift;
    public TextView pay_text;
    public boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        darkDialog = (RelativeLayout) findViewById(R.id.dark_dialog);
        sharedPreferences = getSharedPreferences("TCT", 0);
        waitingDialog = (RelativeLayout) findViewById(R.id.wait_layout);
//        addCard = (Button) findViewById(R.id.add_card_button);
        gift = (Button) findViewById(R.id.gift_open);
        pay_text = (TextView) findViewById(R.id.pay_text);
        pay_layout = (RelativeLayout) findViewById(R.id.pay_layout);
        imageView = (ImageView) findViewById(R.id.image_view);
//        selected_card = (ImageView) findViewById(R.id.selected_card_back);
//        selected_report = (ImageView) findViewById(R.id.selected_card_back2);
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "IRANSansMobile(FaNum)_Bold.ttf");
//        pay_text.setTypeface(typeface2);

        FragmentManager fm = getFragmentManager();
        if (fm != null) { //?
            addBillFragment = new AddBillFragment().newInstance();
            fm.beginTransaction().replace(R.id.detail_fragment, addBillFragment).addToBackStack(null).commit();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_layout:
                if (isCheck) {
                    isFrag = 2;
                    //----------
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        addBill.setElevation(0);
                    }
                    darkDialog.setVisibility(View.VISIBLE);
                    getCardFragment = GetCardFragment.newInstance();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
                    ft.add(android.R.id.content, getCardFragment).commit();//?
                } else
                    Toast.makeText(MainActivity.this, "قبضی انتخاب نشده!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_open:
                drawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.adslLA: //?
            case R.id.adsText:
                drawer.closeDrawer(Gravity.RIGHT);
                startActivity(new Intent(MainActivity.this, adsl.class));
                break;
            case R.id.card_list:
                if (isFrag != 1) {
                    if (isCheck) { //?
                        pay_layout.animate().translationY(0).scaleX((float) 1).scaleY((float) 1).setDuration(250);
                        isCheck = false;
                    }
                    gift.setVisibility(View.INVISIBLE);
                    cardListFragment = new CardListFragment().newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.detail_fragment, cardListFragment).addToBackStack(null).commit();
                    isFrag = 1;
                }
                break;
            case R.id.report_list:
                if (isFrag != 4) {
                    if (isCheck) {
                        pay_layout.animate().translationY(0).scaleX((float) 1).scaleY((float) 1).setDuration(250);
                        isCheck = false;
                    }
                    gift.setVisibility(View.INVISIBLE);
                    reportFragments = new ReportFragments().newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.detail_fragment, reportFragments).addToBackStack(null).commit();
                    isFrag = 4;
                }
                break;
            case R.id.gift_open:
                drawer.closeDrawer(Gravity.RIGHT);
                startActivity(new Intent(MainActivity.this, Gift.class));
                break;
            case R.id.problemLA: //?
            case R.id.problemText:
                drawer.closeDrawer(Gravity.RIGHT);
                startActivity(new Intent(MainActivity.this, problem.class));
                break;
            case R.id.internationalLA:
            case R.id.internationalText:
                drawer.closeDrawer(Gravity.RIGHT);
                startActivity(new Intent(MainActivity.this, international.class));
                break;
            case R.id.exitLA:
                drawer.closeDrawer(Gravity.RIGHT);
                finish();
                break;
            default:
                break;

        }
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
