package edu.troy.cs;

import edu.troy.cs.exceptions.StudentConnectionException;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws StudentConnectionException {

        Manager manager = new Manager();
        Scanner scn = new Scanner(System.in);
        boolean exit = false;
        while(!exit) {
            System.out.println("What do you want to do?");
            System.out.println("[1] Calculate the puntuation for one student");
            System.out.println("[2] Calculate the puntuation for all the students");
            System.out.println("[3] Assign dorms");
            System.out.println("[4] Exit");
            int opt = scn.nextInt();
            switch (opt) {
                case 1:
                    System.out.println("Write the student ID");
                    int idS = scn.nextInt();
                    int punt = manager.getPuntuation(idS);
                    System.out.println("The student " + idS + " has a puntuation of " + punt + ".\n\n\n");
                    break;
                case 2:
                    manager.getPuntuationToAll();
                    System.out.println("The puntuation has been calculated for all the students.\n\n\n");
                    break;
                case 3:
                    manager.assignRooms();
                    System.out.println("Dorms assigned\n\n\n");
                    break;
                case 4:
                    exit = true;
                    break;
            }
        }
        System.exit(0);
    }
}
