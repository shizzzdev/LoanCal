package com.app.shingo.loancal;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {

    private TextView kariire;
    private TextView rate;
    private TextView period;
    private TextView income;
    private TextView monthlyRepay;
    private TextView annualReplay;
    private TextView repayRatio;

    private Button calcButton;
    private TextView kariireT;
    private TextView rateT;
    private TextView periodT;
    private TextView incomeT;
    private TextView monthlyRepayT;
    private TextView annualReplayT;
    private TextView repayRatioT;

    private TextView kariireS;
    private TextView rateS;
    private TextView periodS;
    private TextView incomeS;
    private TextView monthlyRepayS;
    private TextView annualReplayS;
    private TextView repayRatioS;

    private DAO dao;
    private LoanConfig loanConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instance
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dao = new DAO(db);

        // 縦固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();

        setFontSize();

        calcButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                double r = 0;
                double p = 0;
                double n = 0;
                double b = 0;
                double i = 0;

                if (!"".equals(rate.getText())) {
                    r = Double.parseDouble(rate.getText().toString());
                }
                if (!"".equals(period.getText())) {
                    p = Double.parseDouble(period.getText().toString());
                }
                if (!"".equals(kariire.getText())) {
                    n = Double.parseDouble(kariire.getText().toString());
                }
                if (!"".equals(income.getText())){
                    i = Double.parseDouble(income.getText().toString());
                }

                double ResultLoan = 0;
                if (b == 0){
                    // 処理なし
                } else {
                    ResultLoan = pmtJavaLoan(r,p,b);
                    n = n - b;
                }
                if (p == 0 && r == 0 && n == 0) {
                    // 処理なし
                } else {
                    double pmtResult = pmtJava(r,p,n);

                    monthlyRepay.setText(String.format("%.0f  ", pmtResult));

                    double y = (pmtResult * 12);
                    double yr = y / Double.parseDouble(income.getText().toString())*100;
                    annualReplay.setText(String.format("%.0f  ", y));
                    repayRatio.setText(String.format("%.2f  ", yr));

                    Log.d("calc", String.format("y=[%s]", y));
                    Log.d("calc", String.format("income=[%s]", Double.parseDouble(income.getText().toString())));
                    Log.d("calc", "income=[" + income.getText().toString() + "]");
                    Log.d("calc", "repayRatio=[" + repayRatio.getText() + "]");

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    saveConfig();
                }
            }
        });
    }

    private void saveConfig() {
        loanConfig.kariire = kariire.getText().toString();
        loanConfig.rate = rate.getText().toString();
        loanConfig.period = period.getText().toString();
        loanConfig.income = income.getText().toString();

        dao.updateStatus(loanConfig);
    }

    private void initView() {
        calcButton = (Button)findViewById(R.id.calcButton);

        kariire = (TextView)findViewById(R.id.kariire);
        rate = (TextView)findViewById(R.id.rate);
        period = (TextView)findViewById(R.id.period);
        income = (TextView)findViewById(R.id.income);
        monthlyRepay = (TextView)findViewById(R.id.monthlyRepay);
        annualReplay = (TextView)findViewById(R.id.annualReplay);
        repayRatio = (TextView)findViewById(R.id.replayRatio);

        kariireT = (TextView)findViewById(R.id.kariireT);
        rateT = (TextView)findViewById(R.id.rateT);
        periodT = (TextView)findViewById(R.id.periodT);
        incomeT = (TextView)findViewById(R.id.incomeT);
        monthlyRepayT = (TextView)findViewById(R.id.monthlyRepayT);
        annualReplayT = (TextView)findViewById(R.id.annualReplayT);
        repayRatioT = (TextView)findViewById(R.id.replayRatioT);

        kariireS = (TextView)findViewById(R.id.kariireS);
        rateS = (TextView)findViewById(R.id.rateS);
        periodS = (TextView)findViewById(R.id.periodS);
        incomeS = (TextView)findViewById(R.id.incomeS);
        monthlyRepayS = (TextView)findViewById(R.id.monthlyRepayS);
        annualReplayS = (TextView)findViewById(R.id.annualReplayS);
        repayRatioS = (TextView)findViewById(R.id.replayRatioS);

        loanConfig = dao.selectLoanConfig();

        kariire.setText(loanConfig.kariire);
        rate.setText(loanConfig.rate);
        period.setText(loanConfig.period);
        income.setText(loanConfig.income);
    }

    private void setFontSize() {
        float size = 18;

        kariire.setTextSize(size);
        rate.setTextSize(size);
        period.setTextSize(size);
        income.setTextSize(size);

        kariireT.setTextSize(size);
        rateT.setTextSize(size);
        periodT.setTextSize(size);
        incomeT.setTextSize(size);

        kariireS.setTextSize(size);
        rateS.setTextSize(size);
        periodS.setTextSize(size);
        incomeS.setTextSize(size);

        calcButton.setTextSize(size);

        monthlyRepay.setTextSize(size);
        annualReplay.setTextSize(size);
        repayRatio.setTextSize(size);

        monthlyRepayT.setTextSize(size);
        annualReplayT.setTextSize(size);
        repayRatioT.setTextSize(size);

        monthlyRepayS.setTextSize(size);
        annualReplayS.setTextSize(size);
        repayRatioS.setTextSize(size);
    }

    public static double pmtJava(double rate, double term, double amount) {
        return amount*rate/100/12*(Math.pow(1+rate/100/12, term*12))
                /(Math.pow(1+rate/100/12,term*12)-1);
    }
    public static double pmtJavaLoan(double rate, double term, double amount) {
        return amount*rate/100/2*(Math.pow(1+rate/100/2, term*2))
                /(Math.pow(1+rate/100/2,term*2)-1);
    }
}
