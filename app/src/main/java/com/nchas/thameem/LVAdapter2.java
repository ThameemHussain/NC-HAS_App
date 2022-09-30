package com.nchas.thameem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.security.auth.callback.PasswordCallback;


public class LVAdapter2 extends ArrayAdapter<StudentDetails> {






    // constructor for our list view adapter.
    public LVAdapter2(@NonNull Context context, ArrayList<StudentDetails> Arraylt) {
        super(context, 0, Arraylt);
    }



    @NonNull

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.image_lv_item2, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        StudentDetails studentDetails = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        TextView  uniquestuid = listitemView.findViewById(R.id.textViewidno);
        TextView RoomNo1 = listitemView.findViewById(R.id.textViewRN);
        Spinner orders=listitemView.findViewById(R.id.spinnerfod);
        Spinner count=listitemView.findViewById(R.id.spinnerfod2);



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


        String Phonenumber=studentDetails.getStudphone();

        String message1="Extra food got by your son : ";








        //Food msg send algo starts here..
        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm")
                        .setMessage("Do you really want to send this info?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                                String order = orders.getSelectedItem().toString().trim();
                                String counts=count.getSelectedItem().toString().trim();
                                int num=Integer.parseInt(counts);
                               int Price=num*Findrupees(order);
                                SMSAnupu(Phonenumber,message1,order,counts,Price,name,iiiddd,date);





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
        listitemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.

            }
        });

        return listitemView;
    }

    public int Findrupees(String ghj){

        switch (ghj){
            case "Veg Rice":
            case "Bread Toast":
            case "Sada Dosa":
            case "Chappati":
            case "Dosai":
            case "Uthappam":
            case "Parotta":
            case "Egg":
            case "Vadai":
            case "Pongal":
                return 10;

            case "Fish Fry":
            case "Chicken 65":
                return 50;

            case "Egg Masala":
                return 25;

            case "Idly":
            case "Poori":
                return 8;

            case "Cutlet":
                return 20;

            case "Chicken Chops":
            case "Liver":
                return 30;

            case "Appalam":
            case "Mini Poori":
                return 5;

            case "Masal Dosa":
                return 15;

            case "Special Chicken":
                return 35;
            default:
                return 0;
        }
    }



    public void SMSAnupu(String a,String b,String c,String cou,int total,String name1,String iddd,String dated){

        Log.d("SMS ready to send", "----FIRST CALL----");


        Log.d("SMS ready to send", "----SECOND CALL----"+a);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(a, null, "Dated - " + dated+",\n" + name1 + " ("+iddd+") :\n"+ b + c +" x "+cou+"="+"Rs."+total+"\n -TNC Hostel", null, null);
        Toast.makeText(getContext(), "Msg sent successfully.\nTotal amount ="+total, Toast.LENGTH_SHORT).show();
        Log.d("SMS ready to send", "----THIRD CALL----");

    }

}

