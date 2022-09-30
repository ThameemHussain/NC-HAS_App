package com.nchas.thameem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class adminselector extends AppCompatActivity {
private Button admin1,admin2,admin3,admin4,admin5,admin6;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminselector);
        getSupportActionBar().hide();

        admin1=(Button) findViewById(R.id.admin1);
        admin2=(Button) findViewById(R.id.admin2);
        admin3=(Button) findViewById(R.id.admin3);
        admin4=(Button) findViewById(R.id.admin4);
        admin5=(Button) findViewById(R.id.admin5);

        back=(ImageButton)findViewById(R.id.admselback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        admin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(adminselector.this,StudentList.class);
                intent1.putExtra("start",1);
                intent1.putExtra("end",27);
                startActivity(intent1);
            }
        });


        admin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(adminselector.this,StudentList.class);
                intent2.putExtra("start",33);
                intent2.putExtra("end",65);
                startActivity(intent2);
            }
        });


        admin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(adminselector.this,StudentList.class);
                intent3.putExtra("start",67);
                intent3.putExtra("end",99);
                startActivity(intent3);
            }
        });


        admin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(adminselector.this,StudentList.class);
                intent4.putExtra("start",101);
                intent4.putExtra("end",210);
                startActivity(intent4);
            }
        });


        admin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5=new Intent(adminselector.this,StudentList.class);
                intent5.putExtra("start",301);
                intent5.putExtra("end",352);
                startActivity(intent5);
            }
        });
    }


}