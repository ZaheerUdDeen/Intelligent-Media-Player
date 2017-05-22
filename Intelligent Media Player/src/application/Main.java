package application;
	
import org.opencv.core.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	Stage newStage;
	public static Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
				Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("view/MainUI.fxml"));
		 		Scene scene = new Scene(root);
		 		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setTitle("IntelligentMedia Player");
				
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.UNDECORATED);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.setX(10);
				primaryStage.setY(1);
				mainStage=primaryStage;
				primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}
	

}
