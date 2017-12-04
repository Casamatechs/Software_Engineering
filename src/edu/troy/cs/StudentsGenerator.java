package edu.troy.cs;

import edu.troy.cs.connector.DBConnector;
import edu.troy.cs.exceptions.StudentConnectionException;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StudentsGenerator {

    private static String[] male_names = {"Zack", "Troy", "Bikash", "Kavarus", "Matt", "Justin", "Will", "Trey", "Zach", "Chase", "Michael", "Daniel", "Jacob", "Tyler", "Cooper", "Erik", "Julius", "Donald", "Kobe", "Stephen", "Andrew", "Kyrie", "Marc", "Pedro", "Leandro", "Luke", "Devin", "Kevin", "George", "Abraham", "Anthony", "Chris", "Russell"};
    private static String[] female_names = {"Elizabeth", "Emma", "Marissa", "Olivia", "Hannah", "Heather", "Emily", "Lily", "Victoria", "Addie", "Savannah", "Holly", "Morgan", "Megan", "Ashley", "Molly", "Jennifer", "Natalie", "Brittany", "Caroline", "MacKenzie", "Jena", "Hayley", "Maria", "Alicia", "Nancy", "Matea", "Tina", "Diana", "Courtney"};
    private static String[] surnames = {"Harrell", "Moore", "Knopps", "Erskine", "Goodson", "Wisenhunt", "Brock", "Bradley", "Trimble", "Kane", "Cole", "Trusty", "Zucher", "Plump", "Phillips", "Dubos", "Pearce", "Robinson", "Ledbetter", "Robinson", "Rife", "Monsees", "Loklier", "Kaye", "Hilton", "Newman", "Heath", "Ainsworth", "Woods", "Brown", "Jacob", "Williams", "Mac", "Lewis", "Sandfler", "Boggus", "Goodson", "Whigham", "Jernigan", "Henson", "Walker", "Morris", "Potter", "Weasly", "Pearce", "Tomlin", "Mills", "Hoff"};

    public static void generator() throws SQLException, IOException, ClassNotFoundException {
        Connection dbConnection = DBConnector.getConnection();
        String checkIDinDB = "SELECT * FROM student ORDER BY id_student DESC";
        Statement st = dbConnection.createStatement();
        ResultSet result = st.executeQuery(checkIDinDB);
        int id_student = -1;
        if (!result.first()) id_student = 1000000;
        else id_student = result.getInt(1);
        for (int i = 0; i < 500; i++) {
            int rand_gender = ThreadLocalRandom.current().nextInt(0, 2);
            id_student += ThreadLocalRandom.current().nextInt(1, 20);
            String insertQuery = "INSERT INTO student (id_student, name, surname, gpa, age, gender, year, athletic, scholarship, password, email) values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = dbConnection.prepareStatement(insertQuery);
            pst.setInt(1, id_student);
            pst.setString(11, String.valueOf(id_student).concat("@troy.edu"));
            pst.setString(3, surnames[ThreadLocalRandom.current().nextInt(0, surnames.length)]);
            int age = ThreadLocalRandom.current().nextInt(18, 30);
            pst.setInt(5, age);
            Year yr = Year.values()[ThreadLocalRandom.current().nextInt(0, Year.values().length)];
            if (age != 18 && !yr.equals(Year.FRESHMAN)) {
                pst.setBigDecimal(4, new BigDecimal((double) ThreadLocalRandom.current().nextInt(200, 401) / 100));
                pst.setString(7, String.valueOf(yr));
            } else {
                pst.setBigDecimal(4, new BigDecimal(0.00));
                pst.setString(7, String.valueOf(Year.FRESHMAN));
            }
            int boolAth = ThreadLocalRandom.current().nextInt(0, 50);
            if (boolAth == 34) pst.setBoolean(8, true);
            else pst.setBoolean(8, false);
            boolAth = ThreadLocalRandom.current().nextInt(0, 40);
            if (boolAth == 34) pst.setBoolean(9, true);
            else pst.setBoolean(9, false);
            if (rand_gender == 0) {
                pst.setString(6, String.valueOf('M'));
                String name = male_names[ThreadLocalRandom.current().nextInt(0, male_names.length)];
                pst.setString(2, name);
                pst.setInt(10, name.hashCode());
            } else {
                pst.setString(6, String.valueOf('F'));
                String name = female_names[ThreadLocalRandom.current().nextInt(0, female_names.length)];
                pst.setString(2, name);
                pst.setInt(10, name.hashCode());
            }
            pst.execute();
        }
    }

    public static void reservationsGenerator() throws SQLException, IOException, ClassNotFoundException, StudentConnectionException {
        Connection dbConnection = DBConnector.getConnection();
        String getBuildings = "SELECT id_building FROM building";
        Statement st = dbConnection.createStatement();
        ResultSet resultSet = st.executeQuery(getBuildings);
        String [] bldings = new String[12];
        int cnt = 0;
        while(resultSet.next()){
            bldings[cnt] = resultSet.getString(1);
            cnt++;
        }
        String getStudents = "SELECT * FROM student LIMIT 2000";
        ResultSet result = st.executeQuery(getStudents);
        while(result.next()){
            Student stnt = new Student(result.getInt(1), result.getString(2), result.getString(3), result.getString(6).charAt(0),
                    result.getInt(5), result.getBigDecimal(4), Year.valueOf(result.getString(7)), result.getBoolean(8), result.getBoolean(9), String.valueOf(result.getInt(10)), result.getString(11));
            int [] randomNmb = new Random().ints(0,12).distinct().limit(5).toArray();
            String [] selBuil = {bldings[randomNmb[0]], bldings[randomNmb[1]], bldings[randomNmb[2]], bldings[randomNmb[3]], bldings[randomNmb[4]]};
            stnt.doReservation(selBuil);
        }
    }

    public static Student getStudentWithID(String id){
        Student rt = null;
        try {
            Connection dbConnection = DBConnector.getConnection();
            String qr = "SELECT * FROM student WHERE id_student = ".concat(id);
            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery(qr);
            while(rs.next()){
                rt = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(6).charAt(0),
                        rs.getInt(5), rs.getBigDecimal(4), Year.valueOf(rs.getString(7)), rs.getBoolean(8), rs.getBoolean(9), String.valueOf(rs.getInt(10)), rs.getString(11));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StudentConnectionException e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static void main(String[] args) {
        try {
            reservationsGenerator();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (StudentConnectionException e) {
            e.printStackTrace();
        }
    }
}
