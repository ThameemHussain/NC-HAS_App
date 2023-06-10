package com.nchas.thameem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Rooms extends AppCompatActivity {

    ArrayList<roomsDataModel> roomsDataModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        RecyclerView recyclerView =findViewById(R.id.myrecyclerView);
        getFromDatabase();
        RoomsAdapter adapter= new RoomsAdapter(this,roomsDataModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }
    private void getFromDatabase(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
                db.collection("Rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Long totalCapacityLong = (Long) document.getData().get("Total capacity");
                                Long availableSeatsLong = (Long) document.getData().get("Available seats");
                                String totalCapacityString = totalCapacityLong != null ? totalCapacityLong.toString() : "";
                                String availableSeatsString = availableSeatsLong != null ? availableSeatsLong.toString() : "";

                                int totalCapacity = 0;
                                int availableSeats = 0;

                                if (totalCapacityString != null && availableSeatsString != null) {
                                    try {
                                        totalCapacity = Integer.parseInt(totalCapacityString);
                                        availableSeats = Integer.parseInt(availableSeatsString);
                                        Log.d("ValueCheck",Integer.parseInt(document.getId()) + " => " + totalCapacity);
                                        Log.d("ValueCheck",Integer.parseInt(document.getId()) + " => " + availableSeats);

                                    } catch (NumberFormatException e) {
                                        // Handle the case where the strings are not valid integers
                                        // Display an error message or perform appropriate error handling
                                    }
                                }

                                roomsDataModel roomsDataModel = new roomsDataModel(Integer.parseInt(document.getId()), totalCapacity, availableSeats);
                                roomsDataModels.add(roomsDataModel);
                                Log.d("Check",document.getId() + " => " + document.getData());
                            }
                        }else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}