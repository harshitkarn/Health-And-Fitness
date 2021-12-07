package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class exercise extends AppCompatActivity {
    TextView cur_date,exc_view,dayofweek;
    ImageButton exc_back;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        cur_date=findViewById(R.id.datexc);
        Calendar cd=Calendar.getInstance();
        Date c = cd.getTime();
        //System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        cur_date.setText(formattedDate);
        dayofweek=findViewById(R.id.dayofweek);
        exc_view=findViewById(R.id.exc_view);
        int day=cd.get(Calendar.DAY_OF_WEEK);
        mydb=new DBHelper(this);
        Cursor bmic=mydb.getUserDet();
        bmic.moveToFirst();

        int usrweight=bmic.getInt(5);

        int al,bl,cl,dl,el;
        if(usrweight<=40)
        {
            al=10;bl=5;cl=40;dl=10;el=2;
        }
        else if(usrweight<=70)
        {
            al=20;bl=7;cl=50;dl=15;el=3;
        }
        else
        {
            al=30;bl=10;cl=60;dl=20;el=5;
        }

        if(day==1){
            dayofweek.setText("Sunday");
            exc_view.setText("IT'S A SUNDAY\nRELAX\nRest up for next week!");
        }
        else if(day==2) {
            dayofweek.setText("Monday");
            exc_view.setText(""+al+"-second plank (right)\n"+al+"-second plank (left)\n"+al+"-second plank (center)\n"+bl+" lunges each side (2 sets)\n"+bl+" burpees");
        }
        else if(day==3){
            dayofweek.setText("Tuesday");
            exc_view.setText(""+bl+" push-ups\n"+al+" crunches (2 sets)\n"+bl+" burpees\n"+al+"-second plank\n"+bl+" lunges each side");
        }
        else if (day==4){
            dayofweek.setText("Wednesday");
            exc_view.setText(""+bl+" lunges each side (2 sets)\n"+cl+"-second plank\n"+al+" crunches\n"+bl+" push-ups (2 sets)\n"+al+" bicycles");
        }
        else if (day==5){
            dayofweek.setText("Thursday");
            exc_view.setText(""+bl+" push-ups\n"+al+" bicycles\n"+bl+" burpees\n"+cl+"-second plank\n"+bl+" lunges\n(repeat the series)");
        }
        else if (day==6){
            dayofweek.setText("Friday");
            exc_view.setText(""+dl+" burpees\n"+bl+" push-ups\n"+bl+" lunges each side\n"+al+" bicycles\n"+cl+"-second plank");
        }
        else if (day==7){
            dayofweek.setText("Saturday");
            exc_view.setText(""+el+" lunges each side\n"+bl+" push-ups\n"+bl+" burpees\n(repeat the series 3x)");
        }

        exc_back=findViewById(R.id.backcexc);
        exc_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exercise.this, HomePage.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}