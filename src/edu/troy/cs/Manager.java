package edu.troy.cs;

import edu.troy.cs.connector.DBConnector;
import edu.troy.cs.exceptions.StudentConnectionException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Manager {

    final private static int MAX_PUNTUATION = 500;

    public int getPuntuation(int student_id) throws StudentConnectionException {
        int ret = -1;
        try {
            Connection dbConnection = DBConnector.getConnection();
            String checkIDinDB = "SELECT * FROM student WHERE student.id_student = ".concat(String.valueOf(student_id));
            Statement st = dbConnection.createStatement();
            ResultSet result = st.executeQuery(checkIDinDB);
            if (!result.first()) {
                throw new Exception("Student doesn't found in the databse");
            } else {
                Student student = new Student(result.getInt(1), result.getString(2), result.getString(3), result.getString(6).charAt(0),
                        result.getInt(5), result.getBigDecimal(4), Year.valueOf(result.getString(7)), result.getBoolean(8), result.getBoolean(9), String.valueOf(result.getInt(10)), result.getString(11));
                if ((student.getAge() < 19 && !student.isAthletic()) || student.isFullScholarship())
                    ret = MAX_PUNTUATION;
                else if (student.getYear().equals(Year.FRESHMAN))
                    ret = new BigDecimal(4.00).multiply(new BigDecimal(100)).intValue() + (50 - student.getAge()) + (50 - ((1 + student.getYear().ordinal()) * 10));
                else
                    ret = student.getGpa().multiply(new BigDecimal(100)).intValue() + (50 - student.getAge()) + (50 - ((1 + student.getYear().ordinal()) * 10));
            }
        } catch (Exception e) {
            throw new StudentConnectionException("The connection with the database failed");
        }
        return ret;
    }

    public void getPuntuationToAll() throws StudentConnectionException {
        try {
            Connection dbConnection = DBConnector.getConnection();
            String checkIDinDB = "SELECT * FROM student WHERE puntuation IS NULL";
            Statement st = dbConnection.createStatement();
            ResultSet result = st.executeQuery(checkIDinDB);
            int ret;
            while (result.next()) {
                Student student = new Student(result.getInt(1), result.getString(2), result.getString(3), result.getString(6).charAt(0),
                        result.getInt(5), result.getBigDecimal(4), Year.valueOf(result.getString(7)), result.getBoolean(8), result.getBoolean(9), String.valueOf(result.getInt(10)), result.getString(11));
                if ((student.getAge() < 19 && !student.isAthletic()) || student.isFullScholarship())
                    ret = MAX_PUNTUATION;
                else if (student.getYear().equals(Year.FRESHMAN))
                    ret = new BigDecimal(4.00).multiply(new BigDecimal(100)).intValue() + (50 - student.getAge()) + (50 - ((1 + student.getYear().ordinal()) * 10));
                else
                    ret = student.getGpa().multiply(new BigDecimal(100)).intValue() + (50 - student.getAge()) + (50 - ((1 + student.getYear().ordinal()) * 10));
                String insertQuery = "UPDATE student SET puntuation = ? WHERE id_student = ?";
                PreparedStatement pst = dbConnection.prepareStatement(insertQuery);
                pst.setInt(1, ret);
                pst.setInt(2, result.getInt(1));
                pst.execute();
            }
        } catch (Exception e) {
            throw new StudentConnectionException("The connection with the database failed");
        }
    }

    public void assignRooms(){
        //TODO
    }
}
