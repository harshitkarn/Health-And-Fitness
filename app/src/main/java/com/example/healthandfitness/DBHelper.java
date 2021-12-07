package com.example.healthandfitness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static int id=1;
    public DBHelper(Context context) {
        super(context, "caloreie.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id int,name TEXT,phone TEXT PRIMARY KEY,email TEXT,age INTEGER,weight INTEGER,height INTEGER,gender TEXT)");
        db.execSQL("CREATE TABLE fooditem(type TEXT,food TEXT PRIMARY KEY,calorie INTEGER,protein INTEGER,carbs INTEGER,fat INTEGER)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','egg',78,13,1,5)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','banana',105,1,27,1)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','apple',95,1,27,1)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','chapati',80,3,15,1)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','rice(1 cup)',200,5,41,1)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','veggies',25,2,5,1)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','dosa(medium)',170,4,30,8)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','idli',35,1,8,1)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','biryani(chicken)',500,20,31,9)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','chicken(178 gms)',425,50,0,24)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','chole bhature',427,11,60,20)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','oats(1 cup)',389,17,32,5)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','lentil(100 gms)',116,9,20,1)");
        db.execSQL("INSERT INTO fooditem VALUES('appdef','paneer(100 gms)',296,23,5,4)");
        db.execSQL("CREATE TABLE consumed(date TEXT PRIMARY KEY,id INTEGER,calorie INTEGER,protein INTEGER,carbs INTEGER,fat INTEGER,waterglass INTEGER,steps INTEGER)");
        db.execSQL("CREATE TABLE rating(id INTEGER PRIMARY KEY,rating INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS fooditem");
        db.execSQL("DROP TABLE IF EXISTS consumed");
    }

    public boolean Insert(String name, String phone,String email,int age, int weight,int height,String gender){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("age", age);
        contentValues.put("weight", weight);
        contentValues.put("height", height);
        contentValues.put("gender", gender);
        long result = sqLiteDatabase.insert("user", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean InsertRating(int rating){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("id",id);
        contentValues.put("rating", rating);
        long result = sqLiteDatabase.insert("rating", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getUserDet(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM user",null);
        return res;
    }

    public Boolean CheckUseravail(){
            SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
            Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM user",null);
            if(cursor.getCount() >0){
                return true;
            }else{
                return false;
            }
    }

    public void deluser(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("user","id=1",null);
    }

    public void delrating(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("rating","id=1",null);
    }

    public Cursor getFoodList(){
        SQLiteDatabase dbf=this.getReadableDatabase();
        Cursor res=dbf.rawQuery("SELECT food FROM fooditem",null);
        return res;
    }
    public Cursor checkconsumedavail(String date){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM consumed WHERE date=?", new String[]{date});
        return cursor;
    }

    public boolean insertconsumed(String date){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("date",date);
        contentValues.put("id",id);
        contentValues.put("calorie",0);
        contentValues.put("protein",0);
        contentValues.put("carbs",0);
        contentValues.put("fat",0);
        contentValues.put("waterglass",0);
        contentValues.put("steps",0);

        long result = sqLiteDatabase.insert("consumed", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getfoodvalue(String food){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM fooditem WHERE food=?", new String[]{food});
        return cursor;
    }

    public void updatefoodcons(String date,int cal,int prot,int carb,int fat){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE consumed SET calorie="+cal+",protein="+prot+",carbs="+carb+",fat="+fat+" WHERE date='"+date+"'");
    }

    public boolean insertnewfood(String name,int calorie,int protein,int carbs,int fat){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("type","userdef");
        contentValues.put("food",name);
        contentValues.put("calorie",calorie);
        contentValues.put("protein",protein);
        contentValues.put("carbs",carbs);
        contentValues.put("fat",fat);

        long result = sqLiteDatabase.insert("fooditem", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public void deluseraddedfood(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("fooditem","type='userdef'",null);
    }

    public void deluserconsumed(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("consumed","id=1",null);
    }

    public void updatewatercons(String date,int glass){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE consumed SET waterglass="+glass+" WHERE date='"+date+"'");
    }

    public void stepswalk(String date,int steps){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE consumed SET steps="+steps+" WHERE date='"+date+"'");
    }

}
