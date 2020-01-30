package com.example.fecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Farmer_Items_List extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<item_recyclerview> items;
    FirebaseAuth mAuth;
    item_recyclerview_myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer__items__list);
        items=new ArrayList<item_recyclerview>();
        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth=FirebaseAuth.getInstance();
        String uid=mAuth.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Farmers").child(uid).child("Items").child("Vegetables");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("tag",dataSnapshot.toString());
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    item_recyclerview it =data.getValue(item_recyclerview.class);
                    items.add(it);
                }
                adapter=new item_recyclerview_myadapter(Farmer_Items_List.this,items);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Farmer_Items_List.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
