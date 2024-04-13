package com.uwi.ilenius.p2;

import com.uwi.ilenius.p2.enums.RSStatus;

public class test {

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println(test_());
    }
    public static RSStatus test_ (){
        RSStatus status = RSStatus.Open;
        
        return status ;
    }
}

