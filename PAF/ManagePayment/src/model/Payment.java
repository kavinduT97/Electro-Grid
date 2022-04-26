package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

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

	public String insertPayment(String Payment_Account, String Customer_Name, String Payment_Date, String Payment_Amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into paymentm(`Payment_ID`,`Payment_Account`,`Customer_Name`,`Payment_Date`,`Payment_Amount`)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Payment_Account);
			preparedStmt.setString(3, Customer_Name);
			preparedStmt.setString(4, Payment_Date);
			preparedStmt.setString(5, Payment_Amount);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>ID</th><th>Electricity Account No</th><th>Customer Name</th><th>Date</th><th>Amount</th></tr>";
			String query = "select * from paymentm";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String Payment_ID = Integer.toString(rs.getInt("Payment_ID"));
				String Payment_Account = rs.getString("Payment_Account");
				String Customer_Name = rs.getString("Customer_Name");
				String Payment_Date = rs.getString("Payment_Date");
				String Payment_Amount = rs.getString("Payment_Amount");

				output += "<tr><td>" + Payment_ID + "</td>";
				output += "<td>" + Payment_Account + "</td>";
				output += "<td>" + Customer_Name + "</td>";
				output += "<td>" + Payment_Date + "</td>";
				output += "<td>" + Payment_Amount + "</td>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String Payment_ID, String Payment_Account, String Customer_Name, String Payment_Date, String Payment_Amount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE paymentm SET Payment_Account=?,Customer_Name=?,Payment_Date=?,Payment_Amount=? WHERE Payment_ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, Payment_Account);
			preparedStmt.setString(2, Customer_Name);
			preparedStmt.setString(3, Payment_Date);
			preparedStmt.setString(4, Payment_Amount);
			preparedStmt.setInt(5, Integer.parseInt(Payment_ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayment(String Payment_ID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from paymentm where Payment_ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(Payment_ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
