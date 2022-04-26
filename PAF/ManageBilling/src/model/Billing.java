package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Billing {

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

	
	public String insertBilling(String billCName, String billAccNO, String billDate, String billUnits, String billAmount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into billingm(`billID`,`billCName`,`billAccNO`,`billDate`,`billUnits`,`billAmount`)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, billCName);
			preparedStmt.setString(3, billAccNO);
			preparedStmt.setString(4, billDate);
			preparedStmt.setString(5, billUnits);
			preparedStmt.setString(6, billAmount);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readBilling() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Bill ID</th><th>Customer Name</th><th>Account No</th><th>Date</th><th>Use Units</th><th>Total Amount</th></tr>";
			String query = "select * from billingm";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String billID = Integer.toString(rs.getInt("billID"));
				String billCName = rs.getString("billCName");
				String billAccNO = rs.getString("billAccNO");
				String billDate = rs.getString("billDate");
				String billUnits = rs.getString("billUnits");
				String billAmount = rs.getString("billAmount");

				// Add into the html table
				output += "<tr><td>" + billID + "</td>";
				output += "<td>" + billCName + "</td>";
				output += "<td>" + billAccNO + "</td>";
				output += "<td>" + billDate + "</td>";
				output += "<td>" + billUnits + "</td>";
				output += "<td>" + billAmount + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateBilling(String billID, String billCName, String billAccNO, String billDate, String billUnits, String billAmount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE billingm SET billCName=?,billAccNO=?,billDate=?,billUnits=?,billAmount=?" + "WHERE billID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, billCName);
			preparedStmt.setString(2, billAccNO);
			preparedStmt.setString(3, billDate);
			preparedStmt.setString(4, billUnits);
			preparedStmt.setString(5, billAmount);
			preparedStmt.setInt(6, Integer.parseInt(billID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteBilling(String billID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from billingm where billID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(billID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the billing.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
