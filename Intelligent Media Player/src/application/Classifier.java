package application;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class Classifier {
	Image image;
	Class properties;
	ArrayList <Class> information=new ArrayList<>();
	
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public ArrayList <Class> getInformation() {
		return information;
	}
	
	
	public void setInformation(String className,String confidenceScore) {
		information.add(new Class(className,confidenceScore));
	}
	
	
}
