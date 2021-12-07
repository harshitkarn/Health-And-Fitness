package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class steps extends AppCompatActivity implements SensorEventListener, StepListener {
    DBHelper bdhelper;
    ProgressBar step_prog;
    ImageButton back_step;
    String formattedDate;
    TextView cal_steps,kms_steps;
    double weight,height,stepsCount,walkingFactor=0.57,CaloriesBurnedPerMile,strip,stepCountMile;
    double conversationFactor,CaloriesBurned,distance;
    NumberFormat formatter=new DecimalFormat("#0.00");
    private TextView stepstatus,cur_date;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    public int numSteps;

    public void setdet(int noofsteps)
    {

        Cursor bmic=bdhelper.getUserDet();
        bmic.moveToFirst();
        int usrheight=bmic.getInt(6);
        int usrweight=bmic.getInt(5);
        weight=usrweight;
        height=usrheight;
        stepsCount=noofsteps;
        CaloriesBurnedPerMile = walkingFactor * (weight * 2.2);
        strip = height * 0.415;
        stepCountMile = 160934.4 / strip;
        conversationFactor = CaloriesBurnedPerMile / stepCountMile;
        CaloriesBurned = stepsCount * conversationFactor;
        cal_steps=findViewById(R.id.cal_steps);
        cal_steps.setText(formatter.format(CaloriesBurned));
        distance = (stepsCount * strip) / 100000;
        kms_steps=findViewById(R.id.kms_steps);
        kms_steps.setText(formatter.format(distance));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        cur_date = findViewById(R.id.datstep);
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        cur_date.setText(formattedDate);
        bdhelper = new DBHelper(this);
        step_prog = findViewById(R.id.stepprog);
        step_prog.setMax(5000);

        try {
            Cursor consumed = bdhelper.checkconsumedavail(formattedDate);
            if (!(consumed.getCount() > 0)) {
                try {
                    bdhelper.insertconsumed(formattedDate);
                } catch (Exception e) {
                    Toast.makeText(steps.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            Cursor consumes = bdhelper.checkconsumedavail(formattedDate);
            consumes.moveToFirst();
            int stepsval = consumes.getInt(7);
            step_prog.setProgress(stepsval);
            stepstatus = (TextView) findViewById(R.id.step_status);
            stepstatus.setText(stepsval+"/5000");


            setdet(stepsval);


            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            simpleStepDetector = new StepDetector();
            simpleStepDetector.registerListener(this);



            numSteps = stepsval;
            sensorManager.registerListener(steps.this, accel, SensorManager.SENSOR_DELAY_FASTEST);


            back_step = findViewById(R.id.backstep);
            back_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(steps.this, HomePage.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            });

        }
        catch (Exception e){
            Toast.makeText(steps.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        stepstatus.setText(numSteps+"/5000");
        step_prog.setProgress(numSteps);
        bdhelper.stepswalk(formattedDate,numSteps);
        setdet(numSteps);
    }



    }
