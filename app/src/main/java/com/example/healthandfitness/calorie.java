package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class calorie extends AppCompatActivity {
    DBHelper bdhelper;
    ArrayList<String> fooditem = new ArrayList<>();
    String foodname;
    TextView foodqty, calorie_cap, prot_cap, carb_cap, fat_cap, cur_date,plus_btn,minus_btn;
    ProgressBar calorie, protein, carbs, fat;
    Button addexs,addnew;
    EditText newname,newcal,newprot,newcarb,newfat;
    ImageButton imageButtonCal;
    LinearLayout add_consumed, add_new_foood, add_consumed_lay, add_new_food_lay;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);
        cur_date = findViewById(R.id.datcal);
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        cur_date.setText(formattedDate);
        Spinner spinner = findViewById(R.id.spinner2);
        foodqty = findViewById(R.id.food_qty);
        calorie = findViewById(R.id.cal_progress);
        protein = findViewById(R.id.prot_progress);
        carbs = findViewById(R.id.carb_progress);
        fat = findViewById(R.id.fat_progress);
        calorie_cap = findViewById(R.id.cal_det);
        prot_cap = findViewById(R.id.prot_det);
        carb_cap = findViewById(R.id.carb_det);
        fat_cap = findViewById(R.id.fat_det);
        plus_btn=findViewById(R.id.addbtntext);
        minus_btn=findViewById(R.id.minusbtntext);
        addexs=findViewById(R.id.addconsum);
        addnew=findViewById(R.id.addnewfood);
        newname=findViewById(R.id.name_new_foood);
        newcal=findViewById(R.id.cal_new_food);
        newprot=findViewById(R.id.prot_new_food);
        newcarb=findViewById(R.id.carb_new_food);
        newfat=findViewById(R.id.fat_new_food);
        bdhelper = new DBHelper(this);
        add_consumed = findViewById(R.id.add_consumed_food);
        add_new_foood = findViewById(R.id.add_new_foood);
        add_consumed_lay = findViewById(R.id.add_food_layout);
        add_new_food_lay = findViewById(R.id.add_new_food_layout);
        scrollView = findViewById(R.id.scrollViewCalorie);
        add_consumed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_new_food_lay.getVisibility() == View.VISIBLE)
                    add_new_food_lay.setVisibility(View.GONE);
                if(add_consumed_lay.getVisibility() == View.VISIBLE)
                    add_consumed_lay.setVisibility(View.GONE);
                else
                    add_consumed_lay.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 10);
            }
        });

        add_new_foood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_consumed_lay.getVisibility() == View.VISIBLE)
                    add_consumed_lay.setVisibility(View.GONE);
                if(add_new_food_lay.getVisibility() == View.VISIBLE)
                    add_new_food_lay.setVisibility(View.GONE);
                else
                    add_new_food_lay.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 10);
            }
        });

        Cursor consumed = bdhelper.checkconsumedavail(formattedDate);
        if (!(consumed.getCount() > 0)) {
            try {
                bdhelper.insertconsumed(formattedDate);
            } catch (Exception e) {
                Toast.makeText(calorie.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        Cursor consumes = bdhelper.checkconsumedavail(formattedDate);
        consumes.moveToFirst();
        int calorieCon = consumes.getInt(2);
        int proteinCon = consumes.getInt(3);
        int carbCon = consumes.getInt(4);
        int fatCon = consumes.getInt(5);
        try {
            Cursor res = bdhelper.getFoodList();
            res.moveToFirst();
            do {
                fooditem.add(res.getString(0));
            } while (res.moveToNext());

        } catch (Exception e) {
            Toast.makeText(calorie.this, "unknown error", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list, fooditem);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String foodname = parent.getItemAtPosition(position).toString();
                foodqty.setHint(foodname);
                //Toast.makeText(calorie.this,foodname,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        try {
            Cursor userdet = bdhelper.getUserDet();
            userdet.moveToFirst();
            int age = userdet.getInt(4);
            int weight = userdet.getInt(5);
            int height = userdet.getInt(6);
            String gender = userdet.getString(7);
            if (gender.equals("Male")) {
                double caloriereq = weight * 1.0 * 24.0 * 0.95 * 1.65;
                double protreq = weight * 0.9;
                double carbreq = 0.15 * caloriereq;
                int calorint = (int) caloriereq;
                int protint = (int) protreq;
                int carbint = (int) carbreq;
                double fatreq = (0.3 * caloriereq) / 9;
                int fatint = (int) fatreq;
                calorie.setMax(calorint);
                calorie.setProgress(calorieCon);
                int caltext=calorieCon,prottext=proteinCon,carbtext=carbCon,fattext=fatCon;
                if(calorieCon>=calorint)
                    caltext=calorint;
                if(proteinCon>=protint)
                    prottext=protint;
                if(carbCon>=carbint)
                    carbtext=carbint;
                if(fatCon>=fatint)
                    fattext=fatint;
                calorie_cap.setText(String.valueOf(caltext) + "/" + String.valueOf(calorint));
                protein.setMax(protint);
                protein.setProgress(proteinCon);
                prot_cap.setText(String.valueOf(prottext) + "/" + String.valueOf(protint));
                carbs.setMax(carbint);
                carbs.setProgress(carbCon);
                carb_cap.setText(String.valueOf(carbtext) + "/" + String.valueOf(carbint));
                fat.setMax(fatint);
                fat.setProgress(fatCon);
                fat_cap.setText(String.valueOf(fattext) + "/" + String.valueOf(fatint));

            } else {
                double caloriereq = weight * 0.9 * 24.0 * 0.95 * 1.65;
                double protreq = weight * 0.8;
                double carbreq = 0.15 * caloriereq;
                int calorint = (int) caloriereq;
                int protint = (int) protreq;
                int carbint = (int) carbreq;
                double fatreq = (0.3 * caloriereq) / 9;
                int fatint = (int) fatreq;
                calorie.setMax(calorint);
                calorie.setProgress(calorieCon);
                int caltext=calorieCon,prottext=proteinCon,carbtext=carbCon,fattext=fatCon;
                if(calorieCon>=calorint)
                    caltext=calorint;
                if(proteinCon>=protint)
                    prottext=protint;
                if(carbCon>=carbint)
                    carbtext=carbint;
                if(fatCon>=fatint)
                    fattext=fatint;
                calorie_cap.setText(String.valueOf(caltext) + "/" + String.valueOf(calorint));
                protein.setMax(protint);
                protein.setProgress(proteinCon);
                prot_cap.setText(String.valueOf(prottext) + "/" + String.valueOf(protint));
                carbs.setMax(carbint);
                carbs.setProgress(carbCon);
                carb_cap.setText(String.valueOf(carbtext) + "/" + String.valueOf(carbint));
                fat.setMax(fatint);
                fat.setProgress(fatCon);
                fat_cap.setText(String.valueOf(fattext) + "/" + String.valueOf(fatint));
            }
        } catch (Exception e) {
            Toast.makeText(calorie.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int foodqt=Integer.parseInt(foodqty.getText().toString());
                if(foodqt<5){
                    foodqty.setText(String.valueOf(foodqt+1));
                }

            }
        });

        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int foodqt=Integer.parseInt(foodqty.getText().toString());
                if(foodqt>1){
                    foodqty.setText(String.valueOf(foodqt-1));
                }

            }
        });
        addexs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addfood=foodqty.getHint().toString();
                int foodqt=Integer.parseInt(foodqty.getText().toString());
                Cursor cf=bdhelper.getfoodvalue(addfood);
                cf.moveToFirst();
                int caloffood=calorieCon+foodqt*cf.getInt(2);
                int protoffood=proteinCon+foodqt*cf.getInt(3);
                int carboffood=carbCon+foodqt*cf.getInt(4);
                int fatoffood=fatCon+foodqt*cf.getInt(5);
                bdhelper.updatefoodcons(formattedDate,caloffood,protoffood,carboffood,fatoffood);
                Intent intent = new Intent(calorie.this, calorie.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                return;


            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newnameS=newname.getText().toString();
                String newcalS=newcal.getText().toString();
                String newprotS=newprot.getText().toString();
                String newcarbS=newcarb.getText().toString();
                String newfatS=newfat.getText().toString();
                if(newnameS.length()==0||newcalS.length()==0||newprotS.length()==0||newcarbS.length()==0||newfatS.length()==0){
                    Toast.makeText(calorie.this, "One or more fields empty", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(newcalS)==0||Integer.parseInt(newprotS)==0||Integer.parseInt(newcarbS)==0||Integer.parseInt(newfatS)==0){
                    Toast.makeText(calorie.this, "Zero unit not allowed", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(newcalS)>1000)
                    newcal.setError("max 1000 calories allowed");
                else if(Integer.parseInt(newprotS)>45)
                    newprot.setError("max 45 gm protein allowed");
                else if(Integer.parseInt(newcarbS)>128)
                    newcarb.setError("max 128 gm carbs allowed");
                else if(Integer.parseInt(newfatS)>37)
                    newfat.setError("max 37 gm fat allowed");
                else{
                    Cursor cdd=bdhelper.getfoodvalue(newnameS);
                    if(cdd.getCount()>0)
                        Toast.makeText(calorie.this, "Food with same name exists\nTry another name", Toast.LENGTH_LONG).show();
                    else{
                        boolean res=bdhelper.insertnewfood(newnameS,Integer.parseInt(newcalS),Integer.parseInt(newprotS),Integer.parseInt(newcarbS),Integer.parseInt(newfatS));
                        if(res){
                            Intent intent = new Intent(calorie.this, calorie.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "New Food Added Successfully", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            Toast.makeText(calorie.this, "Unknown error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        imageButtonCal=findViewById(R.id.backcal);
        imageButtonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calorie.this, HomePage.class);
                startActivity(intent);
                finish();
                return;
            }
        });


    }
}