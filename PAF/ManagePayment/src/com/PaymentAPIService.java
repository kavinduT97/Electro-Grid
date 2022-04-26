package com;

import model.Payment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payment")
public class PaymentAPIService {
	Payment PaymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment() {
		return PaymentObj.readPayment();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(
			@FormParam("Payment_Account") String Payment_Account,
			@FormParam("Customer_Name") String Customer_Name,
			@FormParam("Payment_Date") String Payment_Date,
			@FormParam("Payment_Amount") String Payment_Amount) {
		String output = PaymentObj.insertPayment(Payment_Account, Customer_Name, Payment_Date, Payment_Amount);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePayment(String paymentData) {
		// Convert the input string to a JSON object
		JsonObject PayObject = new JsonParser().parse(paymentData).getAsJsonObject();

		// Read the values from the JSON object
		String Payment_ID = PayObject.get("Payment_ID").getAsString();
		String Payment_Account = PayObject.get("Payment_Account").getAsString();
		String Customer_Name = PayObject.get("Customer_Name").getAsString();
		String Payment_Date = PayObject.get("Payment_Date").getAsString();
		String Payment_Amount = PayObject.get("Payment_Amount").getAsString();
		
		String output = PaymentObj.updatePayment(Payment_ID, Payment_Account, Customer_Name, Payment_Date, Payment_Amount);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element
		String Payment_ID = doc.select("Payment_ID").text();
		String output = PaymentObj.deletePayment(Payment_ID);
		return output;
	}
}
