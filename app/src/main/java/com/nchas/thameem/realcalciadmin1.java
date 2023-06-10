package com.nchas.thameem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class realcalciadmin1 extends AppCompatActivity {
    private DatePicker simpleDatePicker, datepicker2;
    private Button dapi;
    private ImageButton perMsg, Back;
    private TextView Answer, msgHint;
    private String TAG="HEREEEE";
    public int Counter=0;
    public float nump1;
    public float nump2;
    public float nump3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realcalciadmin1);
        getSupportActionBar().hide();

        simpleDatePicker = (DatePicker) findViewById(R.id.datePicker);
        datepicker2 = (DatePicker) findViewById(R.id.datePicker2);
        dapi = (Button) findViewById(R.id.button2);
        perMsg = (ImageButton) findViewById(R.id.PercentageSender);
        Back = (ImageButton) findViewById(R.id.loginback2);
        Answer=(TextView)findViewById(R.id.Percentage);
        msgHint=(TextView)findViewById(R.id.textView10);

        String StuID=getIntent().getStringExtra("stuid");
        String Name=getIntent().getStringExtra("name");
        String Number1=getIntent().getStringExtra("number");


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //datepickeralgo
        dapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker
                int day = simpleDatePicker.getDayOfMonth();
                int month = (simpleDatePicker.getMonth() + 1);
                int year = simpleDatePicker.getYear();
                String from = String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
                int day1 = datepicker2.getDayOfMonth();
                int month2 = (datepicker2.getMonth() + 1);
                int year3 = datepicker2.getYear();
                String to = String.valueOf(day1) + "-" + String.valueOf(month2) + "-" + String.valueOf(year3);
                Log.d("From",from);
                Log.d("To",to);
                LocalDate start = LocalDate.parse(from, DateTimeFormatter.ofPattern("d-M-yyyy"));
                LocalDate end = LocalDate.parse(to, DateTimeFormatter.ofPattern("d-M-yyyy"));
                long numofDays = ChronoUnit.DAYS.between(start, end);
                nump1=Long.valueOf(numofDays).floatValue()+1;
                Log.d("numofdays","Total no. of days No change "+nump1);
                nump2=nump1;
                List<LocalDate> FromTo = Stream.iterate(start, date -> date.plusDays(1)).limit(numofDays + 1).collect(Collectors.toList());
                List<String> temp = new ArrayList(FromTo.size());
                for (LocalDate date : FromTo) {
                    temp.add(date.toString()); //toString() or the appropriate method

                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String[] SundayArray = temp.toArray(new String[FromTo.size()]);
                Log.d("SundayArray","String Array of Dates "+SundayArray);
                    for (String date1 : SundayArray) {

                        String ab = getFormatedDate(date1, "yyyy-MM-dd", "dd-MM-yyyy");
                        db.collection("StudentDetails").document(StuID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot snapshot = task.getResult();
                                    if (snapshot.getData().get(ab) != null) {
                                        Map<String, Object> dateinfo = (Map<String, Object>) snapshot.getData().get(ab);
                                        String attte = (String) dateinfo.get("Attendance");

                                        Log.d("Attendance",ab+attte);
                                        if (attte.equals("Present")){
                                             Counter += 1;

                                        }

                                    }else{
                                        nump2 =nump2- 1;
                                        nump3=nump2;
                                        Log.d("numofdays","Total no. of days With change 000 "+nump2);

                                    }

                                }

                            }


                        });

                    }

                Log.d("Counter","No. of days Present "+Counter);
                Log.d("numofdays","Total no. of days With change "+nump3);
                    try {
                        float percentage =Counter*100/nump3;
                        if(Counter==0){
                            dapi.setText("Calculate");
                            Answer.setText("Click Again!!");
                      }
                        else{
                            Answer.setText(Name+"'s Attendance Percentage is "+percentage);
                            dapi.setVisibility(View.INVISIBLE);
                            msgHint.setVisibility(View.VISIBLE);
                            perMsg.setVisibility(View.VISIBLE);
                            PerSender(perMsg,Number1,"Your son's Attendance from "+from+" to "+to+" is "+ percentage+"%",Name,StuID);
                            Log.d("Percentage","Attendance percentage "+percentage);
                        }
                    }catch (ArithmeticException e){
                        Log.d("Exception ",e.toString());
                    }

//                long ab=numofDays+1;
//                System.out.println("Total days"+ab );

            }

        });

    }
    //Percentage msg send algo starts here..
    public void PerSender(ImageButton sendbtn,String Number1, String RemarksMessage, String Name ,String StuID)
    {
        sendbtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(realcalciadmin1.this)
                    .setTitle("Confirm")
                    .setMessage("Do you really want to send this info?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            SMSAnupu(Number1,RemarksMessage,Name,StuID,date);
                        }})
                    .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    }).show();




        }
    });
        }
    public static String getFormatedDate(String strDate, String sourceFormate,
                                         String destinyFormate) {
        SimpleDateFormat df;
        df = new SimpleDateFormat(sourceFormate);
        Date date = null;
        try {
            date = df.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat(destinyFormate);
        return df.format(date);

    }
    public void SMSAnupu(String a,String b,String name1,String iddd,String dated){

        Log.d("SMS ready to send", "----FIRST CALL----");


        Log.d("SMS ready to send", "----SECOND CALL----"+a);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(a, null, "Dated - " + dated+",\n" + name1 + " ("+iddd+") :\n"+ b +"\n -TNC Hostel", null, null);
        Toast.makeText(realcalciadmin1.this, "Msg sent successfully", Toast.LENGTH_SHORT).show();
        Log.d("SMS ready to send", "----THIRD CALL----");

    }
}
