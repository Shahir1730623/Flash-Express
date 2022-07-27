package com.example.flashexpress.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.flashexpress.Model.Order;
import com.example.flashexpress.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendParcel extends AppCompatActivity {

    DatabaseReference reference;
    TextInputLayout receiverNameEditTxt,receiverPhoneEditTxt,senderAddressEditTxt,receiverAddressEditTxt,itemNamesEditTxt,itemWeightEditTxt;
    AutoCompleteTextView serviceType;
    String userFullName,userEmail,userPhone;
    String orderId,senderName,receiverName,senderPhone,receiverPhone,senderAddress,receiverAddress,courierService,itemNames,totalWeight;
    String[] items = {"Standard","Express"};
    long maxID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_parcel);

        //Initialize
        receiverNameEditTxt = findViewById(R.id.receiverNameEditTxt);
        receiverPhoneEditTxt = findViewById(R.id.receiverPhoneEditTxt);
        senderAddressEditTxt = findViewById(R.id.senderAddressEditTxt);
        receiverAddressEditTxt = findViewById(R.id.receiverAddressEditTxt);
        serviceType = findViewById(R.id.courierType);
        itemNamesEditTxt = findViewById(R.id.itemNamesEditTxt);
        itemWeightEditTxt = findViewById(R.id.itemWeightEditTxt);

        //Dropdown
        ArrayAdapter<String> adapterItems= new ArrayAdapter<String>(SendParcel.this,R.layout.dropdown_item,items);
        serviceType.setAdapter(adapterItems);

        //GetIntent()
        userFullName = getIntent().getStringExtra("name");
        userEmail = getIntent().getStringExtra("email");
        userPhone = getIntent().getStringExtra("phone");

        reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxID = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        serviceType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                courierService = parent.getItemAtPosition(position).toString();
            }
        });

    }

    public void pickupRequest(View view) {
        orderId = String.valueOf(maxID + 1);
        senderName = userFullName;
        receiverName = receiverNameEditTxt.getEditText().getText().toString();
        senderPhone = userPhone;
        receiverPhone = receiverPhoneEditTxt.getEditText().getText().toString();
        senderAddress = senderAddressEditTxt.getEditText().getText().toString();
        receiverAddress = receiverAddressEditTxt.getEditText().getText().toString();
        itemNames = itemNamesEditTxt.getEditText().getText().toString();
        totalWeight = itemWeightEditTxt.getEditText().getText().toString();

        Order order = new Order(orderId, senderName, receiverName, senderPhone, receiverPhone,senderAddress,receiverAddress,courierService,itemNames,totalWeight);
        reference.child(orderId).setValue(order);
        Toast.makeText(SendParcel.this, "Request Sent", Toast.LENGTH_SHORT).show();
        onBackPressed();


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SendParcel.this, Dashboard.class);
        intent.putExtra("email", userEmail);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}