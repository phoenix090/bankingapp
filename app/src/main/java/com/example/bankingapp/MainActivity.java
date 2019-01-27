package com.example.bankingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private int balance = 0;
    public final static String TRANSAC_TIME = "time";
    public final static String TRANSAC_RECIPIENT = "recipient";
    public final static String TRANSAC_SUBTRACT = "subtract";
    public final static String TRANSAC_BLCNOW = "blcnow";

    public final static String PARAM1 = "amount";
    public static final int RESULT_CODE = 0;
    private TextView lblBalance;
    private ArrayList<String> payedTo;
    private ArrayList<Integer> howMuch;
    private ArrayList<String> transactionTimes;
    private ArrayList<Integer> currentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.randomNumberGenerator();
        // Array of recipients
        this.payedTo = new ArrayList<>();
        this.payedTo.add("You (account owner)");
        // Array of how much is paid to each of the recipients
        this.howMuch = new ArrayList<>();
        this.howMuch.add(this.balance);
        // When the transaction was made
        this.transactionTimes = new ArrayList<>();
        this.transactionTimes.add(getCurrentTime());
        // Current balance at all times!
        this.currentBalance = new ArrayList<>();
        this.currentBalance.add(this.balance);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == RESULT_CODE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // System.out.println(data.getStringExtra(TransferActivity.PARAM2));
                Bundle bundle = data.getExtras();
                String payed = bundle.get(TransferActivity.PARAM2).toString();
                String toPerson = bundle.get(TransferActivity.PARAM3).toString();
                this.balance -= Integer.parseInt(payed);
                this.lblBalance.setText(Integer.toString(this.balance));
                this.payedTo.add(toPerson);
                this.howMuch.add(Integer.parseInt(payed));
                this.currentBalance.add(this.balance);
                this.transactionTimes.add(getCurrentTime());
                if (this.payedTo.size() > 0) {
                    for (int i=0; i<this.payedTo.size(); i++){
                        System.out.println("\tTime: " + this.transactionTimes.get(i) +
                                "\tRecipient: " + this.payedTo.get(i) +
                                "\tCurrent Balance: " + this.currentBalance.get(i) +
                                "\tAmount: " + this.howMuch.get(i));
                    }
                }
            }
        }
    }

    // Generates random EUR balance.
    public void randomNumberGenerator(){
        Random r = new Random(System.currentTimeMillis());
        this.balance = r.nextInt(20) + 90;
        this.lblBalance = (TextView)findViewById(R.id.lbl_balance);
        this.lblBalance.setText(Integer.toString(this.balance));
    }

    // Transfers user to Transaction Activity.
    public void transactionsClick(View v){
        final Intent transaction = new Intent(MainActivity.this, TransactionsActivity.class);
        transaction.putExtra("SIZE", String.valueOf(this.payedTo.size()));
        for(int i=0; i<this.payedTo.size(); i++){
            transaction.putExtra(TRANSAC_TIME + String.valueOf(i), this.transactionTimes.get(i));
            transaction.putExtra(TRANSAC_RECIPIENT + String.valueOf(i), this.payedTo.get(i));
            transaction.putExtra(TRANSAC_SUBTRACT + String.valueOf(i), String.valueOf(this.howMuch.get(i)));
            transaction.putExtra(TRANSAC_BLCNOW + String.valueOf(i), String.valueOf(this.currentBalance.get(i)));
        }
        startActivity(transaction);
    }

    // Transfers user to TransferClick Activity.
    public void transferClick(View v){
        final Intent transfer = new Intent(MainActivity.this, TransferActivity.class);
        final Bundle myBundle = new Bundle();
        myBundle.putString(PARAM1, Integer.toString(this.balance));
        transfer.replaceExtras(myBundle);
        startActivityForResult(transfer, RESULT_CODE);
    }

    public String getCurrentTime(){
        return String.valueOf(android.text.format.DateFormat.format("HH:mm:ss", new java.util.Date()));
    }
}
