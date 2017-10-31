package edu.troy.cs;

import edu.troy.cs.connector.DBConnector;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class StudentsGenerator {

    private static String[] male_names = {"Zack", "Troy", "Bikash", "Kavarus", "Matt", "Justin", "Will", "Trey", "Zach", "Chase", "Michael", "Daniel", "Jacob", "Tyler", "Cooper", "Erik", "Julius", "Donald", "Kobe", "Stephen", "Andrew", "Kyrie", "Marc", "Pedro", "Leandro", "Luke", "Devin", "Kevin", "George", "Abraham", "Anthony", "Chris", "Russell"};
    private static String[] female_names = {"Elizabeth", "Emma", "Marissa", "Olivia", "Hannah", "Heather", "Emily", "Lily", "Victoria", "Addie", "Savannah", "Holly", "Morgan", "Megan", "Ashley", "Molly", "Jennifer", "Natalie", "Brittany", "Caroline", "MacKenzie", "Jena", "Hayley", "Maria", "Alicia", "Nancy", "Matea", "Tina", "Diana", "Courtney"};
    private static String[] surnames = {"Harrell", "Moore", "Knopps", "Erskine", "Goodson", "Wisenhunt", "Brock", "Bradley", "Trimble", "Kane", "Cole", "Trusty", "Zucher", "Plump", "Phillips", "Dubos", "Pearce", "Robinson", "Ledbetter", "Robinson", "Rife", "Monsees", "Loklier", "Kaye", "Hilton", "Newman", "Heath", "Ainsworth", "Woods", "Brown", "Jacob", "Williams", "Mac", "Lewis", "Sandfler", "Boggus", "Goodson", "Whigham", "Jernigan", "Henson", "Walker", "Morris", "Potter", "Weasly", "Pearce", "Tomlin", "Mills", "Hoff"};

    public static void generator() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = DBConnector.getConnection();
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
            String insertQuery = "INSERT INTO student (id_student, name, surname, gpa, age, gender, year, athletic, scholarship) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = dbConnection.prepareStatement(insertQuery);
            pst.setInt(1, id_student);
            pst.setString(3, surnames[ThreadLocalRandom.current().nextInt(0, surnames.length)]);
            int age = ThreadLocalRandom.current().nextInt(18, 30);
            pst.setInt(5, age);
            if (age != 18) {
                pst.setBigDecimal(4, new BigDecimal((double) ThreadLocalRandom.current().nextInt(200, 401) / 100));
                pst.setString(7, String.valueOf(Year.values()[ThreadLocalRandom.current().nextInt(0, Year.values().length)]));
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
                pst.setString(2, male_names[ThreadLocalRandom.current().nextInt(0, male_names.length)]);
            } else {
                pst.setString(6, String.valueOf('F'));
                pst.setString(2, female_names[ThreadLocalRandom.current().nextInt(0, female_names.length)]);
            }
            pst.execute();
        }
    }

    public static void main(String[] args) {
        try {
            generator();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
