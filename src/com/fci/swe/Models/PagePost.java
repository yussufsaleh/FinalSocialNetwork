package com.FCI.SWE.Models;

import java.util.Map;

import com.FCI.SWE.Models.UserPost.UserPostBuilder;

public class PagePost extends Post{
	
	public int seen;
	public String pageName;
	
	public PagePost(PagePostBuilder pageBuilder) {
		// TODO Auto-generated constructor stub
	}

	public int getSeen() {
		return seen;
	}
	public static class PagePostBuilder {	
		public String owner;
		public String content;
		public int likes;
		public String seen;
		public Privacy userPrivacy;
		public String hashTag;
			
		
		public PagePostBuilder(String content, String owner){
			this.content=content;
			this.owner=owner;
		}
		
		public PagePostBuilder setLikes(int likes) {
			this.likes = likes;
			return this;
		}
		public PagePostBuilder setOwner(String owner) {
			this.owner = owner;
			return this;
		}
		
		public PagePostBuilder setContent(String content) {
			this.content = content;
			return this;
		}
	
	
	
		public PagePostBuilder setSeen (String seen) {
			this.seen = seen;
			return this;
			}
		public PagePostBuilder setHashTag(String hashTag) {
			this.hashTag = hashTag;
			return this;
		}
		public Post Build(){
			
		
			return new PagePost(this);
			
		}
	}
	@Override
	public boolean savePost() {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public Map<Long, String> getAllPosts(String timeLine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, String> getPublicPosts(String timeLine) {
		// TODO Auto-generated method stub
		return null;
	}	
	


}
