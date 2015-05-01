package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;

public class UserPost extends Post  {
	public String feeling;
	public String timeLine;
	public ArrayList<Post> postsList;
	
	
	private UserPost(UserPostBuilder builder) {
	        this.owner=builder.owner;
	       this.content = builder.content;
	        this.likes=builder.likes;
	        this.timeLine=builder.timeLine;
	        this.feeling=builder.feeling;
	        this.userPrivacy=builder.userPrivacy;
	   }
		// TODO Auto-generated constructor stub
	
	
	public static class UserPostBuilder {	
		 public String owner;
		public String content;
		public int likes;
		public String feeling;
		public Privacy userPrivacy;
		public String hashTag;
		public String timeLine="me";
		
			
		
		public UserPostBuilder(String content, String owner){
			this.content=content;
			this.owner=owner;
		}
		
		public  UserPostBuilder setLikes(int likes) {
			this.likes = likes;
			return this;
		}
		public UserPostBuilder setOwner(String owner) {
			this.owner = owner;
			return this;
		}
		public UserPostBuilder settimeLine(String timeLine) {
				this.timeLine = timeLine;
				return this;
			}
		
		public UserPostBuilder setContent(String content) {
			this.content = content;
			return this;
		}
	
	
	
		public UserPostBuilder setFeelig (String feeling) { 
			this.feeling = feeling;
			return this;
			}
		public UserPostBuilder setHashTag(String hashTag) {
			this.hashTag = hashTag;
			return this;
		}
		
		public UserPostBuilder setUserPrivacy(Privacy userPrivacy) {
			this. userPrivacy = userPrivacy;
			return this;
		}
		
		public UserPost Build(){
			
		
			return new UserPost(this);	
			
		}
	}	

////////////////////////
	public boolean savePost(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity post = new Entity("Posts", list.size() + 1);

		post.setProperty("content", this.content);
		post.setProperty("owner", this.owner);
		post.setProperty("likes", this.likes);
		post.setProperty("timeLine", this.timeLine);
		post.setProperty("feeling", this.feeling);
		post.setProperty("hashTag",this.hashTag);
		post=this.userPrivacy.addPrivacy(post);
		
		datastore.put(post);
		return true;
	}
	
	public Map<Long,String>getPublicPosts(String timeLine) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filter1 = new FilterPredicate("timeLine",FilterOperator.EQUAL,timeLine);
		Filter filter2 = new FilterPredicate("privacy",FilterOperator.EQUAL,"public");
		Filter filter = CompositeFilterOperator.and(filter1,filter2);
		Query gaeQuery = new Query("Posts").setFilter(filter);//Select From p'osts where timem=dddsf && pprivacy=jfjd
		//till now unprepared query
		Map<Long,String> posts = new HashMap<Long,String>();
		PreparedQuery pq=datastore.prepare(gaeQuery);
		for(Entity entity : pq.asIterable()){
			posts.put(entity.getKey().getId(), entity.getProperty("content").toString());
		}
		return posts;
		
		
		

	}
	public Map<Long,String> getAllPosts(String timeLine){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filter = new FilterPredicate("timeLine",FilterOperator.EQUAL,timeLine);
		
		Query gaeQuery = new Query("Posts").setFilter(filter);//Select From p'osts where timem=dddsf && pprivacy=jfjd
		//till now unprepared query
		Map<Long,String> posts = new HashMap<Long,String>();
		PreparedQuery pq=datastore.prepare(gaeQuery);
		for(Entity entity : pq.asIterable()){
			posts.put(entity.getKey().getId(), entity.getProperty("content").toString());
		}
		return posts;
		
	}



}
