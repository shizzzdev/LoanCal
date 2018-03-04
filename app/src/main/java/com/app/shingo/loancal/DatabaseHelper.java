package com.app.shingo.loancal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shingo on 2017/07/13.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {

        /*
            ここで任意のデータベースファイル名と、バージョンを指定する
            データベースファイル名 = MyTable.db
            バージョン = 1
         */
        super(context, "MyTable1.db", null, 1);
    }

    //onCreateメソッド
    /*
    onCreateメソッドはデータベースを初めて使用する時に実行される処理
    ここでテーブルの作成や初期データの投入を行う
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルの作成
        Log.d("debug", "CREATE TABLE CONFIG");
        db.execSQL("CREATE TABLE LOANCONFIG " +
                "(" +
                "  NO INTEGER PRIMARY KEY" +
                ", KARIIRE TEXT" +
                ", INNERBONUS TEXT" +
                ", RATE TEXT" +
                ", PERIOD TEXT" +
                ", INCOME TEXT" +
                ")");

        db.execSQL("INSERT INTO LOANCONFIG (NO, KARIIRE, INNERBONUS, RATE, PERIOD, INCOME) " +
                " VALUES (1, '30000', '0', '1.0', '5', '50000')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ver2
    }

}
