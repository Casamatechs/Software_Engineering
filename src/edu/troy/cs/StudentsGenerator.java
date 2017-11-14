package edu.troy.cs;

import edu.troy.cs.connector.DBConnector;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;
public class StudentsGenerator {

    private static String[] male_names = {"Zack", "Troy", "Bikash", "Kavarus", "Matt", "Justin", "Will", "Trey", "Zach", "Chase", "Michael", "Daniel", "Jacob", "Tyler", "Cooper", "Erik", "Julius", "Donald", "Kobe", "Stephen", "Andrew", "Kyrie", "Marc", "Pedro", "Leandro", "Luke", "Devin", "Kevin", "George", "Abraham", "Anthony", "Chris", "Russell", "Joshus", "Matthew", "Christopher", "Ethan", "Joseph","William", "Nicholas", "David", "Alexander", "Ryan", "James", "John", "Jonathan", "Brandon", "Christian", "Dylan", "Zachary", "Noah", "Samuel", "Benjamin", "Nathan", "Logan", "Jose", "Gabriel", "Austin", "Caleb", "Robert", "Elijah", "Thomas", "Jordan", "Cameron", "Hunter", "Jack", "Angel", "Isaiah", "Jackson", "Evan", "Jason", "Isaac", "Mason", "Aaron", "Connor", "Gavin", "Kyle", "Jayden", "Aidan", "Juan", "Luis", "Charles", "Aiden", "Adam", "Brian", "Eric", "Lucas", "Sean", "Nathaniel", "Alex", "Adrian", "Carlos", "Bryan", "Ian", "Jesus", "Owen", "Julian", "Cole", "Landon", "Diego", "Steven", "Timothy", "Jeremiah", "Sebastian", "Xavier", "Cody", "Seth", "Hayden", "Blake", "Richard", "Carter", "Wyatt", "Dominic", "Antonio", "Jaden", "Miguel", "Brayden", "Patrick", "Alejandro", "Carson", "Jesse", "Tristan"};
    private static String[] female_names = {"Elizabeth", "Emma", "Marissa", "Olivia", "Hannah", "Heather", "Emily", "Lily", "Victoria", "Addie", "Savannah", "Holly", "Morgan", "Megan", "Ashley", "Molly", "Jennifer", "Natalie", "Brittany", "Caroline", "MacKenzie", "Jena", "Hayley", "Maria", "Alicia", "Nancy", "Matea", "Tina", "Diana", "Courtney", "April", "Madison Abigail Isabella Samantha”,  “Alexis", "Sarah", "Alyssa", "Grace", "Sophia", "Taylor", "Brianna", "Lauren", "Ava", "Kayla", "Jessica”, “Chloe Anna”, “Hailey", "Mia", "Sydney", "Jasmine”, “Julia", "Destiny", "Rachel", "Megan", "Kaitlyn", "Katherine", "Ella", "Alexandra", "Haley", "Allison", "Nicole”, “Brooke", "Makayla", "Kaylee", "Stephanie", "Andrea", "Faith", "Amanda", "Katelyn", "Kimberly", "Madeline", "Gabrielle", "Zoe", "Trinity", "Alexa", "Mary", "Jenna", "Lillian", "Paige", "Kylie", "Gabriella", "Rebecca", "Jordan", "Sara", "Addison", "Michelle", "Riley", "Vanessa", "Angelina", "Leah", "Sofia", "Audrey", "Maya", "Avery", "Evelyn","Autumn", "Amber", "Ariana", "Jocelyn", "Claire", "Jada", "Danielle", "Bailey", "Isabel", "Arianna", "Sierra", "Mariah", "Aaliyah", "Melanie", "Erin", "Nevaeh", "Brooklyn"};
    private static String[] surnames = {"Harrell", "Moore", "Knopps", "Erskine", "Goodson", "Wisenhunt", "Brock", "Bradley", "Trimble", "Kane", "Cole", "Trusty", "Zucher", "Plump", "Phillips", "Dubos", "Pearce", "Robinson", "Ledbetter", "Rife", "Monsees", "Loklier", "Kaye", "Hilton", "Newman", "Heath", "Ainsworth", "Woods", "Brown", "Jacob", "Williams", "Mac", "Lewis", "Sandfler", "Boggus", "Whigham", "Jernigan", "Henson", "Walker", "Morris", "Potter", "Weasly", "Tomlin", "Mills", "Hoff", "Smith", "Johnson", "Jones", "Davis", "Miller", "Wilson", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Clark", "Rodriguez", "Lee", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Barnes", "Ross", "Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander", "Russell", "Griffin"};
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
