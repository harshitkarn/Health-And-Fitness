package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class profileview extends AppCompatActivity {
    DBHelper mydb;
    TextView dataname,data6,data8,data10,data12,data14,data16,data18;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileview);
        mydb=new DBHelper(this);
        dataname=findViewById(R.id.textViewname);
        data6=findViewById(R.id.textView6);
        data8=findViewById(R.id.textView8);
        data10=findViewById(R.id.textView10);
        data12=findViewById(R.id.textView12);
        data14=findViewById(R.id.textView14);
        data16=findViewById(R.id.textView16);
        data18=findViewById(R.id.textView18);
        imageButton=findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileview.this, HomePage.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        try{
            Cursor res= mydb.getUserDet();
            if(res.getCount()==0){
                Toast.makeText(profileview.this,"Unknown error",Toast.LENGTH_SHORT).show();
            }
            else {
                res.moveToFirst();
                String name = res.getString(1);
                String phone = res.getString(2);
                String email = res.getString(3);
                String age = String.valueOf(res.getInt(4));
                String weight = String.valueOf(res.getInt(5));
                String height = String.valueOf(res.getInt(6));
                String gender = res.getString(7);
                dataname.setText(name);
                data6.setText(name);
                data8.setText(phone);
                data10.setText(email);
                data12.setText(age+" years");
                data14.setText(weight+" kgs");
                data16.setText(height+" cms");
                data18.setText(gender);

            }
        }
        catch (Exception e){
            Toast.makeText(profileview.this,"unknown error",Toast.LENGTH_SHORT).show();
        }


    }
}