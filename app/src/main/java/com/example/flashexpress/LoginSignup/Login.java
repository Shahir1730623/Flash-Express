package com.example.flashexpress.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.flashexpress.MainActivity;
import com.example.flashexpress.R;
import com.example.flashexpress.User.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;
    TextInputLayout emailEditTxt,passwordEditTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        emailEditTxt = findViewById(R.id.emailEditTxt);
        passwordEditTxt = findViewById(R.id.passwordEditTxt);

        //Database
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
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

    private boolean validatePassword() {
        String val = passwordEditTxt.getEditText().getText().toString();

        if (val.isEmpty()) {
            passwordEditTxt.setError("The field is empty");
            return false;
        }

        else {
            passwordEditTxt.setError(null);
            passwordEditTxt.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view) {
        if (!validateEmail() || !validatePassword()) {
            return;
        }
        else{
            checkUser();
        }
    }

    private void checkUser() {
        final String userEnteredEmail = emailEditTxt.getEditText().getText().toString();
        final String userEnteredPassword = passwordEditTxt.getEditText().getText().toString();

        auth.signInWithEmailAndPassword(userEnteredEmail,userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    intent.putExtra("email",userEnteredEmail);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(Login.this, "Wrong Credentials,try again", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void goToRegisterPage(View view) {
        Intent intent = new Intent(Login.this,Registration.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    public void goToForgetPasswordScreen(View view) {
        Intent intent = new Intent(Login.this,ForgetPassword.class);
        startActivity(intent);
    }
}