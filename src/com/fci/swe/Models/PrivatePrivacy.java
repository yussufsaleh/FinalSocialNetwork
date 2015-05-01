package com.FCI.SWE.Models;

import com.google.appengine.api.datastore.Entity;

public class PrivatePrivacy implements Privacy{

	@Override
	public Entity addPrivacy(Entity entity) {
		entity.setProperty("privacy", "private");
		return entity;
	}

}
