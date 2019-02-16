package com.example.lenovo.criminalfaceidentificationsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.EditText;


public class Start extends AppCompatActivity {

    private EditText editTextMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        editTextMobile = findViewById(R.id.editTextMobile);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                //Intent intent = new Intent(Start.this, VerifyPhone.class);
               //intent.putExtra("mobile", mobile);
                Intent intent = new Intent(Start.this, Login_Police.class);
                startActivity(intent);
            }
        });
    }

}