package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Customer;

@Path("/Customer")
public class CustomerAPIService {
	Customer CustomerObj = new Customer();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCustomer() {
		return CustomerObj.readCustomer();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(
			@FormParam("customerName") String Name,			
	 @FormParam("customerAddress") String Address,
	 @FormParam("customerNIC") String NIC,
	 @FormParam("customerEmail") String Email,
	 @FormParam("customerPNO") String Phone)
	{
	 String output = CustomerObj.insertCustomer(Name, Address, NIC, Email, Phone);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customerData)
	{
	//Convert the input string to a JSON object
	 JsonObject cObject = new JsonParser().parse(customerData).getAsJsonObject();
	//Read the values from the JSON object
	 String ID = cObject.get("cID").getAsString();
	 String Name = cObject.get("customerName").getAsString();
	 String Address = cObject.get("customerAddress").getAsString();
	 String NIC = cObject.get("customerNIC").getAsString();
	 String Email = cObject.get("customerEmail").getAsString();
	 String Phone = cObject.get("customerPNO").getAsString();
	 String output = CustomerObj.updateCustomer(ID, Name, Address, NIC, Email, Phone);
	return output;
	} 
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String customerData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(customerData, "", Parser.xmlParser());

	//Read the value from the element <ID>
	 String cID = doc.select("cID").text();
	 String output = CustomerObj.deleteCustomer(cID);
	return output;
	}
	
}
