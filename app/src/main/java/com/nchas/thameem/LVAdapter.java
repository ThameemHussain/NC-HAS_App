package com.nchas.thameem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.util.Log;
import android.content.DialogInterface;
import android.view.View.OnClickListener;



public class LVAdapter extends ArrayAdapter<StudentDetails> {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    // constructor for our list view adapter.
    public LVAdapter(@NonNull Context context, ArrayList<StudentDetails> Arraylt) {
        super(context, 0, Arraylt);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.image_lv_item, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        StudentDetails studentDetails = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        TextView  uniquestuid = listitemView.findViewById(R.id.textViewidno);
        TextView RoomNo1 = listitemView.findViewById(R.id.textViewRN);
        EditText Remarks=listitemView.findViewById(R.id.Remarkset);


     Button Present=listitemView.findViewById(R.id.presentbtn);
      Button Absent=listitemView.findViewById(R.id.absentbtn);
        ImageButton sendBtn=listitemView.findViewById(R.id.Remarksbtn);



String iiiddd= studentDetails.getUniqueID();
        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        String name=studentDetails.getName();
        nameTV.setText(name);
        String ac=Integer.toString(studentDetails.getRoomno());
        RoomNo1.setText(ac);
        uniquestuid.setText(iiiddd);


        String Phonenumber=studentDetails.getPhoneNo();

        String message1="Present today!!";
        String message2="Absent today!!";




        //present algo here
        Present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                Map<String, Object> Details = new HashMap<>();
                Map<String, Object> nestedData = new HashMap<>();
                nestedData.put("Attendance", "Present");






                Details.put(date, nestedData);


                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("StudentDetails").document(iiiddd).set(Details,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getContext(), "Data Updated as Present", Toast.LENGTH_SHORT).show();
                        SMSAnupu(Phonenumber,message1,name,iiiddd,date);




                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Unsuccessful ", Toast.LENGTH_SHORT).show();
                    }
                });







            }
        });

        //Absent algo here
        Absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Map<String, Object> Details = new HashMap<>();
                Map<String, Object> nestedData = new HashMap<>();
                nestedData.put("Attendance", "Absent");






                Details.put(date, nestedData);


                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("StudentDetails").document(iiiddd).set(Details,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Data Updated as Absent ", Toast.LENGTH_SHORT).show();
                        SMSAnupu(Phonenumber,message2,name,iiiddd,date);



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Unsuccessful ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Remarks msg send algo starts here..
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm")
                        .setMessage("Do you really want to send this info?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                String RemarksMessage=Remarks.getText().toString().trim();
                                if (RemarksMessage.isEmpty()){
                                    Remarks.setError("You can send remarks only after writing something");
                                    return;
                                }

                                //Storing Algo
                                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                Map<String, Object> Details = new HashMap<>();
                                Map<String, Object> nestedData = new HashMap<>();
                                Map<String, Object> nestedData1 = new HashMap<>();
                                nestedData.put("Remarks", RemarksMessage);


                                Details.put(date, nestedData);

                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                db.collection("StudentDetails").document(iiiddd).set(Details, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Remarks Stored", Toast.LENGTH_SHORT).show();
                                        SMSAnupu(Phonenumber,RemarksMessage,name,iiiddd,date);

                                        Remarks.setText(null);
                                        Remarks.clearFocus();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Storing Unsuccessful ", Toast.LENGTH_SHORT).show();
                                    }
                                });
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

        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Intent intent=new Intent(getContext(),realcalciadmin1.class);
                intent.putExtra("stuid",iiiddd);
                intent.putExtra("name",name);
                intent.putExtra("number",Phonenumber);
                v.getContext().startActivity(intent);

            }
        });

        return listitemView;
    }

    public void SMSAnupu(String a,String b,String name1,String iddd,String dated){

        Log.d("SMS ready to send", "----FIRST CALL----");


        Log.d("SMS ready to send", "----SECOND CALL----"+a);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(a, null, "Dated - " + dated+",\n" + name1 + " ("+iddd+") :\n"+ b +"\n -TNC Hostel", null, null);
        Toast.makeText(getContext(), "Msg sent successfully", Toast.LENGTH_SHORT).show();
        Log.d("SMS ready to send", "----THIRD CALL----");

    }



}

