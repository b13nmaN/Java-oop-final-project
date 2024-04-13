package com.uwi.ilenius.p2.enums;

public enum Light {
    Red("Red"), 
    Green("Green"),
    ;

    private String description;
    
    Light(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
