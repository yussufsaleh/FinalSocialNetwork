package com.FCI.SWE.Models;

import com.google.appengine.api.datastore.Entity;

public class PublicPrivacy implements Privacy{

	@Override
	public Entity addPrivacy(Entity entity) {
		entity.setProperty("privacy", "public");
		return entity;
	}
	
}
