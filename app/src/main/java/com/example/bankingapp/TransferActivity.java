package com.example.bankingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TransferActivity extends AppCompatActivity {
    static Button btnPay;
    private EditText txtAmount;
    private TextView lblAmountCheck;
    private Spinner mySpinner;
    static int amount;
    final static String PARAM2 = "payment";
    final static String PARAM3 = "toWhom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Intent intent = getIntent();
        amount = Integer.parseInt(intent.getStringExtra(MainActivity.PARAM1));

        btnPay = findViewById(R.id.btn_pay);
        this.txtAmount = findViewById(R.id.txt_amount);
        btnPay.setVisibility(View.INVISIBLE);
        this.txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty() &&  amount > Integer.parseInt(s.toString())) {
                    btnPay.setVisibility(View.VISIBLE);
                } else {
                    btnPay.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.mySpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.friends));
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        this.mySpinner.setAdapter(myAdapter);
    }

    // Triggers payment and return back mainActivity
    public void MakePayment(View v){
        String pay = this.txtAmount.getText().toString();
        String person = this.mySpinner.getSelectedItem().toString();
        Intent back = new Intent(TransferActivity.this, MainActivity.class);
        back.putExtra(PARAM2, pay);
        back.putExtra(PARAM3, person);
        setResult(Activity.RESULT_OK, back);
        finish();
    }

}
