package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import com.jfoenix.controls.JFXSlider;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MainUIController implements Initializable{
	@FXML
	private Pane mainPanel;
	@FXML
	private JFXSlider mediaSlider;
	@FXML
	private ImageView planAndPauseIcon;
	
	@FXML
	private JFXSlider volumeSlider;
	
	@FXML
	private MediaView mediaView;
	MediaPlayer player;
	static Thread cameraThread;
	Media media;
	String file1="file:///C:/Users/Zephyr/Videos/abc.mp4".replaceAll(" ","%20");
	public static VideoCapture capture;
	// Event Listener on Button.onAction
	Notification notify=new Notification();
	MediaUIController mc;//for controlling play and pause button through face detection
	// Event Listener on JFXButton.onAction
	
	@FXML
	public void loadMedia(ActionEvent event) {
    	
		loadMediaScreen();
	}
	
	public void loadMediaScreen(){

		// TODO Auto generated
		mainPanel.getChildren().clear();
		createThreadForCamera();
    	
		
	}
	
	void createThreadForCamera(){
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				FaceDetuctionThread fdt=new FaceDetuctionThread();
				System.out.println("here");
				cameraThread = new Thread(fdt);
				// TODO Auto-generated method stub
					try {
		    			
					    mainPanel.getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("view/MediaUI.fxml")));
					    
					} catch (Exception e) {
						// TODO Auto-generated catch block
						notify.errorNotifiaction("Error Loading MediaUI :"+e.getMessage());
						e.printStackTrace();
					}
			}
			// ...
		});
	}
	// Event Listener on JFXButton.onAction
	
	@FXML
	public void summary(ActionEvent event) {
			MainUIController.cameraThread.stop();
			
			if(MainUIController.capture!=null)
				MainUIController.capture.release();
		
		if(MediaUIController.player!=null)
			MediaUIController.player.stop();
		mainPanel.getChildren().clear();
		try {
			mainPanel.getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("view/ImageLoader.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadMediaScreen();
	}
	
	
	@FXML
	void close(ActionEvent event) {
		Platform.exit();
        System.exit(0);
	}
	
	
//faceDeduction thread to continuesly capturing camera
private  class FaceDetuctionThread  implements Runnable {

    @Override
    public void run() {
    	
    	openAndSetCamera();
    	startCamera();
    }
 // a timer for acquiring the video stream
 	private ScheduledExecutorService timer;
 	// the OpenCV object that performs the video capture
 	
 	// a flag to change the button behavior
 	private boolean cameraActive;
 	
 	// face cascade classifier
 	private CascadeClassifier faceCascade= new CascadeClassifier("haarcascade_frontalface_alt.xml");;
 	private int absoluteFaceSize;
    
    void openAndSetCamera(){
    	capture = new VideoCapture(0);
		this.faceCascade = new CascadeClassifier();
		this.absoluteFaceSize = 0;
		checkboxSelection("resources/haarcascades/haarcascade_frontalface_alt.xml");
		
    }
    void startCamera(){
    	
    	if (!this.cameraActive){
    		
    			capture.open(0);
    			if (capture.isOpened())
    			{
    				this.cameraActive = true;
    				
    				// grab a frame every 33 ms (30 frames/sec)
    				Runnable frameGrabber = new Runnable() {
    					
    					@Override
    					public void run()
    					{
    						// effectively grab and process a single frame
    						Mat frame = grabFrame();   						
    					}
    				};
    				
    				this.timer = Executors.newSingleThreadScheduledExecutor();
    				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
    				
    			}
    			else
    			{
    				// log the error
    				System.err.println("Failed to open the camera connection...");
    			}
		}
    }
    
    
    private Mat grabFrame()
	{
		Mat frame = new Mat();
		
		// check if the capture is open
		if (capture.isOpened())
		{
			try
			{
				// read the current frame
				capture.read(frame);
				
				// if the frame is not empty, process it
				if (!frame.empty())
				{
					
					this.detectAndDisplay(frame);
				}
				
			}
			catch (Exception e)
			{
				// log the (full) error
				System.err.println("Exception during the image elaboration: ");
				e.printStackTrace();
			}
		}
		
		return frame;
	}
    
    private void detectAndDisplay(Mat frame)
	{
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		
		// convert the frame in gray scale
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		// equalize the frame histogram to improve the result
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		// compute minimum face size (20% of the frame height, in our case)
		if (this.absoluteFaceSize == 0)
		{
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				this.absoluteFaceSize = Math.round(height * 0.2f);
			}
		}
		
		// detect faces
		this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		if(faces.empty()){
				
				MediaUIController.player.pause();
		}
		else{
			//MediaUIController.playAndPauseDecision();
			if(MediaUIController.lock!=1){
				
				MediaUIController.player.play();
			}
		}
		// each rectangle in faces is a face: draw them!
		
			
	}
    
    private void checkboxSelection(String classifierPath)
	{
		// load the classifier(s)
		this.faceCascade.load(classifierPath);
		

	}

}

}
