package com.example.parkingapp2;

public class ParkingSpot {
    private boolean ParkingSpot1;
    private boolean ParkingSpot2;

    public ParkingSpot(boolean parkingSpot1, boolean parkingSpot2) {
        ParkingSpot1 = parkingSpot1;
        ParkingSpot2 = parkingSpot2;
    }

    public ParkingSpot() {
    }

    public boolean isParkingSpot1() {
        return ParkingSpot1;
    }

    public void setParkingSpot1(boolean parkingSpot1) {
        ParkingSpot1 = parkingSpot1;
    }

    public boolean isParkingSpot2() {
        return ParkingSpot2;
    }

    public void setParkingSpot2(boolean parkingSpot2) {
        ParkingSpot2 = parkingSpot2;
    }
}
