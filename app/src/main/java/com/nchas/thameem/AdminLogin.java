package com.nchas.thameem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {


    private Button AdminLoginn;
    private EditText name;
    private EditText Password;
    private ImageButton back;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
      getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        name=(EditText) findViewById(R.id.editTextTextPersonName);
        Password=(EditText) findViewById(R.id.editTextTextPassword);
        AdminLoginn=(Button)findViewById(R.id.button);
        back=(ImageButton)findViewById(R.id.loginback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        AdminLoginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
                checkPermission(Manifest.permission.SEND_SMS, MY_PERMISSIONS_REQUEST_SEND_SMS);

            }
        });
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }}

    private void Login(){
        String COMname=name.getText().toString().trim();
        String COMpass=Password.getText().toString().trim();

        if(COMname.isEmpty()){
            name.setError("Required Field");
            name.requestFocus();
            return;

        }if (COMpass.isEmpty()){
            Password.setError("Required Field");
            Password.requestFocus();
            return;

        }

        if((COMname.equals("NCHostel_Admin23*")) && (COMpass.equals("5*59%31ok"))){
            Intent intent=new Intent(AdminLogin.this,adminselector.class);
            startActivity(intent);
        }


        else if((COMname.equals("NCMess_Admin32*")) && (COMpass.equals("5258#food%"))){
            Intent intent2=new Intent(AdminLogin.this,StudentList2.class);
            startActivity(intent2);
        }
        else{
            Toast.makeText(this, "Incorrect Credentials!", Toast.LENGTH_SHORT).show();
        }
    }
}