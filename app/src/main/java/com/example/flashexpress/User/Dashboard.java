package com.example.flashexpress.User;

import static com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType.SLIDE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.flashexpress.LoginSignup.Login;
import com.example.flashexpress.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class Dashboard extends AppCompatActivity {

    String nameFromDB="",emailFromDB="",phoneFromDB="",passwordFromDB="";
    SliderView sliderView;
    int[] images ={R.drawable.slideshow_1,R.drawable.slideshow_2,R.drawable.slideshow_3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //sliderView
        sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);

        //Animations(SliderView)
        sliderView.setIndicatorAnimation(SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        //method Calls
        showUserData();
    }


    private void showUserData() {
        emailFromDB = getIntent().getStringExtra("email");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("email").equalTo(emailFromDB);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        nameFromDB = snapshot1.child("name").getValue().toString();
                        emailFromDB = snapshot1.child("email").getValue().toString();
                        phoneFromDB = snapshot1.child("phone").getValue().toString();
                    }
                }

                else
                    Toast.makeText(Dashboard.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to Log Out?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // clear the current activity and all the activities in the stack
                finishAffinity();
                startActivity(new Intent(Dashboard.this, Login.class));
                Toast.makeText(Dashboard.this, "Succesfully Logged out!", Toast.LENGTH_LONG).show();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public void sendParcelIcon(View view) {
        Intent intent = new Intent(Dashboard.this, SendParcel.class);
        intent.putExtra("name", nameFromDB);
        intent.putExtra("email",emailFromDB);
        intent.putExtra("phone", phoneFromDB);
        startActivity(intent);
    }

    public void contactusIcon(View view) {
        Intent intent = new Intent(Dashboard.this, ContactUs.class);
        intent.putExtra("email",emailFromDB);
        startActivity(intent);
    }

    public void profileIcon(View view) {
        Intent intent = new Intent(Dashboard.this, Profile.class);
        intent.putExtra("name", nameFromDB);
        intent.putExtra("email", emailFromDB);
        intent.putExtra("phone", phoneFromDB);
        startActivity(intent);
    }

    public void FAQIcon(View view) {
        Intent intent = new Intent(Dashboard.this, FAQ.class);
        intent.putExtra("email",emailFromDB);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}