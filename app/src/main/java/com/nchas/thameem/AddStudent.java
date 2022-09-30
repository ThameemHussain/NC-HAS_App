package com.nchas.thameem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {
 private EditText Stuname,DOB,Dep,ROOMNO,UID,mobile,studmobile;
 private Button ADDbutton;
 private ImageButton bac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

      getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        Stuname=(EditText) findViewById(R.id.Studentnameet);
        DOB=(EditText) findViewById(R.id.dobet);
        Dep=(EditText) findViewById(R.id.Departmentet);
        ROOMNO=(EditText) findViewById(R.id.roomnoet);
        UID=(EditText) findViewById(R.id.IDet2);
        mobile=(EditText)findViewById(R.id.phoneet);
        studmobile=(EditText)findViewById(R.id.phoneet4);


        ADDbutton=(Button) findViewById(R.id.btnADDStudent);
        bac=(ImageButton)findViewById(R.id.addstunback);
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

                ADDbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADDSTU();
            }
        });




    }

    public void ADDSTU(){
        String name=Stuname.getText().toString().trim();
        String uniqueID=UID.getText().toString().trim();
        String dob= DOB.getText().toString().trim();
        String department=Dep.getText().toString().trim();
        String ab=ROOMNO.getText().toString().trim();

        if (ab.isEmpty()){
            ROOMNO.setError("Room number is Required");
            ROOMNO.requestFocus();
            return;

        }
        int roomno=Integer.parseInt(ab);
        String phoneNo=mobile.getText().toString().trim();
        String studphone=studmobile.getText().toString().trim();

        if (name.isEmpty()){
            Stuname.setError("Name is Required");
            Stuname.requestFocus();
            return;

        }
        if (dob.isEmpty()){
            DOB.setError("D.O.B. is Required");
            DOB.requestFocus();
            return;

        }
        if (department.isEmpty()){
            Dep.setError("Department is Required");
            Dep.requestFocus();
            return;

        }

        if (uniqueID.isEmpty()){
            UID.setError("ID is Required");
            UID.requestFocus();
            return;

        }
        if (phoneNo.isEmpty()){
            mobile.setError("Mobile number is Required");
            mobile.requestFocus();
            return;

        }

        if (studphone.isEmpty()){
            studmobile.setError("Mobile number is Required");
            studmobile.requestFocus();
            return;

        }

        Map<String ,Object> Details=new HashMap<>();
        Details.put("name",name);
        Details.put("dob",dob);
        Details.put("uniqueID",uniqueID);
        Details.put("department",department);
        Details.put("roomno",roomno);
        Details.put("phoneNo",phoneNo);
        Details.put("studphone",studphone);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
db.collection("StudentDetails").document(uniqueID).set(Details).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddStudent.this, "Successful ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddStudent.this, "Unsuccessful, Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}