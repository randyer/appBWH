package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.DBConnection;
import edu.wpi.teamc.dao.IDao;
import edu.wpi.teamc.dao.users.PatientUser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MealRequestDAO implements IDao<MealRequest, Integer> {
  public List<MealRequest> fetchAllObjects() {
    List<MealRequest> returnList = new ArrayList<>();
    DBConnection db = new DBConnection(CApp.getWpiDB());
    try {
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"mealRequest\"";
      // Query
      String query = "SELECT * FROM " + table;
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String mealType = rs.getString("meal");
        String roomName = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String deliveryTime = rs.getString("ETA");
        String status = rs.getString("status");
        String assignedto = rs.getString("assignedto");
        MealRequest request =
            new MealRequest(
                requestID,
                new PatientUser(requester),
                roomName,
                additionalNotes,
                new Meal(mealType, ""));
        request.setEta(deliveryTime);
        request.setStatus(STATUS.valueOf(status));
        request.setAssignedto(assignedto);

        returnList.add(request);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnList;
  }

  public MealRequest addRow(MealRequest orm) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query =
          "INSERT INTO \"ServiceRequests\".\"mealRequest\" (Requester, meal, additionalNotes, ETA, roomName, status, assignedto) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement ps =
          db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, orm.getRequester().toString());
      ps.setString(2, orm.getMeal().toString());
      ps.setString(3, orm.getAdditionalNotes());
      ps.setString(4, orm.getEta());
      ps.setString(5, orm.getRoomName());
      ps.setString(6, orm.getStatus().toString());
      ps.setString(7, orm.getAssignedto());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int requestID = rs.getInt("requestID");
      orm.requestID = (requestID);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  public MealRequest updateRow(MealRequest orm, MealRequest orm2) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    MealRequest request = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query =
          "UPDATE \"ServiceRequests\".\"mealRequest\" SET requestid = ?, requester = ?, status = ?, additionalnotes = ?, meal = ?, eta= ?, roomname = ?, assignedto = ? WHERE requestid = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, orm2.getRequestID());
      ps.setString(2, orm2.getRequester().toString());
      ps.setString(3, orm2.getStatus().toString());
      ps.setString(4, orm2.getAdditionalNotes());
      ps.setString(5, orm2.getMeal().toString());
      ps.setString(6, orm2.getEta());
      ps.setString(7, orm2.getRoomName());
      ps.setString(8, orm2.getAssignedto());
      ps.setInt(9, orm.getRequestID());
      ps.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm2;
  }

  public MealRequest deleteRow(MealRequest orm) {
    DBConnection db = new DBConnection(CApp.getWpiDB());
    MealRequest request = null;
    try {
      Statement stmtNode = db.getConnection().createStatement();
      String query = "DELETE FROM \"ServiceRequests\".\"mealRequest\" WHERE requestid = ?";
      PreparedStatement ps = db.getConnection().prepareStatement(query);
      ps.setInt(1, orm.getRequestID());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.closeConnection();
    return orm;
  }

  @Override
  public MealRequest fetchObject(Integer key) {
    MealRequest request = null;
    try {
      DBConnection db = new DBConnection(CApp.getWpiDB());
      Statement stmt = db.getConnection().createStatement();
      // Table Name
      String table = "\"ServiceRequests\".\"mealRequest\"";
      // Query
      String query = "SELECT * FROM " + table + " WHERE requestid = " + key;
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        // Get all the data from the table
        int requestID = rs.getInt("requestID");
        String requester = rs.getString("Requester");
        String mealType = rs.getString("meal");
        String roomName = rs.getString("roomName");
        String additionalNotes = rs.getString("additionalNotes");
        String deliveryTime = rs.getString("ETA");
        request =
            new MealRequest(
                requestID,
                new PatientUser(requester),
                roomName,
                additionalNotes,
                new Meal(mealType, ""));
        request.setEta(deliveryTime);
        request.setStatus(STATUS.valueOf(rs.getString("status")));
        request.setAssignedto(rs.getString("assignedto"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return request;
  }

  public boolean exportCSV(String CSVfilepath) throws IOException {
    createFile(CSVfilepath);
    BufferedWriter writer = new BufferedWriter(new FileWriter(CSVfilepath));
    // Write the header row to the CSV file
    writer.write("requestid,requester,status,additionalnotes,meal,eta,roomname,assignedto\n");
    for (MealRequest mealRequest : fetchAllObjects()) {
      writer.write(
          mealRequest.getRequestID()
              + ","
              + mealRequest.getRequester()
              + ","
              + mealRequest.getStatus()
              + ","
              + mealRequest.getAdditionalNotes()
              + ","
              + mealRequest.getMeal()
              + ","
              + mealRequest.getEta()
              + ","
              + mealRequest.getRoomName()
              + ","
              + mealRequest.getAssignedto()
              + "\n");
    }
    writer.close();
    return true;
  }

  static void createFile(String fileName) throws IOException {
    File file = new File(fileName);
    if (file.createNewFile()) {
      System.out.println("File created: " + file.getName());
    } else {
      System.out.println("File already exists.");
    }
  }
}
