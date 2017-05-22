package application;

import java.io.File;
import java.util.ArrayList;

public class WatsonThread implements Runnable{
	VisualRecog vr=new VisualRecog();
	VisualRecog vr1=new VisualRecog();
	static ArrayList<File> listOfImages;
	boolean even=false;
	WatsonThread(boolean even){
		this.even=even;
		listOfImages=new ArrayList<File>();
	}
	static int num=0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if(even==true){
			for(int i=0;i<listOfImages.size();i+=2){
				System.out.println("Size"+listOfImages.size());
				vr.imageRecgnition(listOfImages.get(i));
				//System.out.println("wt1 index "+i+"img no "+num++);
			}
			VideoToFrameConverter.watsonT1.stop();
			
			//MediaUIController.suumaryObserver.start();
		}
		else{
			for(int i=1;i<listOfImages.size();i+=2){
				//System.out.println("wt2 index "+i+" "+"img no "+num++);
				vr1.imageRecgnition(listOfImages.get(i));
			}
			
			
			VideoToFrameConverter.watsonT2.stop();
		}	
	}
}
