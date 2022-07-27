package com.example.flashexpress.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.flashexpress.MainActivity;
import com.example.flashexpress.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class OTPVerify extends AppCompatActivity {

    FirebaseAuth auth;
    ProgressBar progress_bar;
    String userEmail;
    TextView otp_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

        //initialize
        progress_bar = findViewById(R.id.progress_bar);
        otp_email = findViewById(R.id.otp_email);

        //
        userEmail = getIntent().getStringExtra("email");
        otp_email.setText(userEmail);

        auth = FirebaseAuth.getInstance();
    }


    public void verifyOTP(View view) {
        if (userEmail.isEmpty()) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }
        progress_bar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(OTPVerify.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                }

//                        else {
//                            Toast.makeText(OTPVerify.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
//                        }
                progress_bar.setVisibility(View.GONE);
                startActivity(new Intent(OTPVerify.this,Login.class));
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OTPVerify.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}