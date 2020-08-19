package com.parking.java;

public class Main {
    public static void main(String[] args) {
        int numberOfSmallRegularSpaces = 9;
        int numberOfMediumRegularSpaces = 24;
        int numberOfLargeRegularSpaces = 10;
        int numberOfHandicapMediumSpaces = 5;

        ParkingL parkingLot = new ParkingL(9,24,10,0,5,0);

        System.out.println("Parking Medium Handicapped Cars");
        for(int i=0; i<6; i++){
            parkingLot.park(new Car(Vehicle.VehicleType.HANDICAPPED));
        }
        System.out.println();

        System.out.println("Parking Large Regular trucks");
        for(int j=0; j<8; j++){
            parkingLot.park(new Truck(Vehicle.VehicleType.REGULAR));
        }
        System.out.println();

        System.out.println("Parking Medium Regular Cars");
        for(int k=0; k<3; k++){
            parkingLot.park(new Car(Vehicle.VehicleType.REGULAR));
        }
        System.out.println();

        System.out.println("Parking Large Handicapped Truck");
        parkingLot.park(new Truck(Vehicle.VehicleType.HANDICAPPED));
        System.out.println();

        System.out.println("Parking Large Regular Truck");
        parkingLot.park(new Truck(Vehicle.VehicleType.REGULAR));
        System.out.println();

        System.out.println("Parking Large Handicapped Truck");
        parkingLot.park(new Truck(Vehicle.VehicleType.HANDICAPPED));
        System.out.println();

        System.out.println("Parking Regular bikes");
        for(int l = 0; l<3; l++){
            parkingLot.park(new Bike(Vehicle.VehicleType.REGULAR));
        }
        System.out.println();

        System.out.println("Parking Regular Car");
        parkingLot.park(new Car(Vehicle.VehicleType.REGULAR));
        System.out.println();

        System.out.println("Is the parking lot full ? " + parkingLot.getIsParkingLotFull());
        System.out.println();
        System.out.println("What spots are left?\n" + parkingLot.toStringSpotsLeft());
    }
}
