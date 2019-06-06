package com.example.manishyadab.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Highscores.db";
    public static final String TABLE_NAME = "Highscores_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "highscore";

    public dbhandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY ,HIGHSCORE INTEGER)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String id,String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,score);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }

    public boolean updateData(String id,String score){
        SQLiteDatabase db = this.getWritableDatabase();
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,score);
        db.update(TABLE_NAME,contentValues,"ID = ?", new String[] {id} );*/
        Cursor t  = db.rawQuery("update "+TABLE_NAME+" set "+COL_2+"="+score+" where ID="+id,null);
        t.moveToFirst();
        t.close();
        Cursor r1 =db.rawQuery("Select * From "+TABLE_NAME+" where ID= "+id,null);
        r1.moveToNext();
        System.out.println("db  :" +r1.getString(1));
        return true;
    }
}
