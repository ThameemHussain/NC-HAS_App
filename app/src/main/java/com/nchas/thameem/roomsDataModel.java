package com.nchas.thameem;

public class roomsDataModel {
    int roomNumber;
    int totalCapacity;
    int avaiableSeats;

    public roomsDataModel(int roomNumber, int totalCapacity, int availableSeats) {
        this.roomNumber = roomNumber;
        this.totalCapacity = totalCapacity;
        this.avaiableSeats = availableSeats;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getAvaiableSeats() {
        return avaiableSeats;
    }
}
