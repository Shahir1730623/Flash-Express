package com.example.flashexpress.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.flashexpress.MainActivity;
import com.example.flashexpress.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout emailEditTxt;
    FirebaseAuth auth;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //initialize
        emailEditTxt = findViewById(R.id.emailEditTxt);
        progress_bar = findViewById(R.id.progress_bar);
        auth = FirebaseAuth.getInstance();

    }

    private boolean validateEmail() {
        String val = emailEditTxt.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            emailEditTxt.setError("The field is empty");
            return false;
        }
        else if (!val.matches(emailPattern)) {
            emailEditTxt.setError("Invalid Email Address");
            return false;
        }

        else {
            emailEditTxt.setError(null);
            emailEditTxt.setErrorEnabled(false);
            return true;
        }
    }



    public void sendOTP(View view) {
        String userEmail = emailEditTxt.getEditText().getText().toString();

        if (!validateEmail()) {
            return;
        }

        progress_bar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                    progress_bar.setVisibility(View.GONE);
                    startActivity(new Intent(ForgetPassword.this, Login.class));
                }
                else {
                    Toast.makeText(ForgetPassword.this, "Email does not exist!", Toast.LENGTH_LONG).show();
                    progress_bar.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
