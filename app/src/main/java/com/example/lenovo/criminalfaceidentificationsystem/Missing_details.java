

package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.criminalfaceidentificationsystem.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;


import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;

public class Missing_details extends AppCompatActivity implements View.OnClickListener{
    String  contact;
    String  contact2;
    String id;
    String loc="";
    String loc1="";
    EditText number;
    EditText number2;
    public FusedLocationProviderClient client;
    public DatabaseReference mDatabase;
    @Override
    public void onClick(View v) {
        requestPermission();
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        client = LocationServices.getFusedLocationProviderClient(this);
        onSend();

    }

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    private ImageView img;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_details);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.contact2);
        img = (ImageView) findViewById(R.id.m_image);

        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(this);
        // Recieve data
        Intent intent = getIntent();
        String Name = intent.getExtras().getString("Name");
        String dob = intent.getExtras().getString("dob");
        String gender= intent.getExtras().getString("gender");
        String last = intent.getExtras().getString("last");
        String reason = intent.getExtras().getString("reason");
        String desc = intent.getExtras().getString("desc");
        String height = intent.getExtras().getString("height");
        contact = intent.getExtras().getString("contact");
        contact2 = intent.getExtras().getString("contact2");
        String image = intent.getExtras().getString("photo") ;
        //String police_no= intent.getExtras().getString("police_no");

/*
        intent.putExtra("Name",missingCurrent.getName());
        intent.putExtra("dob",missingCurrent.getDob());
        intent.putExtra("gender",missingCurrent.getGender());
        intent.putExtra("last",missingCurrent.getLast_seen());
        intent.putExtra("reason",missingCurrent.getReason());
        intent.putExtra("desc",missingCurrent.getDesc());
        intent.putExtra("height",missingCurrent.getHeignt());
        intent.putExtra("contact",missingCurrent.getContact());
        intent.putExtra("photo",missingCurrent.getPhoto());
*/
        Picasso.get()
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(img);
        // Setting values
        tv1.setText(Name);
        tv2.setText(dob);
        tv3.setText(gender);
        tv4.setText(last);
        tv5.setText(reason);
        tv6.setText(contact);
        tv7.setText(desc);
        tv8.setText(height);
        tv9.setText(contact2);


    }


    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }


    public void onSend(){
        Toast.makeText(this, "clicked and in send", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(Missing_details.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            return;
        }

        client.getLastLocation().addOnSuccessListener(Missing_details.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!= null){//Toast.makeText(MainActivity.this, "sadsd", Toast.LENGTH_SHORT).show();
                    loc = location.toString();
                    Toast.makeText(Missing_details.this, ""+loc, Toast.LENGTH_LONG).show();
                    loc1=loc.substring(15,34);
                    mDatabase = FirebaseDatabase.getInstance().getReference("Users-location");
                    mDatabase.child("u").setValue(loc1);
                    String phoneNumber =contact;
                    String phoneNumber2 =contact2;

                    String smsMessage;

                    Toast.makeText(Missing_details.this, ""+phoneNumber2, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Missing_details.this, ""+id, Toast.LENGTH_SHORT).show();
                    smsMessage="FOUND A PERSON "+"\nName:"+tv1.getText().toString().trim()+"\nGender:"+tv3.getText().toString().trim()+"\nHeight:"+tv8.getText().toString().trim()+"\nClick on below link to get location"+"\n https://www.google.com/maps?q="+loc1;
                    Toast.makeText(Missing_details.this, ""+phoneNumber, Toast.LENGTH_LONG).show();
                    if(phoneNumber == null || phoneNumber.length() == 0 ||
                            smsMessage == null || smsMessage.length() == 0){
                        return;
                    }
                    if(phoneNumber2 == null || phoneNumber2.length() == 0 ||
                            smsMessage == null || smsMessage.length() == 0){
                        return;
                    }

                    if(checkPermission(permission.SEND_SMS)){
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);
                        smsManager.sendTextMessage(phoneNumber2, null, smsMessage, null, null);
                        Toast.makeText(Missing_details.this, "Message Sent!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Missing_details.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }


    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

}

