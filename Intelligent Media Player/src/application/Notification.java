package application;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class Notification {
	public void succesNotifiaction(String message,String title)
	 {		   
		   // String title = "Congratulations Zaheer";
	        NotificationType notification = NotificationType.SUCCESS;
	        
	        TrayNotification tray = new TrayNotification();
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(notification);
	        tray.setAnimationType(AnimationType.SLIDE);
	        tray.showAndDismiss(Duration.seconds(6));
		 
	 }
	public void errorNotifiaction(String message)
	 {
		   
		    String title = "Error! ";
	        NotificationType notification = NotificationType.ERROR;
	        
	        TrayNotification tray = new TrayNotification();
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(notification);
	        tray.setAnimationType(AnimationType.POPUP);
	        tray.showAndDismiss(Duration.seconds(2));
		 
	 }	
	public void warningNotifiaction(String message)
	 {
		   
		    String title = "Error! ";
	        NotificationType notification = NotificationType.WARNING;
	        
	        TrayNotification tray = new TrayNotification();
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(notification);
	        tray.setAnimationType(AnimationType.FADE);
	        tray.showAndDismiss(Duration.seconds(4));
		 
	 }	
	public void informationNotifiaction(String message)
	 {
		   
		    String title = "Information! ";
	        NotificationType notification = NotificationType.INFORMATION;
	        
	        TrayNotification tray = new TrayNotification();
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(notification);
	        tray.setAnimationType(AnimationType.SLIDE);
	        tray.showAndDismiss(Duration.seconds(5));
		 
	 }	
	public void noticeNotifiaction(String message)
	 {
		   
		    String title = "Information! ";
	        NotificationType notification = NotificationType.INFORMATION;
	        
	        TrayNotification tray = new TrayNotification();
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(notification);
	        tray.setAnimationType(AnimationType.POPUP);
	        tray.showAndDismiss(Duration.seconds(3));
		 
	 }	

}
