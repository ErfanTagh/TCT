package com.services.tct;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.services.tct.Parser.DOMParser;

public class Splash extends AppCompatActivity {

    String phone_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.splash_gif).into(imageViewTarget);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
//                show_first_regPage();
            }
        }, 3200);
    }

    private void show_first_regPage() {
        setContentView(R.layout.registration);
        final EditText phone_ed = (EditText) findViewById(R.id.phone_Edittext);
        phone_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    findViewById(R.id.hint_text).setVisibility(View.INVISIBLE);
                    ((EditText) findViewById(R.id.phone_Edittext)).setGravity(Gravity.LEFT| Gravity.CENTER);
                    findViewById(R.id.pre_text).setVisibility(View.VISIBLE);
                }
            }
        });

        phone_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("8888888", "onTextChanged: ");
                if (phone_ed.getText().length() == 9) {
                    findViewById(R.id.submit_layout).setBackgroundResource(R.drawable.register_bg_ed5);
                    findViewById(R.id.submit_layout).setAlpha(1);
                    hideKey(phone_ed);
                } else if (phone_ed.getText().length() < 10) {
                    findViewById(R.id.submit_layout).setBackgroundResource(R.drawable.register_bg_ed4);
                    findViewById(R.id.submit_layout).setAlpha(0.26f);
                }
                if (phone_ed.getText().length() > 0) {
                    findViewById(R.id.hint_text).setVisibility(View.INVISIBLE);
                    ((EditText) findViewById(R.id.phone_Edittext)).setGravity(Gravity.LEFT | Gravity.CENTER);
                    findViewById(R.id.pre_text).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.submit_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone_ed.getText().length() == 9) {
                    if (!validate_number(phone_ed.getText().toString())) {
                        Toast.makeText(Splash.this, "شماره صحیح نمی باشد!", Toast.LENGTH_SHORT).show();
                    } else {
                        phone_number = phone_ed.getText().toString();
                        if (Build.VERSION.SDK_INT >= 23) {
                            checkPermissions();
                        } else
                            new registration().execute();
                    }
                } else {
                    Toast.makeText(Splash.this, "ابتدا شماره خود را وارد کنید!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void hideKey(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class registration extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.wait_layout).setVisibility(View.VISIBLE);
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget((ImageView) findViewById(R.id.image_view));
            Glide.with(Splash.this).load(R.drawable.gif_loading).into(imageViewTarget);
        }

        @Override
        protected String doInBackground(String... params) {
            DOMParser domParser = new DOMParser();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            return domParser.register(phone_number, telephonyManager.getDeviceId(), "09129638520912963852");
        }

        @Override
        protected void onPostExecute(String result) {
            findViewById(R.id.wait_layout).setVisibility(View.GONE);
            if (result != null) {
                Log.e("55555555555", "onPostExecute: " + result);
                if (result.length() == 3) {
                    showCodePage(result);
                }
            } else
                Toast.makeText(Splash.this, "مشکل در برقراری ارتباط!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermissions() {
        ActivityCompat.requestPermissions(Splash.this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
    }

    boolean hasPermission = true;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            // for each permission check if the user grantet/denied them
            // you may want to group the rationale in a single dialog,
            // this is just an example
            if (ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, Manifest.permission.READ_PHONE_STATE)) {
                    Log.e("2222222222", "onRequestPermissionsResult: ");
                    hasPermission = false;
                }
                Log.e("11111111", "onRequestPermissionsResult: ");
                hasPermission = false;
            }
            Log.e("00000", "onRequestPermissionsResult: ");
            if (hasPermission) {
                new registration().execute();
            }
        }
    }

    private void showCodePage(final String code) {
        setContentView(R.layout.registration1);

        final EditText phone_ed = (EditText) findViewById(R.id.code_Edittext);
        phone_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    findViewById(R.id.hint_text).setVisibility(View.INVISIBLE);
                    phone_ed.setGravity(Gravity.LEFT| Gravity.CENTER);
                }
            }
        });

        phone_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phone_ed.getText().length() == 9) {
                    findViewById(R.id.submit_layout).setBackgroundResource(R.drawable.register_bg_ed5);
                    findViewById(R.id.submit_layout).setAlpha(1);
                    hideKey(phone_ed);
                } else if (phone_ed.getText().length() < 10) {
                    findViewById(R.id.submit_layout).setBackgroundResource(R.drawable.register_bg_ed4);
                    findViewById(R.id.submit_layout).setAlpha(0.26f);
                }
                if (phone_ed.getText().length() > 0) {
                    findViewById(R.id.hint_text).setVisibility(View.INVISIBLE);
                    phone_ed.setGravity(Gravity.LEFT | Gravity.CENTER);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        final Chronometer mChronometer = (Chronometer) findViewById(R.id.time_label);
        mChronometer.start();
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (mChronometer.getText().equals("02:00")) {
//                    finish();
                }
            }
        });

        findViewById(R.id.submit_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText) findViewById(R.id.code_Edittext)).getText().toString().equals(code)) {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(Splash.this, "کد فعال سازی صحیح نمی باشد!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validate_number(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != '0' && number.charAt(i) != '1' && number.charAt(i) != '2' && number.charAt(i) != '3'
                    && number.charAt(i) != '4' && number.charAt(i) != '5' && number.charAt(i) != '6' && number.charAt(i) != '7'
                    && number.charAt(i) != '8' && number.charAt(i) != '9') {
                return false;
            }
        }
        return true;
    }

}
