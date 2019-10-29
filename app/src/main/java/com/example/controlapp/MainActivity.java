package com.example.controlapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout mainText;
    private Button Btn;
    private TextView text, dark;
    private SeekBar bar;
    private ProgressBar progress;
    private ProgressDialog dialog;
    private RatingBar rating;
    private Switch nightmode;
    private ToggleButton toggle;

    int textSize = 3;
    int saveProgress;
    boolean isDark = false;
    private ConstraintLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = findViewById(R.id.main_text);
        Btn = findViewById(R.id.main_button);
        text = findViewById(R.id.textView);
        text.setTextScaleX(textSize);
        bar = findViewById(R.id.seekBar);
        progress = findViewById(R.id.progressBar);
        dialog = new ProgressDialog(this);
        rating = findViewById(R.id.ratingBar);
        nightmode = findViewById(R.id.switch1);
        back = findViewById(R.id.background);
        dark = findViewById(R.id.dark);
        toggle = findViewById(R.id.toggleButton);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // The toggle is disabled
                }
            }
        });

        isDark = getThemeStatePref();
        if(isDark) {
            // dark theme is on

            back.setBackgroundColor(getResources().getColor(R.color.black));
            mainText.setBoxBackgroundColor(getResources().getColor(R.color.white));
            bar.setBackgroundColor(getResources().getColor(R.color.white));
            rating.setBackgroundColor(getResources().getColor(R.color.white));
            nightmode.setBackgroundColor(getResources().getColor(R.color.white));
            dark.setBackgroundColor(getResources().getColor(R.color.white));

        }
        else
        {
            // light theme is on
            back.setBackgroundColor(getResources().getColor(R.color.white));

        }

        nightmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
                    back.setBackgroundColor(getResources().getColor(R.color.black));
                    mainText.setBoxBackgroundColor(getResources().getColor(R.color.white));
                    bar.setBackgroundColor(getResources().getColor(R.color.white));
                    rating.setBackgroundColor(getResources().getColor(R.color.white));
                    nightmode.setBackgroundColor(getResources().getColor(R.color.white));
                    dark.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    // The toggle is disabled
                    back.setBackgroundColor(getResources().getColor(R.color.white));
                }
                saveThemeStatePref(isDark);
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();

                if (mainText.getEditText().getText().toString().isEmpty()){

                    dialog.setTitle("Empty Message");
                    dialog.setMessage("You can't enter the empty text!");
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                } else if (mainText.getEditText().getText().toString() != null){

                    progress.setVisibility(View.VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress.setVisibility(View.INVISIBLE);
                            String msg = mainText.getEditText().getText().toString();
                            text.setVisibility(View.VISIBLE);
                            text.setText("You entered:\n" +msg);
                            mainText.getEditText().setText("");
                        }
                    }, 3000);

                }


            }
        });

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textSize = textSize + (progress-saveProgress);
                saveProgress = progress;
                text.setTextSize(textSize);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }

    private boolean getThemeStatePref () {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }
}
