package com.example.fecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class F_Login_Or_Register extends AppCompatActivity {

    Button Login_farmer,Register_Farmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f__login__or__register);

        loginButtonClickListner();
        registerButtonClickListener();


    }

    private void registerButtonClickListener() {
        Register_Farmer = (Button)findViewById(R.id.F_Register);
        Register_Farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(F_Login_Or_Register.this, Farmer_Register.class);
                startActivity(i);
            }
        });
    }



    public void loginButtonClickListner(){
        Login_farmer = (Button)findViewById(R.id.F_Login);
        Login_farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(F_Login_Or_Register.this, Farmer_Login.class);
                startActivity(i);
            }
        });
    }

}
