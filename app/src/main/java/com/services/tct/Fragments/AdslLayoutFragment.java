package com.services.tct.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.services.tct.MainActivity;
import com.services.tct.R;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by erfan on 9/15/2016.
 */

public class AdslLayoutFragment extends Fragment {

    RelativeLayout downButton;

    public static AdslLayoutFragment newInstance()
    {
        AdslLayoutFragment adslLayoutFragment=new AdslLayoutFragment();
        return adslLayoutFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adsl_layout_fragment, null);
        downButton=(RelativeLayout) v.findViewById(R.id.down_button);

        v.findViewById(R.id.telephone_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 00000000" );
                FragmentTransaction ft;
                ((MainActivity)getActivity()).isFrag = 3;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ((MainActivity)getActivity()).addBill.setElevation(0);
//                }
                ((MainActivity)getActivity()).darkDialog.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).getPhoneFragment = GetPhoneFragment.newInstance();
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
                ft.add(android.R.id.content, ((MainActivity)getActivity()).getPhoneFragment).commit();
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
