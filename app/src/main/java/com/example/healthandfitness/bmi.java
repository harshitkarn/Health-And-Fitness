package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class bmi extends AppCompatActivity {
    TextView userbmi,bmidet;
    EditText heightbmi,weightbmi;
    Button btncalc;
    ImageButton backbmi;
    DBHelper mydb;
    Dialog terms;


    public double calculatebmi(double height,double weight){
        return weight/((height*height)/10000.0);
    }
    public void settext(double bmi,TextView a,TextView b){
            a.setText("Your BMI is "+String.format("%.2f",bmi));
        if(bmi<18.5)
            b.setText("Underweight");
        else if(bmi>=18.5&&bmi<=24.9)
            b.setText("Normal Weight");
        else if(bmi>24.9&&bmi<=29.9)
            b.setText("Overweight");
        else if(bmi>29.9&&bmi<=34.9)
            b.setText("Obese");
        else
            b.setText("Extremely Obese");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        userbmi=findViewById(R.id.user_bmi);
        bmidet=findViewById(R.id.bmi_det);
        btncalc=findViewById(R.id.btnclcbmi);
        mydb=new DBHelper(this);
        Cursor bmic=mydb.getUserDet();
        bmic.moveToFirst();
        int usrheight=bmic.getInt(6);
        int usrweight=bmic.getInt(5);
        double usrhd=usrheight;
        double usrwd=usrweight;
        double usrbmi=calculatebmi(usrhd,usrwd);
        settext(usrbmi,userbmi,bmidet);
        terms=new Dialog(this);
        btncalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightbmi = findViewById(R.id.heightbmi);
                weightbmi = findViewById(R.id.weightbmi);
                if (heightbmi.getText().toString().length() == 0)
                    heightbmi.setError("Enter height first");
                else if (weightbmi.getText().toString().length() == 0)
                    weightbmi.setError("Enter weight first");
                else {
                    int calcheight = Integer.parseInt(heightbmi.getText().toString());
                    int calcweight = Integer.parseInt(weightbmi.getText().toString());
                    if (!(calcheight <= 250 && calcheight >= 50))
                        heightbmi.setError("Invalid height");
                    else if (!(calcweight <= 300 && calcweight >= 20))
                        weightbmi.setError("Invalid weight");
                    else {
                        try {
                            double usr2hd = calcheight;
                            double usr2wd = calcweight;
                            double usr2bmi = calculatebmi(usr2hd, usr2wd);
                            terms.setContentView(R.layout.bmi_dialog_box);
                            settext(usr2bmi, terms.findViewById(R.id.textView25), terms.findViewById(R.id.textView26));
                            terms.findViewById(R.id.imageView11).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    terms.dismiss();
                                }
                            });
                            terms.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            terms.show();
                        }
                        catch (Exception e){
                            Toast.makeText(bmi.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        backbmi=findViewById(R.id.back_bmi);
        backbmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bmi.this, HomePage.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}