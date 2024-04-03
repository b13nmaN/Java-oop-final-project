package com.uwi.ilenius.p1;

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
