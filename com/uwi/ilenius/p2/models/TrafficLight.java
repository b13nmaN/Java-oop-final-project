package com.uwi.ilenius.p2.models;
import com.uwi.ilenius.p2.enums.Light;
import com.uwi.ilenius.p2.interfaces.Verifiable;


public class TrafficLight implements Verifiable{
    private Integer id;
    private Light colour;

    public TrafficLight(Integer id, Light colour) {
        this.id = id;
        this.colour = colour;
    }

    public Light getColour() {
        return colour;
    }
    public Integer getId() {
        return id;
    }
    public void change() {
        if (colour == Light.Red) {
            colour = Light.Green;
        } else if (colour == Light.Green) {
            colour = Light.Red;
        }
    }

    public boolean verify() {
        return colour != null;
    }
    
}

