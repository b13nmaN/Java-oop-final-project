package com.uwi.ilenius.p1;
import com.uwi.ilenius.p1.Light;


public class TrafficLight {
    private Integer id;
    private Light colour;
    private Segment segment;

    public TrafficLight(Integer id, Light colour) {
        this.id = id;
        this.colour = colour;
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

