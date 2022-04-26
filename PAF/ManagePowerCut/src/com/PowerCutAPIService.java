package com;

import model.PowerCut;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/PowerCut")
public class PowerCutAPIService {
	PowerCut PowerCutObj = new PowerCut();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPowerCut() {
		return PowerCutObj.readPowerCut();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPowerCut(@FormParam("PowerCutProvince") String PowerCutProvince,
			@FormParam("PowerCutCity") String PowerCutCity,
			@FormParam("PowerCutDate") String PowerCutDate,
			@FormParam("PowerCutTime") String PowerCutTime) {
		String output = PowerCutObj.insertPowerCut(PowerCutProvince, PowerCutCity, PowerCutDate, PowerCutTime);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePowerCut(String powercutData) {
		// Convert the input string to a JSON object
		JsonObject PowerObject = new JsonParser().parse(powercutData).getAsJsonObject();

		// Read the values from the JSON object
		String PowerCutID = PowerObject.get("PowerCutID").getAsString();
		String PowerCutProvince = PowerObject.get("PowerCutProvince").getAsString();
		String PowerCutCity = PowerObject.get("PowerCutCity").getAsString();
		String PowerCutDate = PowerObject.get("PowerCutDate").getAsString();
		String PowerCutTime = PowerObject.get("PowerCutTime").getAsString();	
		String output = PowerCutObj.updatePowerCut(PowerCutID, PowerCutProvince, PowerCutCity, PowerCutDate, PowerCutTime);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePowerCut(String powercutData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(powercutData, "", Parser.xmlParser());

		// Read the value from the element
		String PowerCutID = doc.select("PowerCutID").text();
		String output = PowerCutObj.deletePowerCut(PowerCutID);
		return output;
	}
}
