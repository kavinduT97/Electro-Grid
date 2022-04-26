package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Billing;

@Path("/Billing")
public class BillingAPIService {
	Billing BillObj = new Billing();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBilling() {
		return BillObj.readBilling();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBilling(@FormParam("billCName") String billCName,		
	 @FormParam("billAccNO") String billAccNO,
	 @FormParam("billDate") String billDate,
	 @FormParam("billUnits") String billUnits,
	 @FormParam("billAmount") String billAmount)
	{
	 String output = BillObj.insertBilling(billCName, billAccNO, billDate, billUnits, billAmount);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBilling(String billingData)
	{
	//Convert the input string to a JSON object
	 JsonObject billObject = new JsonParser().parse(billingData).getAsJsonObject();
	//Read the values from the JSON object
	 String billID = billObject.get("billID").getAsString();
	 String billCName = billObject.get("billCName").getAsString();
	 String billAccNO = billObject.get("billAccNO").getAsString();
	 String billDate = billObject.get("billDate").getAsString();
	 String billUnits = billObject.get("billUnits").getAsString();
	 String billAmount = billObject.get("billAmount").getAsString();
	 String output = BillObj.updateBilling(billID, billCName, billAccNO, billDate, billUnits, billAmount);
	return output;
	} 
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBilling(String billingData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(billingData, "", Parser.xmlParser());

	//Read the value from the element <ID>
	 String billID = doc.select("billID").text();
	 String output = BillObj.deleteBilling(billID);
	return output;
	}
}
