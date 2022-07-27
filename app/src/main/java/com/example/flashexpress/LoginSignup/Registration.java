package com.example.flashexpress.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.flashexpress.Model.User;
import com.example.flashexpress.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;
    int x;
    TextInputLayout nameEditTxt,emailEditTxt,phoneEditTxt,passwordEditTxt;
    long totalUser=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initialize
        nameEditTxt = findViewById(R.id.nameEditTxt);
        emailEditTxt = findViewById(R.id.emailEditTxt);
        phoneEditTxt = findViewById(R.id.phoneEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);

        //Database
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    totalUser = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private boolean validateName() {
        String val = nameEditTxt.getEditText().getText().toString();
        if (val.isEmpty()) {
            nameEditTxt.setError("The field is empty");
            return false;
        }

        else {
            nameEditTxt.setError(null);
            nameEditTxt.setErrorEnabled(false);
            return true;
        }
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

    private boolean validatePhone() {
        String val = phoneEditTxt.getEditText().getText().toString();
        if (val.isEmpty()) {
            phoneEditTxt.setError("The field is empty");
            return false;
        }

        else if (val.length() != 11) {
            phoneEditTxt.setError("Wrong phone number");
            return false;
        }

        else {
            phoneEditTxt.setError(null);
            phoneEditTxt.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordEditTxt.getEditText().getText().toString();
        String passwordVal = "^" +    // start-of-string
                "(?=.*[0-9])" +       // a digit must occur at least once
                "(?=.*[a-z])" +      // a lower case letter must occur at least once
                "(?=.*[A-Z])" +      // an upper case letter must occur at least once
                "(?=\\S+$)" +      // no whitespace allowed in the entire string
                ".{4,}" +     // anything, at least six places though
                "$";                 // end-of-string

        if (val.isEmpty()) {
            passwordEditTxt.setError("The field is empty");
            return false;
        }

        else if (!val.matches(passwordVal)) {
            passwordEditTxt.setError("Password too weak");
            return false;
        }

        else {
            passwordEditTxt.setError(null);
            passwordEditTxt.setErrorEnabled(false);
            return true;
        }
    }

    public void Register(View view) {
        if (!validateName() || !validateEmail() || !validatePhone() || !validatePassword()) {
            return;
        }

        String name = nameEditTxt.getEditText().getText().toString();
        String email = emailEditTxt.getEditText().getText().toString();
        String phone = "+88" + phoneEditTxt.getEditText().getText().toString();
        String password = passwordEditTxt.getEditText().getText().toString();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name, email, phone);
                    reference.child(String.valueOf(totalUser + 1)).setValue(user);
                    Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    goToLoginPage(view);
                }

                else{
                    Toast.makeText(Registration.this, "Registration Unuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    public void goToLoginPage(View view) {
        Intent intent = new Intent(Registration.this,Login.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}