package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.AcceptReqNotification;
import com.FCI.SWE.Models.Notification;
import com.FCI.SWE.Models.NotificationEntity;
import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.PrivatePrivacy;
import com.FCI.SWE.Models.PublicPrivacy;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Models.UserPost;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			String not=NotificationEntity.getNotification(user.getEmail());
			object.put("notification", not);
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	@POST
	@Path("/addFriendservice")
	public String addFriendservice(@FormParam("email") String email) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.loggedUser;
		UserEntity user2 = UserEntity.getUser(email);
		if (user == null) {
			object.put("Status", "Failed");
		} else {
			ArrayList<String>friends=user.friends;
			friends.add(email);
			user.friends=friends;
			user.saveFriends(user);
			friends=user2.friends;
			friends.add(user.getEmail());
			user2.saveFriends(user2);
			ArrayList<String>requests=user.requests;
			requests.remove(email);
			user.requests=requests;
			user.saveRequests(user);
			Notification n= new AcceptReqNotification(user2.getEmail(),user.getEmail());
			UserEntity.setNotfication(n);
			// invoker
			object.put("Status", "OK");
			
		}

		return object.toString();

	}
	
	@POST
	@Path("/sendRequestservice")
	public String sendRequestservice(@FormParam("email") String email) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(email);
		System.out.println("name="+user.getName());
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			ArrayList<String>requests=user.requests;
			requests.add(UserEntity.loggedUser.getEmail());
			user.requests=requests;
			user.saveRequests(user);
			object.put("Status", "OK");
		}

		return object.toString();

	}
	/*
	@POST
	@Path("/showlastNotification")
	public String showNotifservice() {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.loggedUser;
		System.out.println("name="+user.getName());
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			
			String not=NotificationEntity.getNotification(user.getEmail());
			
			object.put("notification", not);
			object.put("Status", "OK");
		}

		return object.toString();

	}
	*/
	
	//////////////////////////////////////////////////
	@POST
	@Path("/ConversationService")
	public String Group_ConversationService(@FormParam("Friend") String friend) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(friend);
		//System.out.println("Friend="+user.getName()); // friend
		if (user == null) {
		System.out.println("the user you search about in not exist ");
		object.put("Status", "Failed");
		} else {
			System.out.println("the user you search about is exist ");
		    ArrayList<String>messages=user.messages;
		    
		    System.out.println("my name is "+UserEntity.loggedUser.getName());
			messages.add(UserEntity.loggedUser.getName());
			System.out.println("here");
			user.messages=messages;
			user.saveMessages(user);
			System.out.println("before return ");
			object.put("Status", "OK");
		}

		return object.toString();
		

	}
	@POST
	@Path("/UserPublicPostService")
	public String PublicPostService(@FormParam("content") String  content,@FormParam("timeLine") String timeLine,
			                  @FormParam("feeling") String feeling,
			                  @FormParam("hashTag") String  hashTag) {
		JSONObject object = new JSONObject();
		String owner= UserEntity.loggedUser.getEmail();
		Post p= new UserPost.UserPostBuilder(content, owner)
        				.setFeelig(feeling).setHashTag(hashTag).setUserPrivacy(new PublicPrivacy()).Build();		
		p.savePost();
		object.put("Status", "OK");
		return object.toString();
	}
	@POST
	@Path("/UserPrivatePostService")
	public String PrivatePostService(@FormParam("content") String  content,@FormParam("timeLine") String timeLine,
			                  @FormParam("feeling") String feeling,
			                  @FormParam("hashTag") String  hashTag) {
		JSONObject object = new JSONObject();
		String owner= UserEntity.loggedUser.getEmail();
		Post p= new UserPost.UserPostBuilder(content, owner)
        				.setFeelig(feeling).setHashTag(hashTag).setUserPrivacy(new PrivatePrivacy()).Build();		
		p.savePost();
		object.put("Status", "OK");
		return object.toString();
	}
	
	@POST
	@Path("/createpageservice")
	public String createpageservice(@FormParam("ownername") String ownernam,
		@FormParam("pagename") String pagname,
		@FormParam("pagecategory") String pagcategory,
		@FormParam("pagetypee") String pagtype) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(ownernam);
		//System.out.println("name="+user.getName());
		if (user == null) {
			object.put("Status", "Failed");
		} else {
		//	System.out.println("asd");
			
			user.savePage(user,pagname,pagcategory,pagtype,ownernam); 
			object.put("Status", "OK");
			//ArrayList<String>requests=user.requests;
			//requests.add(UserEntity.loggedUser.getEmail());
			//user.requests=requests;
			//user.saveRequests(user);
			//object.put("Status", "OK");
		}

		return object.toString();

	}
}
