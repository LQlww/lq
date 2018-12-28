package com.example.musicplayer50;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

//创建一个数据库辅助类存放手机里的音乐文件
public class MusicSqlHelper extends SQLiteOpenHelper {

    public static final String sql= "create table login("
            +"id integer primary key autoincrement,"
            +"title String,"
            +"artist String,"
            +"url String)";
    private Context mcontext;
    public MusicSqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mcontext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(sql);
        Toast.makeText(mcontext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists ACCOUNT_PASSWORD");
        onCreate(db);
    }
}
