package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PowerCut {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/electriproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPowerCut(String PowerCutProvince, String PowerCutCity, String PowerCutDate, String PowerCutTime) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into powercutm(`PowerCutID`,`PowerCutProvince`,`PowerCutCity`,`PowerCutDate`,`PowerCutTime`)" 
			+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PowerCutProvince);
			preparedStmt.setString(3, PowerCutCity);
			preparedStmt.setString(4, PowerCutDate);
			preparedStmt.setString(5, PowerCutTime);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the power cut time.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPowerCut() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>ID</th><th>Province</th><th>City</th><th>Date</th><th>Time</th></tr>";
			String query = "select * from powercutm";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String PowerCutID = Integer.toString(rs.getInt("PowerCutID"));
				String PowerCutProvince = rs.getString("PowerCutProvince");
				String PowerCutCity = rs.getString("PowerCutCity");
				String PowerCutDate = rs.getString("PowerCutDate");
				String PowerCutTime = rs.getString("PowerCutTime");

				output += "<tr><td>" + PowerCutID + "</td>";
				output += "<td>" + PowerCutProvince + "</td>";
				output += "<td>" + PowerCutCity + "</td>";
				output += "<td>" + PowerCutDate + "</td>";
				output += "<td>" + PowerCutTime + "</td>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the power cut time.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePowerCut(String PowerCutID, String PowerCutProvince, String PowerCutCity, String PowerCutDate, String PowerCutTime) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE powercutm SET PowerCutProvince=?,PowerCutCity=?,PowerCutDate=?,PowerCutTime=? WHERE PowerCutID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, PowerCutProvince);
			preparedStmt.setString(2, PowerCutCity);
			preparedStmt.setString(3, PowerCutDate);
			preparedStmt.setString(4, PowerCutTime);
			preparedStmt.setInt(5, Integer.parseInt(PowerCutID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the power cut time.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePowerCut(String PowerCutID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from powercutm where PowerCutID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PowerCutID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the power cut time.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
