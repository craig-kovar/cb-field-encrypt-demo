package com.cb.demo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cb.demo.model.Entity;
import com.cb.demo.utils.JacksonConverter;
import com.cb.demo.utils.JsonConverter;
import com.cb.demo.utils.PropertyManager;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.encryption.AES256CryptoProvider;
import com.couchbase.client.encryption.CryptoManager;
import com.couchbase.client.encryption.JceksKeyStoreProvider;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.transcoder.JsonTranscoder;





public class CbRepository {
	
	private static Logger logger = LoggerFactory.getLogger(CbRepository.class);
	
	private final JsonConverter converter = new JacksonConverter();
	private final JsonTranscoder transcoder = new JsonTranscoder();
	
	private static Cluster cluster;
	private static Bucket bucket;
	
	public CbRepository(PropertyManager pm) {
		try {
			
			JceksKeyStoreProvider kp = new JceksKeyStoreProvider("secret");
			kp.storeKey("SecretKey", "aabbccddqqnnmmeerryybbff3322kk99".getBytes());
			kp.storeKey("HMACsecret", "myauthsecret".getBytes());
			kp.publicKeyName("secretkey");
			kp.signingKeyName("hmacsecret");
			AES256CryptoProvider aes256CryptoProvider = new AES256CryptoProvider(kp);
			CryptoManager cryptomanager = new CryptoManager();
			cryptomanager.registerProvider("AES", aes256CryptoProvider);
			
			
			CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().cryptoManager(cryptomanager).build();
			env.eventBus().get().subscribe(CbRepository::logEvent);
			cluster = CouchbaseCluster.create(env, pm.getValWithDefault("node", "localhost"));
			cluster.authenticate(pm.getVal("user"), pm.getVal("password"));
			bucket = cluster.openBucket(pm.getValWithDefault("bucket", "default"));
			
			logger.info("Connected to " + bucket.name());
			
		} catch (CouchbaseException cb) {
			System.out.println("Failed to connect to cluster");
			cb.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void logEvent(CouchbaseEvent e) {
		logger.info("EVENT : " + e);
	}
	
	public void upSert(JsonDocument doc) {
		JsonDocument docOut = null;
		try {
			docOut = bucket.upsert(doc);
			
			//bucket.repository().upsert(document)
			
		} catch (CouchbaseException e) {
			logger.error(e.getMessage());;
			e.printStackTrace();
		}
	}
	
	public void upSert(EntityDocument<?> doc) {

		try {
			bucket.repository().upsert(doc);
		} catch (CouchbaseException e) {
			logger.error(e.getMessage());;
			e.printStackTrace();
		}
	}
	
	public JsonDocument get(String  key) {
		JsonDocument docOut = null;
		try {
			docOut = bucket.get(key);
		} catch (CouchbaseException e) {
			logger.error(e.getMessage());;
			e.printStackTrace();
		}
		
		return docOut;
	}
	
	public <T extends Entity> T fromJsonDocument(JsonDocument doc, Class<T> type) {
		if (doc == null) {
			throw new IllegalArgumentException("document is null");
		}
		JsonObject content = doc.content();
		if (content == null) {
			throw new IllegalStateException("document has no content");
		}
		if (type == null) {
			throw new IllegalArgumentException("type is null");
		}
		T result = converter.fromJson(content.toString(), type);
		
		return result;
	}

	
	public <T extends Entity> JsonDocument toJsonDocument(T source) {
		JsonDocument doc = null;
		if (source == null) {
			throw new IllegalArgumentException("entity is null");
		}
		String id = source.get_id();
		if (id == null) {
			throw new IllegalStateException("entity ID is null");
		}
		try {
			JsonObject content = 
				transcoder.stringToJsonObject(converter.toJson(source));
			 doc = JsonDocument.create(id, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public <T extends Entity> JsonDocument toJsonDocument(String id, JsonObject content) {
		JsonDocument doc = null;
		if (content == null) {
			throw new IllegalArgumentException("entity is null");
		}
	
		if (id == null) {
			throw new IllegalStateException("entity ID is null");
		}
		
		try {
			 doc = JsonDocument.create(id, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public <T> JsonObject toJsonObject(T source) {
		JsonObject content = null;
		if (source == null) {
			throw new IllegalArgumentException("entity is null");
		}
		try {
			content = transcoder.stringToJsonObject(converter.toJson(source));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return content;
	}

}
