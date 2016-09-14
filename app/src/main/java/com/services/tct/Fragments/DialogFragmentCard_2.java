package com.services.tct.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.services.tct.R;

public class DialogFragmentCard_2 extends Fragment {

    Typeface typeface;
    boolean isCommit = false;
    private String card = "",pin="";
    public static DialogFragmentCard_2 newInstance() {
        DialogFragmentCard_2 fragmentDemo = new DialogFragmentCard_2();
        return fragmentDemo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "IRANSansMobile(FaNum)_Bold.ttf");
        View v = inflater.inflate(R.layout.get_pay_card, null);
        show_dialog_get_pay_card(v);
        return v;
    }

    public void show_dialog_get_pay_card(View view) {

        final EditText card_ed = (EditText) view.findViewById(R.id.card_Edittext);
        final EditText pin_ed = (EditText) view.findViewById(R.id.passEdittext);
        card_ed.setTypeface(typeface);

        card_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    card_ed.setGravity(Gravity.LEFT);
                    card_ed.setHint("");
                }
            }
        });
        card_ed.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            card_ed.setSelection(card_ed.getText().length());
        }
        });

        pin_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    pin_ed.setGravity(Gravity.LEFT);
                    pin_ed.setHint("");
                }
            }
        });



//        card_ed.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (card_ed.getText().length() == 4 || card_ed.getText().length()-1 == 8 || card_ed.getText().length()-2 == 12 || card_ed.getText().length()-3 == 16 ) {
//                    s.append("-");
//                }
//            }
//        });
        card_ed.setCursorVisible(false);
        card_ed.addTextChangedListener(new TextWatcher() {
            private static final char space = '-';
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });

        view.findViewById(R.id.edge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(DialogFragmentCard_2.this).commit();
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(DialogFragmentCard_2.this).commit();
            }
        });
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card_ed.getText().toString().length() != 19 && pin_ed.getText().toString().length()<3) {
                    Toast.makeText(getActivity(), "شماره کامل نمی باشد!", Toast.LENGTH_SHORT).show();
                } else {
                    isCommit = true;
                    card = card_ed.getText().toString();
                    pin = pin_ed.getText().toString();
                    getActivity().getFragmentManager().beginTransaction().remove(DialogFragmentCard_2.this).commit();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    }
}
