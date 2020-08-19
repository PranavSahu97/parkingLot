package com.parking.java;

//Space inherits from vehicle, so anything that has space also has to implement all methods inside vehicle as well
public interface Space extends Vehicle {
    boolean getIsTaken();
    void setIsTaken(boolean isTaken);

}
