package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public class ImageLoader implements Initializable{	
	VisualRecog vr=new VisualRecog();
	ObservableList<Path> imageFiles = FXCollections.observableArrayList();
	Stage newStage=new Stage();
	static Path pth;
	
	@FXML
    private Pane summarrPane;
	@FXML
    private Pane chartPane;
    @FXML
    private TextArea sumarryText;
    @FXML
    private StackPane imageHolder;
    
    @FXML
    private ListView<Path> imageFilesList;
    
    @FXML
    private BarChart<?, ?> imageInformationChart;

    @FXML
    private CategoryAxis classi;

    @FXML
    private NumberAxis scores;
    static File file;
    DirectoryChooser chooser;
    @FXML
    void loadImages(ActionEvent event) {
    	
       DirectoryChooser chooser = new DirectoryChooser();
       file = chooser.showDialog(newStage);
    	 if (file != null) {
	            imageFiles.setAll(load(file.toPath()));
	        } 
	        
	        Loadimages();
    }
    @FXML
    void showSummary(ActionEvent event) {
    	summarrPane.setVisible(true);
    	chartPane.setVisible(false);
      for(int i=0;i<JsonExtract.classifierList.size();i++){
    	  for(int j=0;j<JsonExtract.classifierList.get(i).information.size();j++)
    		  
    		  if(Double.parseDouble(JsonExtract.classifierList.get(i).information.get(j).getConfidenceScore())>0.6) { 
    			
    			  //System.out.println(JsonExtract.classifierList.get(i).information.get(j).getClassName());
    			  sumarryText.setText(sumarryText.getText().toString()+"\n"+JsonExtract.classifierList.get(i).information.get(j).getClassName());
    		  }  	  
      }
    }
    
    Image image;
    public void Loadimages(){

        imageFilesList.setCellFactory(listView -> new ListCell<Path>() {
            private final ImageView imageView = new ImageView();

           @Override
            public void updateItem(Path path, boolean empty) {
                super.updateItem(path, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    
                	
                	image = new Image(path.toUri().toString(), 150, 170, true, true, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                    
                }
            }
        });
        
        imageFilesList.setOrientation(Orientation.VERTICAL);
        imageFilesList.getItems().addAll(imageFiles);
        imageFilesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        imageFilesList.getSelectionModel().selectedItemProperty().addListener((obs, oldFile, newFile) -> {
            if (newFile == null) {
            	
            } else {	
            	summarrPane.setVisible(false);
            	chartPane.setVisible(true);
            	//images.setImage(new Image());
            	JsonExtract.classifierList.clear();
            	vr.imageRecgnition(newFile.toFile());
            	setCharts();
            }
        });
    }    
  
    private List<Path> load(Path directory) {
        List<Path> files = new ArrayList<>();
        try {
            Files.newDirectoryStream(directory, "*.{jpg,jpeg,png,JPG,JPEG,PNG}").forEach(file -> files.add(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files ;
    }
    
    
    ArrayList<XYChart.Series> list=new ArrayList<>();
    @SuppressWarnings("unchecked")
	public  void setCharts(){
    	//XYChart.Series<String, Number> series1=new XYChart.Series<String, Number> ();
    	imageInformationChart.getData().clear();
    	list.clear();
    		for(int i=0;i<JsonExtract.classifierList.size();i++){
    			for(int j=0;j<JsonExtract.classifierList.get(i).information.size();j++){
    				list.add(new  XYChart.Series());
    				list.get(j).setName(JsonExtract.classifierList.get(i).information.get(j).getClassName()); 
    				//System.out.println(JsonExtract.classifierList.size()+JsonExtract.classifierList.get(i).information.get(j).getClassName()+" "+JsonExtract.classifierList.get(i).information.get(j).getConfidenceScore());
          			 
    				list.get(j).getData().add(new XYChart.Data(JsonExtract.classifierList.get(i).information.get(j).getClassName(), Double.parseDouble((JsonExtract.classifierList.get(i).information.get(j).getConfidenceScore()))));

    			}

    		}
    		imageInformationChart.getData().clear();
    		
        for(int i=0;i<list.size();i++){
        	 imageInformationChart.getData().addAll(list.get(i)); 
        }
       
        
        
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		 if (ListOfFrame.imageDirectory != null) {
	            imageFiles.setAll(load(ListOfFrame.imageDirectory.toPath()));
	        } 
	        
	        Loadimages();
	}
}