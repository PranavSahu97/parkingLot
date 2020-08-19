package com.parking.java;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkingL {
    //HashMap structure
    //(SMALL, (Reg, Handicap))
    //(MEDIUM, (Reg, Handicap))
    //(LARGE, (Reg, Handicap))
    private HashMap<Vehicle.VehicleSize, HashMap<Vehicle.VehicleType, ArrayList<ParkingSpace>>> parkingAvaliableBySize = new HashMap<>();

    final private Vehicle.VehicleType [] vehicleTypes = Vehicle.VehicleType.values();
    final private Vehicle.VehicleSize [] vehicleSizes = Vehicle.VehicleSize.values();

    public ParkingL(int numberOfSmallRegularSpaces,
                    int numberOfMediumRegularSpaces,
                    int numberOfLargeRegularSpaces,
                    int numberOfSmallHandicappedSpaces,
                    int numberOfMediumHandicappedSpaces,
                    int numberOfLargeHandicappedSpaces){

        int [][] numberOfSpaces = { {numberOfSmallRegularSpaces, numberOfSmallHandicappedSpaces},
                                    {numberOfMediumRegularSpaces, numberOfMediumHandicappedSpaces},
                                    {numberOfLargeRegularSpaces, numberOfLargeHandicappedSpaces} };

        //Initialize the parking lot
        for(int sizeIndex = 0; sizeIndex < vehicleSizes.length; sizeIndex++){
            for(int vehicleTypeIndex =0; vehicleTypeIndex < vehicleTypes.length; vehicleTypeIndex++){
                ArrayList<ParkingSpace> spaces = new ArrayList<>();
                for (int i=0; i<numberOfSpaces[sizeIndex][vehicleTypeIndex]; i++){
                    ParkingSpace p = new ParkingSpace(vehicleSizes[sizeIndex], vehicleTypes[vehicleTypeIndex]);
                    spaces.add(p);
                }
                if(parkingAvaliableBySize.containsKey(vehicleSizes[sizeIndex])){
                    HashMap<Vehicle.VehicleType, ArrayList<ParkingSpace>> previousTypeToSpaceMap = parkingAvaliableBySize.get(vehicleSizes[sizeIndex]);
                    previousTypeToSpaceMap.put(vehicleTypes[vehicleTypeIndex], spaces);
                } else{
                    HashMap<Vehicle.VehicleType, ArrayList<ParkingSpace>> typeToSpaceMap = new HashMap<>();
                    typeToSpaceMap.put(vehicleTypes[vehicleTypeIndex], spaces);
                    parkingAvaliableBySize.put(vehicleSizes[sizeIndex], typeToSpaceMap);
                }
            }
        }
        System.out.println("Init");
    }

    public ParkingL(HashMap<Vehicle.VehicleSize, HashMap<Vehicle.VehicleType, ArrayList<ParkingSpace>>> parkingAvaliableBySize){
        this.parkingAvaliableBySize = parkingAvaliableBySize;
    }

    public ParkingSpace park(Vehicle v){
        ParkingSpace attemptedPark = tryToPark(v);
        if(attemptedPark != null){
            attemptedPark.setIsTaken(true);
            System.out.println("The " +v.getClass().getSimpleName()+ " is parked in a " +attemptedPark.getSize() + " " + attemptedPark.getType() + " spot.");
        } else{
            System.out.println(v.getClass().getSimpleName() +" cannot be parked at this time.");
        }
        return attemptedPark;
    }

    private ParkingSpace tryToPark(Vehicle vehicle){
        Vehicle.VehicleSize size = vehicle.getSize();
        Vehicle.VehicleType type = vehicle.getType();

        //Large Parking Spots
        if(size.equals(Vehicle.VehicleSize.LARGE)){
            //If this is null , then no spaces are available
            return findAvaliableSpaceWithType(type, parkingAvaliableBySize.get(size));

        } else if(size.equals(Vehicle.VehicleSize.MEDIUM)){
            ParkingSpace mediumSpace = findAvaliableSpaceWithType(type, parkingAvaliableBySize.get(size));
            if(mediumSpace != null){
                return mediumSpace;
            } else{
                //Check if large spaces are full, if yes then there are no spaces avaliable
                return findAvaliableSpaceWithType(type, parkingAvaliableBySize.get(size));

            }
        } else if(size.equals(Vehicle.VehicleSize.SMALL)){
            ParkingSpace smallSpace = findAvaliableSpaceWithType(type, parkingAvaliableBySize.get(size));
            if(smallSpace != null){
                return smallSpace;
            } else {
                //check if medium spaces are full, if yes then no medium spaces avaliable
                ParkingSpace mediumSpace = findAvaliableSpaceWithType(type, parkingAvaliableBySize.get(Vehicle.VehicleSize.MEDIUM));
                if(mediumSpace != null){
                    return mediumSpace;
                } else{
                    return findAvaliableSpaceWithType(type, parkingAvaliableBySize.get(Vehicle.VehicleSize.LARGE));
                }
            }
        } else{
            return null;
        }
    }

    private ParkingSpace findAvaliableSpaceWithType(Vehicle.VehicleType vehicleType, HashMap<Vehicle.VehicleType, ArrayList<ParkingSpace>> parkingAvailableByType){
        if(vehicleType.equals(Vehicle.VehicleType.HANDICAPPED)){
            //Check handicapped spaces first
            ArrayList<ParkingSpace> handicappedSpaces = parkingAvailableByType.get(vehicleType);
            ParkingSpace handicappedSpace = areSpacesTaken(handicappedSpaces);
            if(handicappedSpace != null){
                return handicappedSpace;
            }
            else{
                //check regular spaces
                ArrayList<ParkingSpace> regularSpaces = parkingAvailableByType.get(Vehicle.VehicleType.REGULAR);
                return areSpacesTaken(regularSpaces);
            }
        } else{
            //check regular spaces
            ArrayList<ParkingSpace> regularSpaces = parkingAvailableByType.get(Vehicle.VehicleType.REGULAR);
            return areSpacesTaken(regularSpaces);
        }
    }

    private ParkingSpace areSpacesTaken(ArrayList<ParkingSpace> parkingSpaces){
        if (parkingSpaces == null){
            return null;
        }
        for(ParkingSpace space : parkingSpaces){
            if(!space.getIsTaken()){
                return space;
            }
        }
        return null;
    }

    public boolean getIsParkingLotFull(){
        for(int sizeIndex = 0; sizeIndex < vehicleSizes.length; sizeIndex++){
            for(int vehicleTypeIndex =0; vehicleTypeIndex < vehicleTypes.length; vehicleTypeIndex++){
                ParkingSpace space = findAvaliableSpaceWithType(vehicleTypes[vehicleTypeIndex], parkingAvaliableBySize.get(vehicleSizes[sizeIndex]));
                if(space == null){
                    return false;
                }
            }
        }
        //no null spots mean lot is full
        return true;
    }

    private int toStringHelper_AvaliableSpacesWithTypeAndSize(Vehicle.VehicleType vehicleType, Vehicle.VehicleSize vehicleSize){
        HashMap<Vehicle.VehicleType, ArrayList<ParkingSpace>> avaliableByTypeAndSize = parkingAvaliableBySize.get(vehicleSize);
        ArrayList<ParkingSpace> exactSpaces = avaliableByTypeAndSize.get(vehicleType);
        int count = 0;
        for(ParkingSpace space : exactSpaces){
            if(!space.getIsTaken()){
                count++;
            }
        }
        return count;
    }

    public String toStringSpotsLeft(){
        String description = "";
        for(int sizeIndex =0; sizeIndex < vehicleSizes.length; sizeIndex++){
            for(int vehicleTypeIndex =0; vehicleTypeIndex < vehicleTypes.length; vehicleTypeIndex++){
                int count = toStringHelper_AvaliableSpacesWithTypeAndSize(vehicleTypes[vehicleTypeIndex], vehicleSizes[sizeIndex]);
                description = description + "There are " + count + " " + vehicleSizes[sizeIndex] + " " + vehicleTypes[vehicleTypeIndex] + " spots left.\n";
            }
        }
        return description;
    }

}
