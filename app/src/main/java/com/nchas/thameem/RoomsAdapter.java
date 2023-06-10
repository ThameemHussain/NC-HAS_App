package com.nchas.thameem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.MyViewHolder> {
    Context context;
    ArrayList<roomsDataModel> roomsDataModels;
    public RoomsAdapter(Context context, ArrayList<roomsDataModel> roomsDataModels) {
        this.context=context;
        this.roomsDataModels=roomsDataModels;
    }

    @NonNull
    @Override
    public RoomsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate Layout here
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rooms_item,parent,false);
        return new RoomsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.MyViewHolder holder, int position) {
        //Bind - what data actually be showing OnScreen
        holder.roomNumber.setText(roomsDataModels.get(position).getRoomNumber());
        holder.Capacity.setText(roomsDataModels.get(position).getTotalCapacity());
        holder.Availability.setText(roomsDataModels.get(position).getAvaiableSeats());
    }

    @Override
    public int getItemCount() {
        //Total number of items we want to display
        return 1;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView roomNumber,Capacity,Availability;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNumber= itemView.findViewById(R.id.roomNo_roomsItem);
            Capacity= itemView.findViewById(R.id.roomCapacity_roomsItem);
            Availability= itemView.findViewById(R.id.roomAvailibility_roomsItem);
        }
    }
}
