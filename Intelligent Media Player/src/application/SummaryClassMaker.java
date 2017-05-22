package application;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SummaryClassMaker {
	static int layoutY=10;
	static int layoutX=5;
	static int line =0;
	public Label labelMaker(String className){
		
		if(line>16)
		{	layoutX=200;
			layoutY=10;
			line=0;
		}
		Label lbl1=new Label();
		lbl1.setText(className);
		
		lbl1.setLayoutX(layoutX);
		lbl1.setLayoutY(layoutY);
		layoutY+=15;
		line++;
		return lbl1;
	}
	
	public Slider labelMaker(int number){
		

		Slider s1=new Slider();
		s1.setValue(number);
		
		s1.setLayoutX(layoutX);
		s1.setLayoutY(layoutY);
		layoutY+=15;
		//line++;
		return s1;
	}

}
