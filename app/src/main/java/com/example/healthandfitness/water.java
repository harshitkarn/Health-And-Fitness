package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class water extends AppCompatActivity {
    DBHelper bdhelper;
    ProgressBar water_prog;
    TextView water_status,cur_date;
    ImageView addnewwater;
    int watertext;
    ImageButton back_wat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        bdhelper = new DBHelper(this);
        cur_date = findViewById(R.id.datwat);
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        cur_date.setText(formattedDate);
        water_prog=findViewById(R.id.waterprog);
        water_prog.setMax(8);
        water_status=findViewById(R.id.water_status);
        try {
            Cursor consumed = bdhelper.checkconsumedavail(formattedDate);
            if (!(consumed.getCount() > 0)) {
                try {
                    bdhelper.insertconsumed(formattedDate);
                } catch (Exception e) {
                    Toast.makeText(water.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            Cursor consumes = bdhelper.checkconsumedavail(formattedDate);
            consumes.moveToFirst();
            int waterval = consumes.getInt(6);
            water_prog.setProgress(waterval);
            if (waterval <= 8) {
                watertext = waterval;
            } else {
                watertext = 8;
            }

        water_status.setText(String.valueOf(watertext));
        addnewwater=findViewById(R.id.addnewwater);
        addnewwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor consumess = bdhelper.checkconsumedavail(formattedDate);
                consumess.moveToFirst();
                int waterval1 = consumess.getInt(6) + 1;
                bdhelper.updatewatercons(formattedDate,waterval1);
                water_prog.setProgress(waterval1);
                if(waterval1<=8) {
                    water_status.setText(String.valueOf(waterval1));
                    Toast.makeText(water.this,"Water glass added",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(water.this,"You have reached your goal for today",Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
        catch (Exception e){
            Toast.makeText(water.this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        back_wat=findViewById(R.id.backcwat);
        back_wat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(water.this, HomePage.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}