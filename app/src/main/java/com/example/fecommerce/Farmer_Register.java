package com.example.fecommerce;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Farmer_Register extends AppCompatActivity {
    private TextInputLayout name,adhaar,account,bank,mobile,address,email,pass;
    private Button sign;
    private String f_name,f_acc,f_email,f_adhaar,f_bank,f_mobile,f_address,f_pass;
    private FirebaseAuth mAuth;
    private String ud;
    private DatabaseReference mdatabase;
    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer__register);

        mAuth=FirebaseAuth.getInstance();
        mprogress=new ProgressDialog(this);
        sign=(Button)findViewById(R.id.signin);
        email=(TextInputLayout) findViewById(R.id.email);
        name=(TextInputLayout)findViewById(R.id.name);
        adhaar=(TextInputLayout)findViewById(R.id.adhaar);
        account=(TextInputLayout)findViewById(R.id.account);
        bank=(TextInputLayout)findViewById(R.id.bank);
        mobile=(TextInputLayout)findViewById(R.id.mobile);
        address=(TextInputLayout)findViewById(R.id.address);
        pass=(TextInputLayout)findViewById(R.id.pass);




        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f_pass=pass.getEditText().getText().toString().trim();
                f_name=name.getEditText().getText().toString().trim();
                f_email=email.getEditText().getText().toString().trim();
                f_acc=account.getEditText().getText().toString().trim();
                f_adhaar=adhaar.getEditText().getText().toString().trim();
                f_address=address.getEditText().getText().toString().trim();
                f_bank=bank.getEditText().getText().toString().trim();
                f_mobile=mobile.getEditText().getText().toString().trim();

                if(f_acc.isEmpty()||f_address.isEmpty()||f_adhaar.isEmpty()||f_bank.isEmpty()||f_mobile.isEmpty()||f_name.isEmpty()||f_pass.isEmpty()||f_email.isEmpty()){
                    Toast.makeText(Farmer_Register.this,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else {
                    mprogress.setTitle("Signing In");
                    mprogress.show();
                    signin();
                }
            }
        });




    }

    private void signin() {

        mAuth.createUserWithEmailAndPassword(f_email, f_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                            ud=currentuser.getUid();

                            mdatabase= FirebaseDatabase.getInstance().getReference().child("Farmers").child(ud);

                            HashMap<String ,String> map=new HashMap<>();
                            map.put("name",f_name);
                            map.put("adhaar",f_adhaar);
                            map.put("address",f_address);
                            map.put("bank_name",f_bank);
                            map.put("account_no",f_acc);
                            map.put("mobile",f_mobile);


                            mdatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        mprogress.dismiss();
                                        Intent i = new Intent(Farmer_Register.this, Farmer_Login.class);
                                        startActivity(i);
                                    }
                                }
                            });

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            mprogress.hide();
                            Log.e("tag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
