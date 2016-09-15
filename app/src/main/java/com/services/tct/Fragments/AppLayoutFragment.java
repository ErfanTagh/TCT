package com.services.tct.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.services.tct.R;

/**
 * Created by erfan on 9/15/2016.
 */

public class AppLayoutFragment extends Fragment {

    Button contactIcon,helpIcon,settingIcon;


    public static AppLayoutFragment newInstance()
    {
        AppLayoutFragment appLayoutFragment=new AppLayoutFragment();
        return appLayoutFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.app_layout_fragment, null);
        contactIcon=(Button) v.findViewById(R.id.contact_icon);
        helpIcon=(Button) v.findViewById(R.id.help_icon);
        settingIcon=(Button) v.findViewById(R.id.setting_icon);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
