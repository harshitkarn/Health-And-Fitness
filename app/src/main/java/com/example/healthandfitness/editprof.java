package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class editprof extends AppCompatActivity {
    DBHelper mydb;
    TextView dataname;
    EditText data6,data8,data10,data12,data14,data16,data18;
    ImageButton imageButton;
    Button update,update_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprof);
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
                Intent intent = new Intent(editprof.this, HomePage.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        try{
            Cursor res= mydb.getUserDet();
            if(res.getCount()==0){
                Toast.makeText(editprof.this,"Unknown error",Toast.LENGTH_SHORT).show();
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
                data12.setText(age);
                data14.setText(weight);
                data16.setText(height);
                data18.setText(gender);

            }
        }
        catch (Exception e){
            Toast.makeText(editprof.this,"unknown error",Toast.LENGTH_SHORT).show();
        }

        update=findViewById(R.id.update);
        update_1=findViewById(R.id.update_1);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res= HomeScreen.checkuser(data6.getText().toString(),data8.getText().toString(),data10.getText().toString(),data12.getText().toString(),data14.getText().toString(),data16.getText().toString(),data18.getText().toString());
                if(data18.getText().toString().equals("Male")||data18.getText().toString().equals("Female")) {
                    if (res) {
                        try {
                            mydb.deluser();
                            boolean result = mydb.Insert(data6.getText().toString(), data8.getText().toString(), data10.getText().toString(), Integer.parseInt(data12.getText().toString()), Integer.parseInt(data14.getText().toString()), Integer.parseInt(data16.getText().toString()), data18.getText().toString());
                            if (result) {
                                Intent intent = new Intent(editprof.this, HomePage.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch (Exception e) {
                            Toast.makeText(editprof.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(editprof.this, "Invalid Initials", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(editprof.this, "Enter gender as Male or Female", Toast.LENGTH_SHORT).show();
                }
            }
        };
        update.setOnClickListener(onClickListener);
        update_1.setOnClickListener(onClickListener);

    }

}