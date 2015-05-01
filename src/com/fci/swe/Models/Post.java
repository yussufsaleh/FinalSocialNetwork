package com.FCI.SWE.Models;

import java.util.List;
import java.util.Map;

import com.FCI.SWE.Models.UserPost.UserPostBuilder;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class Post {
	
	protected String owner;
	protected String content;
	protected int likes;
	protected String hashTag;
	//protected String timeLine;
	public Privacy userPrivacy;
	
	/*//da constructor 3ady
	 public Post(UserPostBuilder builder) {
     this.owner=builder.owner;
	       this.content = builder.content;
	        this.likes=builder.likes;
	        this.timeLine=builder.timeLine;
	        
	    }
	*/
	public String getContent() {
			return content;
		}

	public String getOwner() {
		return owner;
	}
	
	public int getLikes() {
		return likes;
	}
	public String getHashTag() {
	
		return hashTag;
	}
	/*public String getTimeLine() {
		
		return timeLine;
	}*/
	public abstract boolean savePost();
	
	public abstract  Map<Long,String> getAllPosts(String timeLine);

	public abstract Map<Long,String>getPublicPosts(String timeLine);
	

	
	

	
	
	
	

}
