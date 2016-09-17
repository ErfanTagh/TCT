package com.services.tct.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.services.tct.MainActivity;
import com.services.tct.R;

public class GetPhoneFragment extends Fragment {

    Typeface typeface;
    boolean isCommit = false;
    private String phone = "";
//    private int _xDelta;
//    private int _yDelta;


    public static GetPhoneFragment newInstance() {
        GetPhoneFragment fragmentDemo = new GetPhoneFragment();
        return fragmentDemo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "IRANSansMobile(FaNum)_Bold.ttf");
        View v = inflater.inflate(R.layout.get_phone_dialog, null);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        show_dialog_get_phone(v);
        return v;

    }

    public void show_dialog_get_phone(final View view) {

        final EditText phone_ed = (EditText) view.findViewById(R.id.phoneEdittext);
        phone_ed.setTypeface(typeface);

        phone_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    view.findViewById(R.id.pre_text).setVisibility(View.VISIBLE);
                    phone_ed.setGravity(Gravity.LEFT);
                    phone_ed.setHint("");
                }
            }
        });

        phone_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phone_ed.getText().length() == 8) {
                    view.findViewById(R.id.edittext_back).setBackgroundResource(R.drawable.register_bg_ed_stroke);
                    hideKey(view);
                } else {
                    view.findViewById(R.id.edittext_back).setBackgroundResource(R.drawable.register_bg_ed);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone_ed.requestFocus();
        /*view.findViewById(R.id.edge).setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
            }

        });*/

        /*view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
//                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.rightMargin = -X + _xDelta;
                        layoutParams.leftMargin = X - _xDelta;
//                        layoutParams.topMargin = Y - _yDelta;
//                        layoutParams.rightMargin = -200;
//                        layoutParams.bottomMargin = -200;
                        view.setLayoutParams(layoutParams);
                        break;
                }
//                view.invalidate();
                return false;
            }
        });*/


        view.findViewById(R.id.edge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKey(view);
                getActivity().getFragmentManager().beginTransaction().remove(GetPhoneFragment.this).commit();
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKey(v);
                getActivity().getFragmentManager().beginTransaction().remove(GetPhoneFragment.this).commit();
            }
        });
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone_ed.getText().toString().length() != 8) {
                    Toast.makeText(getActivity(), "شماره کامل نمی باشد!", Toast.LENGTH_SHORT).show();
                } else {
                    isCommit = true;
                    phone = phone_ed.getText().toString();
                    getActivity().getFragmentManager().beginTransaction().remove(GetPhoneFragment.this).commit();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).darkDialog.setVisibility(View.VISIBLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ((MainActivity)getActivity()).addBill.setElevation(0);
//        }
    }

    public void hideKey(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ((MainActivity)getActivity()).addBill.setElevation(5);
//        }
        ((MainActivity) getActivity()).isFrag = 0;

        if (isCommit) {
            ((MainActivity)getActivity()).addBillFragment.sendPhoneNumber(phone);
        }
        ((MainActivity) getActivity()).darkDialog.setVisibility(View.GONE);
    }
}
