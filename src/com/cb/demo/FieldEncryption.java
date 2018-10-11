package com.cb.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cb.demo.dao.CbRepository;
import com.cb.demo.model.Address;
import com.cb.demo.model.EncryptedCC;
import com.cb.demo.model.EncryptedProfile;
import com.cb.demo.model.UnEncryptedProfile;
import com.cb.demo.model.UnencryptedCC;
import com.cb.demo.utils.PropertyManager;
import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class FieldEncryption {

	private static Logger logger = LoggerFactory.getLogger(FieldEncryption.class);
	
	public static void main(String[] args) {
		
		PropertyManager pm = new PropertyManager("/cb.properties");
		CbRepository cbRepo = new CbRepository(pm);
		
		//Build Example Document
		UnEncryptedProfile prof = new UnEncryptedProfile();
		prof.set_id("FLE_EXAMPLE");
		prof.set_version("1.0.0");
		prof.setFirstName("DEMO");
		prof.setLastName("FLE");
		JsonObject jo = cbRepo.toJsonObject(prof);
		
		//One example CC
		UnencryptedCC cc = new UnencryptedCC();
		cc.setCcHolderName("Example Name");
		cc.setCcExpDt("10/18");
		cc.setCvcCode("111");
		cc.setCcNumber("1111 2222 3333 4444");
		jo.putAndEncrypt("cc", cbRepo.toJsonObject(cc) , "AES");
		
		//Address
		Address addr = new Address();
		addr.setStline1("123 Main Street");
		addr.setState("IL");
		addr.setCity("Chicago");
		JsonObject joAddr = cbRepo.toJsonObject(addr);
		joAddr.putAndEncrypt("zipcode", "60606", "AES");
		jo.put("address", joAddr);
		
		//Hobbies as an Array
		JsonArray ja = JsonArray.from("Reading","Video Games","Hockey","Swimming");
		jo.putAndEncrypt("hobbies", ja, "AES");
		

		//Perfom Upsert / Get / and Get/Decrypt
		logger.info("Printing original JsonObject\n\n" + prettyPrint(jo.toString()) + "\n");
		
		logger.info("Performing upsert.... ");
		
		cbRepo.upSert(cbRepo.toJsonDocument("FLE_EXAMPLE", jo));
		
		logger.info("Performing get");
		
		JsonDocument results = cbRepo.get("FLE_EXAMPLE");
		
		
		logger.info("Retrieved Document as \n\n" + prettyPrint(results.content().toString()) + "\n");
		
		try {
			logger.info("Decoding Document \n\n " + prettyPrint(results.content().toDecryptedString("AES")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static String prettyPrint(String source) {
		String retVal = null;
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		retVal =  gson.toJson(jp.parse(source));
		
		
		return retVal;
	}

	private static Logger LoggerFactory(Class<FieldEncryption> class1) {
		// TODO Auto-generated method stub
		return null;
	}

}
