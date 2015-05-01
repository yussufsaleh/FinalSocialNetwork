package com.FCI.SWE.Models;
// one of the concrete command
public class AcceptReqNotification extends Notification{
	public AcceptReqNotification(String userEmail,String from){
		super(userEmail,from);
	}
	public void send(){
		NotificationEntity n=new NotificationEntity(userEmail,from,"AcceptRequest");
		n.saveNotification();
		
	}
}
