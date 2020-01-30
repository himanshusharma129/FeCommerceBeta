package com.example.fecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class addProduct extends AppCompatActivity {
    private ImageView clicedPic;
    private Button takePic;
    private DatabaseReference databaseReference,itemReference;
    private FirebaseAuth mauth;
    private Bitmap bitmapImage;
    private String UID;
    private String item_name, item_kg,item_price,item_category,item_location;
    private ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        mprogress=new ProgressDialog(this);
        takePicListner();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        UID=currentuser.getUid();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                EditText Iname_Edit = (EditText)findViewById(R.id.editItemName);
                EditText avail_Edit = (EditText)findViewById(R.id.editItemKg);
                RadioGroup Rgroup = (RadioGroup)findViewById(R.id.CategoryRadioGrp);
                EditText priceEdit = (EditText)findViewById(R.id.priceEdit);
                EditText location_edit = (EditText)findViewById(R.id.location_edit);

                int id=Rgroup.getCheckedRadioButtonId();

                RadioButton selRadioButton = (RadioButton)findViewById(id);
                item_category = (String) selRadioButton.getText();
                item_name=Iname_Edit.getEditableText().toString().trim();
                item_kg=avail_Edit.getEditableText().toString().trim();
                item_price=priceEdit.getEditableText().toString().trim();
                item_location = location_edit.getEditableText().toString().trim();


                if(item_name.isEmpty()||item_kg.isEmpty()||item_category.isEmpty()||item_price.isEmpty())
                    Toast.makeText(addProduct.this,"All Inputs are needed",Toast.LENGTH_SHORT).show();
                else {
                    mprogress.setTitle("Uploading");
                    mprogress.show();
                    upload_item_data();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void takePicListner(){
        takePic = (Button)findViewById(R.id.takeImageButton);
        clicedPic = (ImageView)findViewById(R.id.PicView);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmapImage = (Bitmap)data.getExtras().get("data");
        clicedPic.setImageBitmap(bitmapImage);
    }

    public void upload_item_data(){

        mauth=FirebaseAuth.getInstance();
        String uid=mauth.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Farmers").child(UID).child("Items").child(item_category).child(item_name);
        HashMap<String ,String> itemDataMap=new HashMap<>();
        //itemDataMap.put("ItemName",item_name);
        itemDataMap.put("name",item_name);
        itemDataMap.put("price",item_price);
        itemDataMap.put("location",item_location);
        itemDataMap.put("WtAvail",item_kg);

        //itemDataMap.put("Category",item_category);

        databaseReference.setValue(itemDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mprogress.dismiss();
                    Toast.makeText(addProduct.this,"Iteam Uploaded",Toast.LENGTH_SHORT).show();
                    upload_to_items();
                    Intent i = new Intent(addProduct.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void upload_to_items(){

        itemReference = FirebaseDatabase.getInstance().getReference().child("Items").child(item_category).child(item_name);
        HashMap<String ,String> itemDMap=new HashMap<>();
        //itemDataMap.put("ItemName",item_name);
        itemDMap.put("name",item_name);
        itemDMap.put("price",item_price);
        itemDMap.put("location",item_location);
        itemDMap.put("WtAvail",item_kg);
        itemDMap.put("UID",UID);



        itemReference.setValue(itemDMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
