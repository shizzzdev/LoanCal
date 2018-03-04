package com.app.shingo.loancal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shingo on 2017/07/13.
 */

public class DAO {
    // SQLiteDatabase
    private SQLiteDatabase db;

    public DAO(SQLiteDatabase db) {
        this.db = db;
    }

    public LoanConfig selectLoanConfig() {

        LoanConfig config = new LoanConfig();
        String sql = "SELECT KARIIRE, " +
                "INNERBONUS, " +
                "RATE, " +
                "PERIOD, " +
                "INCOME " +
                "FROM LOANCONFIG";

        // SQL文を実行してカーソルを取得
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()) {
            config.kariire = c.getString(0);
            config.innerBonus = c.getString(1);
            config.rate = c.getString(2);
            config.period = c.getString(3);
            config.income = c.getString(4);
        }
        // データベースのクローズ処理
        c.close();
        //db.close();

        return config;
    }

    public void updateStatus(LoanConfig loanConfig) {
        String sql = "UPDATE LOANCONFIG set KARIIRE = " + loanConfig.kariire
                + ", INNERBONUS = " + loanConfig.innerBonus
                + ", RATE = " + loanConfig.rate
                + ", PERIOD = " + loanConfig.period
                + ", INCOME = " + loanConfig.income
                + " WHERE NO = 1";

        db.execSQL(sql);
    }
}
