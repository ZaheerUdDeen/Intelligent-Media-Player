package application;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class VideoToFrameConverter implements Runnable{
	WatsonThread wt1,wt2;
	static Thread watsonT1,watsonT2;
	
	
	
	
	String path;
	ListOfFrame listOfimages;
	VideoToFrameConverter(String path){
		this.path=path;
	}
	
	
	File fackFile;
	public void convertVideosToFrame() throws FrameGrabber.Exception {
		// smooth("C:\\Users\\Zephyr\\Documents\\NetBeansProjects\\FacadePattern\\src\\facadepattern\\download.jpg");
		//file:///C:/Users/Zephyr/Videos/funy.mp4
		FFmpegFrameGrabber g = new FFmpegFrameGrabber(path);

		g.start();
		int j = 0;

		try {
			System.out.println("" + g.getLengthInFrames());
			RenderedImage im = null;
			
			ListOfFrame img;
			wt1=new WatsonThread(true);
			wt2=new WatsonThread(false);
			for (int i = 0; i < g.getLengthInFrames(); i++) {
				im = g.grab().getBufferedImage();
				
				
				j++;
				if (j == 30) {
					//img=(ListOfFrame) im;
					
					fackFile=new File("C:\\Users\\Zephyr\\Videos\\frames\\video-frame-" + i + ".jpg");
					WatsonThread.listOfImages.add(fackFile);
					ImageIO.write(im, "jpg", fackFile);
					j = 0;
				}
			}

		} catch (IOException ex) {
			Logger.getLogger(VideoToFrameConverter.class.getName()).log(Level.SEVERE, null, ex);
		}

		g.stop();
		
		ImageLoader.file=fackFile;
		MediaUIController.viedoPlay.start();
		
		
		watsonT1=new Thread(wt1);
		watsonT1.start();
		
		watsonT2=new Thread(wt2);
		watsonT2.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			convertVideosToFrame();
			MediaUIController.frameConverterThread.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
