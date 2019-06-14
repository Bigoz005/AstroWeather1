package com.example.astroweather;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int TABLET_DP = 600;

    boolean loop = true;
    TextView timer;
    PagerAdapter myPagerAdapter;
    ViewPager myViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timer = findViewById(R.id.timer);

        if (!dpOver600(this)) {
            myViewPager = findViewById(R.id.ViewPager);
            myPagerAdapter = new PagerAdapter(getSupportFragmentManager());
            myViewPager.setAdapter(myPagerAdapter);
            clock();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        loop = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        loop = true;
        clock();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                setUpCustomDialog();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        final EditText longitude = dialog.findViewById(R.id.longitude);
        final EditText latitude = dialog.findViewById(R.id.latitude);
        Button submit = dialog.findViewById(R.id.enter);
        Spinner mySpinner = dialog.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(this, R.array.spiner, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (Double.valueOf(longitude.getText().toString()) > -180 && Double.valueOf(longitude.getText().toString()) < 180 && Double.valueOf(latitude.getText().toString()) > -90 && Double.valueOf(latitude.getText().toString()) < 90) {
                        Settings.latitude = Double.valueOf(latitude.getText().toString());
                        Settings.longitude = Double.valueOf(longitude.getText().toString());
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int option, long id) {
        switch (option) {
            case 0:
                Settings.updateIterator = 5;
                Toast.makeText(parent.getContext(), "Updated refresh rate to: " + Settings.updateIterator, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Settings.updateIterator = 10;
                Toast.makeText(parent.getContext(), "Updated refresh rate to: " + Settings.updateIterator, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Settings.updateIterator = 15;
                Toast.makeText(parent.getContext(), "Updated refresh rate to: " + Settings.updateIterator, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Settings.updateIterator = 30;
                Toast.makeText(parent.getContext(), "Updated refresh rate to: " + Settings.updateIterator, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Settings.updateIterator = 45;
                Toast.makeText(parent.getContext(), "Updated refresh rate to: " + Settings.updateIterator, Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Settings.updateIterator = 60;
                Toast.makeText(parent.getContext(), "Updated refresh rate to: " + Settings.updateIterator, Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Settings.updateIterator = 90;
                Toast.makeText(parent.getContext(), "Updated refresh rate to: " + Settings.updateIterator, Toast.LENGTH_SHORT).show();
                break;
            default:
                Settings.updateIterator = 60;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentAdapterView) {

    }

    public static boolean dpOver600(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = context.getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        return dpWidth >= TABLET_DP;
    }

    public void clock() {
        new Thread(new Runnable() {

            public void run() {
                while (loop) {
                    final Date date = new Date();
                    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            timer.setText(String.valueOf(simpleDateFormat.format(date)));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}