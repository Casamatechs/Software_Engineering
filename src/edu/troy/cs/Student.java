package edu.troy.cs;

import edu.troy.cs.connector.DBConnector;
import edu.troy.cs.exceptions.StudentConnectionException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class Student {

    private int id;
    private String name;
    private String surname;
    private char gender;
    private int age;
    private BigDecimal gpa;
    private Year year;
    private boolean athletic;
    private boolean fullScholarship;
    private String email;
    private int password;
    private Connection dbConnection;

    public Student(int id, String name, String surname, char gender, int age, BigDecimal gpa, Year year, boolean athletic, boolean fullScholarship, String password, String email) throws StudentConnectionException {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.gpa = gpa;
        this.year = year;
        this.athletic = athletic;
        this.fullScholarship = fullScholarship;
        this.email = email;
        this.password = password.hashCode();
        try {
            this.dbConnection = DBConnector.getConnection();
            String checkIDinDB = "SELECT * FROM student WHERE student.id_student = ".concat(String.valueOf(id));
            Statement st = dbConnection.createStatement();
            ResultSet result = st.executeQuery(checkIDinDB);
            if (!result.first()) {
                String insertQuery = "INSERT INTO student (id_student, name, surname, gpa, age, gender, year, athletic, scholarship, password, email) values (?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = dbConnection.prepareStatement(insertQuery);
                pst.setInt(1, id);
                pst.setString(2, name);
                pst.setString(3, surname);
                pst.setBigDecimal(4, gpa);
                pst.setInt(5, age);
                pst.setString(6, String.valueOf(gender));
                pst.setString(7, String.valueOf(year));
                pst.setBoolean(8, athletic);
                pst.setBoolean(9, fullScholarship);
                pst.setInt(10, this.password);
                pst.setString(11, email);
                pst.execute();
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new StudentConnectionException("The connection with the database failed");
        }
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

    public BigDecimal getGpa() {
        return gpa;
    }

    public String getEmail() {
        return email;
    }

    public boolean isFullScholarship() {
        return fullScholarship;
    }

    public void doReservation(String[] buildings) {
        String [] validB = buildingValid(buildings);
        String checkIDinDB = "SELECT * FROM reservation WHERE reservation.id_student = ".concat(String.valueOf(this.id));
        try {
            Statement st = dbConnection.createStatement();
            ResultSet result = st.executeQuery(checkIDinDB);
            if (!result.first()) {
                String insertQuery = "INSERT INTO reservation (id_student, building_1, building_2, building_3, building_4, building_5) values (?,?,?,?,?,?)";
                PreparedStatement pst = dbConnection.prepareStatement(insertQuery);
                pst.setInt(1,this.id);
                for(int i = 2; i < 7; i++){
                    if(i < validB.length+2) pst.setString(i,validB[i-2]);
                    else pst.setString(i,null);
                }
                pst.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReservation(String[] buildings) {
        String [] validB = buildingValid(buildings);
        String checkIDinDB = "SELECT * FROM reservation WHERE reservation.id_student = ".concat(String.valueOf(this.id));
        try {
            Statement st = dbConnection.createStatement();
            ResultSet result = st.executeQuery(checkIDinDB);
            if (result.first()) {
                String insertQuery = "UPDATE reservation SET building_1=?, building_2=?, building_3=?, building_4=?, building_5=? WHERE id_student=".concat(String.valueOf(this.id));
                PreparedStatement pst = dbConnection.prepareStatement(insertQuery);
                pst.setInt(1,this.id);
                for(int i = 1; i < 6; i++){
                    if(i < validB.length+1) pst.setString(i,validB[i-1]);
                    else pst.setString(i,null);
                }
                pst.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String [] buildingValid(String [] building){
        ArrayList<String> ret = new ArrayList<>();
        for (String b : building) {
            if ((b.equals("GAR") || b.equals("HIL")) && this.gender == 'F');
            else if ((b.equals("PAD") || b.equals("COW") || b.equals("HAM")) && this.gender == 'M');
            else ret.add(b);
        }
        return ret.toArray(new String[0]);
    }
}
