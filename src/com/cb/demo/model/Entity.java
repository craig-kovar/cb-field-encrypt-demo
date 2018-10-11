package com.cb.demo.model;

import com.couchbase.client.java.repository.annotation.Id;

public abstract class Entity {

	@Id
	private String _id;
	private String _version;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get_version() {
		return _version;
	}
	public void set_version(String _version) {
		this._version = _version;
	}
	
	
	
}
