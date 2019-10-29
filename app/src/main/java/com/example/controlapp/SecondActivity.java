package com.example.controlapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    String[] arr = { "Aaryan Gupta", "Abhishek Singhal", "Aman Kumar", "Ankit Birla", "Avinash Kumar",
            "Ayush Sharma", "Bhanu Yadav", "Deepak Kaushik", "Deepak Kumar Sharma", "Himanshu Verma",
            "Jitendra Kumar", "Drashti Sharma", "Kamini Gautam", "Kritika Rajput", "Mehar Shree",
            "Paridhi Kaushik", "Parul", "Priyanshu Saxena", "Ravi Kumar", "Rudravir Singh",
            "Shobhit Sharma", "Subham Singh", "Suraj", "Suzan Khan", "Umesh", "Vikash Agrawal",
            "Ankit Singh", "Mohit Paras"
    };

    private AutoCompleteTextView Name;
    private ToggleButton Toggle;
    private EditText DOB;
    private EditText birthTime;
    private DatePickerDialog picker;
    private TimePickerDialog timepicker;
    private RadioGroup radioName;
    private RadioButton radioBtn;
    private CheckBox cbiot, cbrobo, cbai, cbml;
    private ImageButton submit;

    private Spinner s;
    int getSelected;
    String text="";

    //Data for populating in Spinner
    String [] gen={"Choose your Gender...", "Male", "Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Name = findViewById(R.id.main_name);
        Toggle = findViewById(R.id.toggle);
        DOB = findViewById(R.id.dob);
        DOB.setInputType(InputType.TYPE_NULL);
        birthTime = findViewById(R.id.time);
        birthTime.setInputType(InputType.TYPE_NULL);
        submit = findViewById(R.id.submitBtn);

        radioName = (RadioGroup)findViewById(R.id.radioName);
        radioName.clearCheck();

        cbiot = (CheckBox) findViewById(R.id.cbiot);
        cbrobo = (CheckBox) findViewById(R.id.cbrobo);
        cbai = (CheckBox) findViewById(R.id.cbai);
        cbml = (CheckBox) findViewById(R.id.cbml);

        s= (Spinner) findViewById(R.id.spinner);

        //Creating Adapter for Spinner for adapting the data from array to Spinner
        ArrayAdapter adapter1= new ArrayAdapter(SecondActivity.this,android.R.layout.simple_spinner_item, gen);
        s.setAdapter(adapter1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        Name.setThreshold(1);
        Name.setAdapter(adapter);

        Toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // The toggle is disabled
                }
            }
        });

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SecondActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        birthTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timepicker = new TimePickerDialog(SecondActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                birthTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                timepicker.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cbiot.isChecked())
                    text = text + " IoT ";
                if(cbrobo.isChecked())
                    text = text + " Robotics ";
                if(cbai.isChecked())
                    text = text + " AI ";
                if(cbml.isChecked())
                    text = text + " ML ";

                getSelected = radioName.getCheckedRadioButtonId();
                radioBtn = (RadioButton)findViewById(getSelected);

                StringBuffer buffer=new StringBuffer();

                buffer.append("Name: "+radioBtn.getText()+" "+Name.getText().toString()+"\n");
                buffer.append("Gender: "+s.getSelectedItem().toString()+"\n");
                buffer.append("Date of Birth:"+DOB.getText().toString()+" "+birthTime.getText().toString()+"\n");
                buffer.append("Subjects: "+text);

                showMessage("Your Details", buffer.toString());
            }

        });
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
