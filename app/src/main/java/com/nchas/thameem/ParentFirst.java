package com.nchas.thameem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ParentFirst extends AppCompatActivity {
 private Button Find;
 private ImageButton back;
    private EditText StudentID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_first);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));


        StudentID=(EditText)findViewById(R.id.editTextparent);
        Find=(Button) findViewById(R.id.btnfind);
        back=(ImageButton) findViewById(R.id.parent1stback);

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();

    }
});






        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Aid=StudentID.getText().toString().trim();
                if (Aid.isEmpty()){
                    StudentID.setError("ID required");
                    StudentID.requestFocus();
                    return;
                }
                Intent intent2=new Intent(ParentFirst.this,ParentSecond.class);
                intent2.putExtra("enteredID",Aid);
                startActivity(intent2);

            }
        });

    }

}