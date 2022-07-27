package com.example.flashexpress.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.flashexpress.R;

public class ContactUs extends AppCompatActivity {

    String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        userEmail = getIntent().getStringExtra("email");
    }

    public void callUs(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+8801758464648"));
        startActivity(intent);
    }

    public void emailUs(View view) {
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + "&to=" + "1730623@iub.edu.bd");
        mailIntent.setData(data);
        startActivity(mailIntent);
    }

    public void facebookUs(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("https://www.facebook.com/zafirahzunairah.prithy"));
        startActivity(browserIntent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ContactUs.this, Dashboard.class);
        intent.putExtra("email", userEmail);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}