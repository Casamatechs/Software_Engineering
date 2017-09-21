package edu.troy.cs;

public class Student {

    private int id;
    private String name;
    private String surname;
    private char gender;
    private int age;
    private Year year;
    private boolean athletic;
    private boolean fullScholarship;

    public Student(int id, String name, String surname, char gender, int age, Year year, boolean athletic, boolean fullScholarship) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.year = year;
        this.athletic = athletic;
        this.fullScholarship = fullScholarship;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public Year getYear() {
        return year;
    }

    public boolean isAthletic() {
        return athletic;
    }

    public boolean isFullScholarship() {
        return fullScholarship;
    }
}
