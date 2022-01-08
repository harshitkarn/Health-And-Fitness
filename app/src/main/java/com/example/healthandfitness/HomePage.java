package com.example.healthandfitness;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthandfitness.databinding.ActivityHomePageBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView cur_date;
    CardView c_bmi,c_steps,c_calorie,c_facts,c_water,c_exc;
    DBHelper bdhelper;
    Dialog terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        bdhelper=new DBHelper(this);

//hooks
        cur_date=findViewById(R.id.cur_date);
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        cur_date.setText(formattedDate);
        drawerLayout=findViewById(R.id.coordinatorLayout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        //toolbar

        setSupportActionBar(toolbar);

        //navigation
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        c_bmi=findViewById(R.id.card_bmi);
        c_steps=findViewById(R.id.card_steps);
        c_calorie=findViewById(R.id.card_calorie);
        c_facts=findViewById(R.id.card_facts);
        c_water=findViewById(R.id.card_water);
        c_exc=findViewById(R.id.card_exercise);
        c_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, bmi.class);
                startActivity(intent);
            }
        });
        c_steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, steps.class);
                startActivity(intent);
            }
        });
        c_calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, calorie.class);
                startActivity(intent);
            }
        });
        c_facts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomePage.this, facts.class);
                startActivity(intent1);
            }
        });
        c_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomePage.this, water.class);
                startActivity(intent1);
            }
        });
        c_exc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomePage.this, exercise.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            //super.onBackPressed();
            finishAffinity();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        terms=new Dialog(this);
        drawerLayout.closeDrawer(GravityCompat.START);
        int id=item.getItemId();
        if(id==R.id.nav_profile){
            Intent intent = new Intent(HomePage.this, profileview.class);
            startActivity(intent);
        }
        if(id==R.id.nav_editprof){
            Intent intent1 = new Intent(HomePage.this, editprof.class);
            startActivity(intent1);
        }
        if(id==R.id.nav_delprofile){
            TextView cancelDelete, confirmDelete;
            ImageView delClose;
            terms.setContentView(R.layout.deleteconfirm_dialog);
            delClose = terms.findViewById(R.id.imageView1727);
            cancelDelete = terms.findViewById(R.id.cancelDelete);
            confirmDelete = terms.findViewById(R.id.confirmDelete);
            View.OnClickListener onclickLis= new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    terms.dismiss();
                }
            };
            delClose.setOnClickListener(onclickLis);
            cancelDelete.setOnClickListener(onclickLis);
            confirmDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (bdhelper.CheckUseravail()) {
                            bdhelper.deluser();
                            bdhelper.delrating();
                            bdhelper.deluserconsumed();
                            bdhelper.deluseraddedfood();
                            Intent intent = new Intent(HomePage.this, HomeScreen.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                            //return;
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(HomePage.this,"Error deleting account",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            terms.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            terms.show();
        }
        if(id==R.id.nav_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at:\n" +
                    "\"PLAYSTORE LINK\"");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
        if(id==R.id.nav_rate) {
            try{
                ImageView dialogclsb;
            RatingBar ratingBar;
            Button submit;
            terms.setContentView(R.layout.rateus);
            dialogclsb = terms.findViewById(R.id.dialogclsb);
            submit = terms.findViewById(R.id.buttondb);
            ratingBar = terms.findViewById(R.id.ratingBar2);
            dialogclsb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    terms.dismiss();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int rating=(int)ratingBar.getRating();
                        if(rating==0){
                            Toast.makeText(HomePage.this, "Please click on stars to rate", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Boolean n=bdhelper.InsertRating(rating);
                            if(n) {
                                Toast.makeText(HomePage.this, "Thanks for rating", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(HomePage.this, "You have already rated the app", Toast.LENGTH_SHORT).show();
                            }
                            terms.dismiss();
                        }
                }
            });
                terms.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            terms.show();
        }
            catch (Exception e){
                Toast.makeText(HomePage.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        if(id==R.id.nav_feedback) {
            String mailto="mailto:harshitsma2011@gmail.com"+
                    "?cc="+"kushsingh01230@gmail.com"+
                    "&subject="+Uri.encode("Feedback for HEALTH AND FITNESS App");
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse(mailto));
            startActivity(email);
        }
        if(id==R.id.nav_about){
            ImageView dialogclsa;
            terms.setContentView(R.layout.aboutus);
            dialogclsa=terms.findViewById(R.id.dialogclsa);
            dialogclsa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    terms.dismiss();
                }
            });
            terms.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            terms.show();

        }
        if(id==R.id.nav_terms){
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

        return true;
    }
}