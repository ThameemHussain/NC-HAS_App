package com.nchas.thameem;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nchas.thameem.databinding.ActivityStudentListBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


public class StudentList extends AppCompatActivity {

    private static final String TAG ="tag1" ;
    private AppBarConfiguration appBarConfiguration;
    private ActivityStudentListBinding binding;
    private FloatingActionButton AddStu , Studetails;
    private ListView List11;
    private SwipeRefreshLayout refreshLayout;
    private SearchView searchView;
    private ImageView logo;

    private ProgressBar progressBar;
private ImageButton back;



    ArrayList<StudentDetails> Arraylt;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_student_list);

        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        List11=(ListView)findViewById(R.id.listview);
        logo=(ImageView) findViewById(R.id.Imagelogo);
        AddStu=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        Studetails=(FloatingActionButton) findViewById(R.id.floatingActionButton2);
        progressBar=(ProgressBar)findViewById(R.id.stulistpb);
        back=(ImageButton)findViewById(R.id.listback);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshView);
        searchView=(SearchView) findViewById(R.id.searchbar2);
        int a=getIntent().getIntExtra("start",0);
        int b=getIntent().getIntExtra("end",0);




        Arraylt= new ArrayList<>();



Studetails.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(StudentList.this,Calciadmin.class);
        intent.putExtra("value","Show");
        startActivity(intent);
    }
});

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});

AddStu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(StudentList.this,AddStudent.class);
        startActivity(intent);
    }
});





        db=FirebaseFirestore.getInstance();


        loadDatainListview(a,b,0);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                int parsedint=Integer.parseInt(s);
                loadDatainListview(a,b,parsedint);
                Arraylt.clear();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                logo.setVisibility(INVISIBLE);

                return false;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

loadDatainListview(a,b,0);
                logo.setVisibility(View.VISIBLE);
                refreshLayout.setRefreshing(false);
                Arraylt.clear();


            }
        });



    }

    private void loadDatainListview(int start,int end,int input) {

        // below line is use to get data from Firebase
        // firestore using collection in android.
        if (input==0) {
            db.collection("StudentDetails").orderBy("roomno", Query.Direction.ASCENDING).whereGreaterThanOrEqualTo("roomno", start).whereLessThanOrEqualTo("roomno", end).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            // after getting the data we are calling on success method
                            // and inside this method we are checking if the received
                            // query snapshot is empty or not.
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // if the snapshot is not empty we are hiding
                                // our progress bar and adding our data in a list.
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    // after getting this list we are passing
                                    // that list to our object class.
                                    StudentDetails studentDetails = d.toObject(StudentDetails.class);

                                    // after getting data from Firebase we are
                                    // storing that data in our array list
                                    Arraylt.add(studentDetails);


                                }
                                // after that we are passing our array list to our adapter class.
                                LVAdapter adapter = new LVAdapter(StudentList.this, Arraylt);

                                // after passing this array list to our adapter
                                // class we are setting our adapter to our list view.

                                List11.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(INVISIBLE);


                            } else {

                                // if the snapshot is empty we are displaying a toast message.
                                Toast.makeText(StudentList.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(INVISIBLE);
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // we are displaying a toast message
                            // when we get any error from Firebase.

                            Toast.makeText(StudentList.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            // below line is use to get data from Firebase
            // firestore using collection in android.
            db.collection("StudentDetails").whereEqualTo("roomno",input).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            // after getting the data we are calling on success method
                            // and inside this method we are checking if the received
                            // query snapshot is empty or not.
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // if the snapshot is not empty we are hiding
                                // our progress bar and adding our data in a list.
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    // after getting this list we are passing
                                    // that list to our object class.
                                    StudentDetails studentDetails = d.toObject(StudentDetails.class);

                                    // after getting data from Firebase we are
                                    // storing that data in our array list
                                    Arraylt.add(studentDetails);


                                }
                                // after that we are passing our array list to our adapter class.
                                LVAdapter adapter = new LVAdapter(StudentList.this, Arraylt);

                                // after passing this array list to our adapter
                                // class we are setting our adapter to our list view.

                                List11.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(INVISIBLE);




                            } else {
                                // if the snapshot is empty we are displaying a toast message.
                                Toast.makeText(StudentList.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(INVISIBLE);
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // we are displaying a toast message
                            // when we get any error from Firebase.
                            Toast.makeText(StudentList.this, "Failed to load data..", Toast.LENGTH_SHORT).show();
                        }
                    });




        }


    }

}