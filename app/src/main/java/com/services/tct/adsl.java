package com.services.tct;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.services.tct.Fragments.DialogFragmentCard_2;

public class adsl extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adsl_main);


//        findViewById(R.id.pay_layout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft;
//                DialogFragmentCard_2 dialogFragmentCard = DialogFragmentCard_2.newInstance();
//                ft = getFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
//                ft.add(android.R.id.content, dialogFragmentCard).commit();
//            }
//        });


    }

}
