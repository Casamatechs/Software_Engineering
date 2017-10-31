package edu.troy.cs;

import edu.troy.cs.exceptions.StudentConnectionException;

import java.math.BigDecimal;

public class Main {

    public static void main (String[]args){
        try {
            Student trial_st = new Student(1532197, "Carlos", "Sanchez", 'M', 23, new BigDecimal(3.87), Year.SENIOR, false, false);
            Manager manager = new Manager();
            int carlos_punct = manager.getPuntuation(1532197);
            System.out.println(carlos_punct);
        } catch (StudentConnectionException e) {
            System.err.println("Failure during DB connection");
        }
    }
}