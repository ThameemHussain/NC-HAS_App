package com.nchas.thameem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calciadmin extends AppCompatActivity {
    private static final String TAG = "StartsHere!!!";
    private EditText a;
    private Button sub, delete ;
    private TextView n, d, i, dob, r;
    private ImageButton backk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calciadmin);


        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        a = (EditText) findViewById(R.id.adstuidet);

        sub = (Button) findViewById(R.id.adsubbtn);
        delete = (Button) findViewById(R.id.deletebtn);
        n = (TextView) findViewById(R.id.stunametv);
        d = (TextView) findViewById(R.id.Studepttv);
        i = (TextView) findViewById(R.id.stuidtv);
        dob = (TextView) findViewById(R.id.studobtv);
        r = (TextView) findViewById(R.id.roomnotv);


        backk = (ImageButton) findViewById(R.id.calciback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




String deldecide1=getIntent().getStringExtra("value");









        delete.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        String EntryDetails1 = a.getText().toString().trim();
        db1.collection("StudentDetails").document(EntryDetails1).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Calciadmin.this, "Student Document Deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Calciadmin.this, "Deletion Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
    });

sub.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        getdocument(deldecide1);
    }
    });

}

    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);


        return dates;
    }

    private void getdocument(String abcd){
        StudentDetails sd=new StudentDetails();
        String EntryDetails=a.getText().toString().trim();


        if (EntryDetails.isEmpty()){
            a.setError("ID is Required");
            a.requestFocus();
            return;

        }


       FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("StudentDetails").document(EntryDetails);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                     String Name=document.getString("name");
                        String DOB=document.getString("dob");
                        String StudentID=document.getString("uniqueID");
                        String Department=document.getString("department");
                        int Roomno=document.getLong("roomno").intValue();
                        String abc=Integer.toString(Roomno);

                        n.setText(Name);
                        dob.setText(DOB);
                        i.setText(StudentID);
                        d.setText(Department);
                        r.setText(abc);
                        if(abcd.equals("Show")) {
                            delete.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(Calciadmin.this, "No such ID Found", Toast.LENGTH_SHORT).show();
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Toast.makeText(Calciadmin.this,"Check your Internet Connection and try again",Toast.LENGTH_SHORT).show();
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

    }



}