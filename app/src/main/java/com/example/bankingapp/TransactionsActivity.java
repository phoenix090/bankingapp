package com.example.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity {
    private ArrayList<String> payedTo;
    private ArrayList<Integer> howMuch;
    private ArrayList<String> transactionTimes;
    private ArrayList<Integer> currentBalance;

    private TextView trTime;
    private TextView trRecipent;
    private TextView trOut;
    private TextView trCBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        this.payedTo = new ArrayList<>();
        this.howMuch = new ArrayList<>();
        this.transactionTimes = new ArrayList<>();
        this.currentBalance = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        int size = Integer.parseInt(bundle.getString("SIZE"));
        for(int i=0; i<size; i++){
            this.transactionTimes.add(bundle.getString(MainActivity.TRANSAC_TIME + String.valueOf(i)));
            this.payedTo.add(bundle.getString(MainActivity.TRANSAC_RECIPIENT + String.valueOf(i)));
            this.howMuch.add(Integer.parseInt(bundle.getString(MainActivity.TRANSAC_SUBTRACT + String.valueOf(i))));
            this.currentBalance.add(Integer.parseInt(bundle.getString(MainActivity.TRANSAC_BLCNOW + String.valueOf(i))));
            System.out.println("\tTime: " + this.transactionTimes.get(i) +
                    "\tRecipient: " + this.payedTo.get(i) +
                    "\tCurrent Balance: " + this.currentBalance.get(i) +
                    "\tAmount: " + this.howMuch.get(i));
        }
        fillInContent();
    }

    public void fillInContent(){
        this.trTime = findViewById(R.id.tr_time);
        this.trRecipent = findViewById(R.id.tr_recipent);
        this.trOut = findViewById(R.id.tr_out);
        this.trCBalance = findViewById(R.id.tr_cbalance);

        TextView infoField = findViewById(R.id.info_field);

        if(this.payedTo.size() == 1) {
            infoField.setText("There are no transactions made yet..!");
            hide();
        } else {
            StringBuffer times = new StringBuffer();
            StringBuffer recps = new StringBuffer();
            StringBuffer outs = new StringBuffer();
            StringBuffer bls = new StringBuffer();
            for (int i=0; i < this.payedTo.size(); i++){
                times.append(this.transactionTimes.get(i) + "\n");
                recps.append(this.payedTo.get(i) + "\n");
                outs.append(this.howMuch.get(i) + "\n");
                bls.append(this.currentBalance.get(i) + "\n");
            }
            this.trTime.setText(times.toString());
            this.trRecipent.setText(recps.toString());
            this.trOut.setText(outs.toString());
            this.trCBalance.setText(bls.toString());
        }
    }

    public void hide(){
        this.trTime.setVisibility(View.INVISIBLE);
        this.trRecipent.setVisibility(View.INVISIBLE);
        this.trOut.setVisibility(View.INVISIBLE);
        this.trCBalance.setVisibility(View.INVISIBLE);
    }
}
