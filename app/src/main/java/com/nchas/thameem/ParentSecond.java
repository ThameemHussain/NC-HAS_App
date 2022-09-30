package com.nchas.thameem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

public class ParentSecond extends AppCompatActivity {
    String TAG="kjhsdhf";
    LottieAnimationView anim1;
    LottieAnimationView anim2,anim3,anim4;

    private TextView displaytv,displayname,disdateyes,disdateyes2,disdateyes3,today;
    private ImageButton back2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_second);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        back2=(ImageButton) findViewById(R.id.Parent2ndback);
        displaytv=(TextView)findViewById(R.id.displayattendancetv);
        displayname=(TextView) findViewById(R.id.stunamepet);
        disdateyes=(TextView) findViewById(R.id.stuyespet);
        disdateyes2=(TextView) findViewById(R.id.stuyes2pet);
        disdateyes3=(TextView) findViewById(R.id.stuyes3pet);
        today=(TextView) findViewById(R.id.parentodayet);

        final MediaPlayer mediaPlayerC = MediaPlayer.create(ParentSecond.this, R.raw.correctanswer);
        final MediaPlayer mediaPlayerW = MediaPlayer.create(ParentSecond.this, R.raw.wronganswer);
        anim1 = (LottieAnimationView) findViewById(R.id.animationView);
        anim2 = (LottieAnimationView) findViewById(R.id.animationView);
        anim3 = (LottieAnimationView) findViewById(R.id.animationView);
        anim4 = (LottieAnimationView) findViewById(R.id.animationView);


        String Aid11=getIntent().getStringExtra("enteredID");




        GetInfo(mediaPlayerC,mediaPlayerW,anim1,anim2,anim3,anim4,Aid11);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finish();

            }
        });


    }

    public void GetInfo(MediaPlayer a, MediaPlayer b, LottieAnimationView anil, LottieAnimationView ani2 ,LottieAnimationView notfound,LottieAnimationView Nodata,String Aid1){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date2 = sdf.format(c.getTime());
        c.add(Calendar.DATE, -1);
        String dateyes = sdf.format(c.getTime());
        c.add(Calendar.DATE,-1);
        String dateyes2 = sdf.format(c.getTime());
        c.add(Calendar.DATE,-1);
        String dateyes3 = sdf.format(c.getTime());









        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("StudentDetails").document(Aid1).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){

                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()){
                        if (snapshot.getData().get(dateyes)!=null){
                            Map<String, Object> dateinfo2 = (Map<String, Object>) snapshot.getData().get(dateyes);
                            String attte2 = (String) dateinfo2.get("Attendance");
                            disdateyes.setText("Yesterday = "+ attte2 + " " );
                        }else{
                            disdateyes.setText("Yesterday = No Data ");
                        }
                            if (snapshot.getData().get(dateyes2)!=null){
                                Map<String, Object> dateinfo3 = (Map<String, Object>) snapshot.getData().get(dateyes2);
                                String attte3 = (String) dateinfo3.get("Attendance");
                                disdateyes2.setText(dateyes2+" = "+ attte3 + " " );
                            }else{
                                disdateyes2.setText(dateyes2 +" = No Data ");
                            }
                        if (snapshot.getData().get(dateyes3)!=null){
                            Map<String, Object> dateinfo4 = (Map<String, Object>) snapshot.getData().get(dateyes3);
                            String attte3 = (String) dateinfo4.get("Attendance");
                            disdateyes3.setText(dateyes3+" = "+ attte3 + " " );
                        }else{
                            disdateyes3.setText(dateyes3 +" = No Data ");
                        }



                        if (snapshot.getData().get(date2)!=null) {

                            Map<String, Object> dateinfo = (Map<String, Object>) snapshot.getData().get(date2);
                            String attte = (String) dateinfo.get("Attendance");
                            String nameee= snapshot.getString("name");
                            String unID=snapshot.getString("uniqueID");

                            displayname.setText(nameee+" (" + unID +") " +"is ");
                            displaytv.setText(attte + "! ");
                            if (Objects.equals(attte, "Present")) {
                                back2.setBackground(ContextCompat.getDrawable(ParentSecond.this, R.drawable.greenripple));
                                a.start();
                                anil.setAnimation(R.raw.successanimation);
                                anil.playAnimation();
                                displaytv.setTextColor(Color.parseColor("#11998e"));
                                displayname.setTextColor(Color.parseColor("#11998e"));
                                disdateyes.setTextColor(Color.parseColor("#11998e"));
                                disdateyes2.setTextColor(Color.parseColor("#11998e"));
                                disdateyes3.setTextColor(Color.parseColor("#11998e"));
                                today.setText("today ");
                                today.setTextColor(Color.parseColor("#11998e"));



                            } else {
                                back2.setBackground(ContextCompat.getDrawable(ParentSecond.this, R.drawable.redripple));
                                b.start();
                                ani2.setAnimation(R.raw.wrongfeedback);
                                ani2.playAnimation();
                                displaytv.setTextColor(Color.parseColor("#ED213A"));
                                displayname.setTextColor(Color.parseColor("#ED213A"));
                                disdateyes.setTextColor(Color.parseColor("#ED213A"));
                                disdateyes2.setTextColor(Color.parseColor("#ED213A"));
                                disdateyes3.setTextColor(Color.parseColor("#ED213A"));
                                today.setTextColor(Color.parseColor("#ED213A"));
                                today.setText("today ");



                            }
                        }else{
                            displaytv.setText("Not Updated Yet! ");
                            Nodata.setAnimation(R.raw.nodata);
                            Nodata.playAnimation();

                            Toast.makeText(ParentSecond.this, "Not Updated yet!!", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        displaytv.setText("No Details Found! ");
                        notfound.setAnimation(R.raw.notfound);
                        notfound.playAnimation();

                        Toast.makeText(ParentSecond.this,"No Details Found,check details provided or internet connection and try again",Toast.LENGTH_LONG).show();
                    }
                }else {   Log.w(TAG, "Error getting documents.", task.getException());}
            }
        });


    }

}