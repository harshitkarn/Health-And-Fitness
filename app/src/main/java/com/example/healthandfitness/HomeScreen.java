package com.example.healthandfitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {
    static boolean checkuser(String name,String phone,String email,String age,String weight,String height,String gender) {
        if(name.length()>0&&phone.length()==10&&gender.length()>0&&email.length()>0&&age.length()>0&&weight.length()>0&&height.length()>0){
            if(Integer.parseInt(age)>=18&&Integer.parseInt(age)<=120&&Integer.parseInt(height)>=100&&Integer.parseInt(height)<=250&&Integer.parseInt(weight)>=20&&Integer.parseInt(weight)<=300&&email.matches("^(.+)@(.+)$"))
                return true;
            else
                return false;
        }
        else{
            return false;
        }
    }
    Dialog terms;
    DBHelper dbHelper;
    EditText name,phone,email,age,weight,height;
    RadioGroup gender;
    RadioButton maleorfem,female,male;
    Button submit;
    CheckBox agreed;
    //TextView data1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        terms=new Dialog(this);

        TextView agree=findViewById(R.id.agreetext);
        String text="By Clicking submit you agree to the\nTerms and Conditions";
        SpannableString ss=new SpannableString(text);
        ClickableSpan cs1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ImageView dialogcls;
                terms.setContentView(R.layout.activity_termsandcond);
                dialogcls=terms.findViewById(R.id.dialogcls);
                dialogcls.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        terms.dismiss();
                    }
                });
                terms.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                terms.show();
            }
        };
        ss.setSpan(cs1,36,56, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        agree.setText(ss);
        agree.setMovementMethod(LinkMovementMethod.getInstance());
        dbHelper = new DBHelper(this);
        if(dbHelper.CheckUseravail()){
            Intent intent= new Intent(HomeScreen.this, HomePage.class);
            startActivity(intent);
            finish();
            return;
        }
        else{

        }
        name=(EditText)findViewById(R.id.wel_name);
        phone=(EditText)findViewById(R.id.wel_phone);
        email=(EditText)findViewById(R.id.wel_email);
        age=(EditText)findViewById(R.id.wel_age);
        weight=(EditText)findViewById(R.id.wel_weight);
        height=(EditText)findViewById(R.id.wel_height);
        gender=(RadioGroup)findViewById(R.id.wel_gender);
        submit=(Button)findViewById(R.id.wel_submit);
        agreed=(CheckBox)findViewById(R.id.wel_agree);
        male=(RadioButton)findViewById(R.id.wel_male);
        female=(RadioButton)findViewById(R.id.wel_female);
        //data1=(TextView)findViewById(R.id.data1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agreed.isChecked()) {
                    if (male.isChecked() || female.isChecked()) {
                        int ids = gender.getCheckedRadioButtonId();
                        maleorfem = (RadioButton) findViewById(ids);
                        if (checkuser(name.getText().toString(), phone.getText().toString(), email.getText().toString(), age.getText().toString(), weight.getText().toString(), height.getText().toString(), maleorfem.getText().toString())) {
                            try {
                                boolean result = dbHelper.Insert(name.getText().toString(), phone.getText().toString(), email.getText().toString(), Integer.parseInt(age.getText().toString()), Integer.parseInt(weight.getText().toString()), Integer.parseInt(height.getText().toString()), maleorfem.getText().toString());
                                if (result) {
                                    Intent intent = new Intent(HomeScreen.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    Toast.makeText(HomeScreen.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(HomeScreen.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(HomeScreen.this, "Invalid Initials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeScreen.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(HomeScreen.this, "Warning. You must accept our terms", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}