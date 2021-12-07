package com.example.healthandfitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class facts extends AppCompatActivity {
    TextView cur_date,fact_view;
    ImageButton fact_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        cur_date=findViewById(R.id.datfact);
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        cur_date.setText(formattedDate);

        fact_view=findViewById(R.id.fact_view);
        SimpleDateFormat df1 = new SimpleDateFormat("dd", Locale.getDefault());
        String dayofmonth = df1.format(c);
        if(dayofmonth.equals("01")){
            fact_view.setText("Laughing is good for the heart and can increase blood flow by 20 percent.");
        }
        else if(dayofmonth.equals("02")){
            fact_view.setText("Your skin works hard. Not only is it the largest organ in the body, but it regulates your temperature and defends against disease and infection.");
        }
        else if(dayofmonth.equals("03")){
            fact_view.setText("Always look on the bright side: being an optimist can help you live longer.");
        }
        else if(dayofmonth.equals("04")){
            fact_view.setText("Exercise will give you more energy, even when you’re tired.");
        }
        else if(dayofmonth.equals("05")){
            fact_view.setText("Sitting and sleeping are great in moderation, but too much can increase your chances of an early death.");
        }
        else if(dayofmonth.equals("06")){
            fact_view.setText("A lack of exercise now causes as many deaths as smoking.");
        }
        else if(dayofmonth.equals("07")){
            fact_view.setText("39% of adults in the world are overweight.");
        }
        else if(dayofmonth.equals("08")){
            fact_view.setText("Between 2000 and 2015, the average global life expectancy increased by five years.");
        }
        else if(dayofmonth.equals("09")){
            fact_view.setText("Maintaining good relationships with your friends and family, reduces harmful levels of stress and boosts your immune system.");
        }
        else if(dayofmonth.equals("10")){
            fact_view.setText("Learning a new language or playing a musical instrument gives your brain a boost");
        }
        else if(dayofmonth.equals("11")){
            fact_view.setText("Chewing gum makes you more alert, relieves stress and reduces anxiety levels.");
        }
        else if(dayofmonth.equals("12")){
            fact_view.setText("Walking outside – or spending time in green space – can reduce negative thoughts and boost self-esteem.");
        }
        else if(dayofmonth.equals("13")){
            fact_view.setText("Chocolate is good for your skin; its antioxidants improve blood flow and protect against UV damage.");
        }
        else if(dayofmonth.equals("14")){
            fact_view.setText("Eating oatmeal provides a serotonin boost to calm the brain and improve your mood.");
        }
        else if(dayofmonth.equals("15")){
            fact_view.setText("Although it only takes you a few minutes to eat a meal, it takes your body hours to completely digest the food.");
        }
        else if(dayofmonth.equals("16")){
            fact_view.setText("Women below the age of 50 need twice the amount of iron per day as men of the same age.");
        }
        else if(dayofmonth.equals("17")){
            fact_view.setText("Vitamin D is as important as calcium in determining bone health, and most people don’t get enough of it.");
        }
        else if(dayofmonth.equals("18")){
            fact_view.setText("There are five main components of fitness: the body’s ability to use oxygen, muscular strength, endurance, flexibility and body composition.");
        }
        else if(dayofmonth.equals("19")){
            fact_view.setText("Walking at a fast pace for three hours or more at least once a week, you can reduce your risk of heart disease by up to 65%.");
        }
        else if(dayofmonth.equals("20")){
            fact_view.setText("Running is good for you. People who run 12-18 miles a week have a stronger immune system and can increase their bone mineral density.");
        }
        else if(dayofmonth.equals("21")){
            fact_view.setText("Exercising regularly can increase your lifespan by keeping your DNA healthy and young.");
        }
        else if(dayofmonth.equals("22")){
            fact_view.setText("Drinking at least five glasses of water a day can reduce your chances of suffering from a heart attack by 40%.");
        }
        else if(dayofmonth.equals("23")){
            fact_view.setText("Repeatedly using plastic water bottles can release chemicals into your water. Why not try a reusable bottle instead? It’s good for you and the planet.");
        }
        else if(dayofmonth.equals("24")){
            fact_view.setText("Hydration is key for a good complexion. Drinking enough water also makes you less prone to wrinkles.");
        }
        else if(dayofmonth.equals("25")){
            fact_view.setText("A lack of water can cause a range of problems, such as constipation, asthma, allergy and migraines.");
        }
        else if(dayofmonth.equals("26")){
            fact_view.setText("Feeling down? Plan a vacation. Not only will getting away make you feel better, but planning and anticipating the vacation will also give you a happiness boost.");
        }
        else if(dayofmonth.equals("27")){
            fact_view.setText("Breathing deeply in moments of stress, or anytime during the day, brings many benefits such as better circulation, decreased anxiety and reduced blood pressure.");
        }
        else if(dayofmonth.equals("28")){
            fact_view.setText("The soles of your feet contain more sweat glands and nerve endings per square inch than anywhere else on your body.");
        }
        else if(dayofmonth.equals("29")){
            fact_view.setText("The eye muscles are the most active in the body, moving more than 100,000 times a day!");
        }
        else if(dayofmonth.equals("30")){
            fact_view.setText("During an allergic reaction your immune system is responding to a false alarm that it perceives as a threat.");
        }
        else if(dayofmonth.equals("31")){
            fact_view.setText("On average, there are more bacteria per square inch in a kitchen sink than the bathroom.");
        }



        fact_back=findViewById(R.id.backcfact);
        fact_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(facts.this, HomePage.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}