package edu.troy.cs;

import edu.troy.cs.connector.DBConnector;
import edu.troy.cs.exceptions.StudentConnectionException;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

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

    public void assignRooms() {
        //TODO
        try {
            Connection dbConnection = DBConnector.getConnection();
            String getReservations = "SELECT reservation.*, student.gender, student.puntuation FROM housing.student, housing.reservation WHERE student.id_student = reservation.id_student ORDER BY student.puntuation DESC";
            Statement st = dbConnection.createStatement();
            ResultSet resultReservationsQuery = st.executeQuery(getReservations);
            while (resultReservationsQuery.next()) {
                boolean assigned = false;
                int i = 2;
                while (!assigned && i <= 6) {
                    String bld = resultReservationsQuery.getString(i);
                    if(bld == null) break;
                    String getDorms = "SELECT * FROM housing.room as `rooms` LEFT JOIN (housing.assigments as `assigments`) ON (assigments.room = rooms.id_room) WHERE rooms.BUILDING = '".concat(bld).concat("'");
                    Statement st2 = dbConnection.createStatement();
                    ResultSet resultJoinQuery = st2.executeQuery(getDorms);
                    while (resultJoinQuery.next() && !assigned) {
                        if (resultJoinQuery.getString(5) == null) {
                            String insertNewRowAssigment = "INSERT INTO assigments (room, student1) values (?,?)";
                            PreparedStatement pst = dbConnection.prepareStatement(insertNewRowAssigment);
                            pst.setString(1, resultJoinQuery.getString(1));
                            pst.setInt(2, resultReservationsQuery.getInt(1));
                            pst.execute();
                            assigned = true;
                        } else {
                            //TODO Situación en la que ya hay alguien en la habitacion. Hay que comprobar capacidad y demás.
                            String genderCheck = "SELECT student.gender FROM housing.student WHERE student.id_student = " + resultJoinQuery.getInt(6);
                            Statement stGender = dbConnection.createStatement();
                            ResultSet rGender = stGender.executeQuery(genderCheck);
                            char genderSt1='N';
                            while(rGender.next()) genderSt1 = rGender.getString(1).charAt(0);
                            if (resultReservationsQuery.getString(7).charAt(0) != genderSt1) continue;
                            int capacityAv = resultJoinQuery.getInt(2) - 1;
                            if (capacityAv == 0) continue;
                            int j = 7;
                            while (!assigned && j <= 9 && capacityAv > 0) {
                                if (resultJoinQuery.getString(j) == null) {
                                    String room = resultJoinQuery.getString(1);
                                    String upAssig = "UPDATE assigments SET student"+(j-5)+" = ? WHERE assigments.room = '"+resultJoinQuery.getString(1)+"'";
                                    PreparedStatement upAss = dbConnection.prepareStatement(upAssig);
                                    upAss.setInt(1,resultReservationsQuery.getInt(1));
                                    upAss.execute();
                                    assigned = true;
                                } else {
                                    j++;
                                    capacityAv--;
                                }
                            }
                        }
                    }
                    i++;
                }
                if (assigned) {
                    /*String deleteReservation = "DELETE FROM reservation WHERE id_student = ?";
                    PreparedStatement deleteSt = dbConnection.prepareStatement(deleteReservation);
                    deleteSt.setInt(1, resultReservationsQuery.getInt(1));
                    deleteSt.execute();*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
