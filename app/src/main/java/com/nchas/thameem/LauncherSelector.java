package com.nchas.thameem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LauncherSelector extends AppCompatActivity {
private Button Parent;
private Button Admin,Messadmin;
private ImageView Logo;
private TextView Wel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_selector);

        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        Parent=(Button) findViewById(R.id.Parentbtn);
        Admin=(Button) findViewById(R.id.Adminbtn);
        Messadmin=(Button)findViewById(R.id.Messbtn);
        Logo=(ImageView)findViewById(R.id.imageView);
        Wel=(TextView)findViewById(R.id.textView6);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clockwise);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_left);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_right);
        Logo.startAnimation(animation1);
        Parent.startAnimation(animation2);
        Admin.startAnimation(animation3);
        Wel.startAnimation(animation2);
        Messadmin.startAnimation(animation2);


        Messadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LauncherSelector.this,AdminLogin.class);
                startActivity(intent);
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LauncherSelector.this,AdminLogin.class);
                startActivity(intent);
            }
        });

 Parent.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent intent =new Intent(LauncherSelector.this,ParentFirst.class);
         startActivity(intent);
     }
 });
    }




}