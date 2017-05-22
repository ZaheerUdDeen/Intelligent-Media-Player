package application;

import java.io.File;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

public class VisualRecog {

	JsonExtract jsonEx;
	Notification notify = new Notification();

	public String imageRecgnition(File file) {
		jsonEx = new JsonExtract();

		// API 1 API 72763e835cdbf762446be5ebbe85acf13f218150 -->
		// Api 2 784320bd23470f6bb04e4189c462c6c4dcb307da  -->
		//API 3  d1ededb540e12ede1d072f9e8a69bd93671dbdfe
		VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
		service.setApiKey("d1ededb540e12ede1d072f9e8a69bd93671dbdfe");
		// System.out.println("Classify an image");
		try {
			ClassifyImagesOptions options = new ClassifyImagesOptions.Builder().images(file).build();

			VisualClassification result = service.classify(options).execute();
			 //System.out.println("Here v1"+result.toString());
			jsonEx.jsonExtractor(result.toString());

			return result.toString();
		} catch (Exception e) {
			notify.errorNotifiaction(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
