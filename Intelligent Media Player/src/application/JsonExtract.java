package application;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonExtract {
	JSONObject jsonObject, jsonObject1, jsonObject2, jsonObject3;
	JSONArray jArry;
	Classifier classifier,classifier1;
	 static int num=0;
	public static ArrayList <Classifier> classifierList=new ArrayList<>();
	//public static ArrayList <Classifier> classifierList1=new ArrayList<>();
	public void jsonExtractor(String json) {

		try {
			jsonObject = new org.json.JSONObject(json);
			jsonObject1 = new org.json.JSONObject(json);
			jsonObject2 = new org.json.JSONObject(json);
			jsonObject3 = new org.json.JSONObject(json);
			Iterator iterator = jsonObject.keys(), iterator1, iterator2;
			String key = null;
			boolean t=true;
			System.out.println("json" );
			while (iterator.hasNext()) {

				key = (String) iterator.next();
				if (key.equals("images")) {

					jsonObject1 = jsonObject.optJSONArray(key).getJSONObject(0);
					iterator1 = jsonObject1.keys();
					int i = 0;
					while (iterator1.hasNext()) {
						key = (String) iterator1.next();
						i++;
						if (key.equals("classifiers")) {
							jsonObject2 = jsonObject1.optJSONArray(key).getJSONObject(0);
						
							iterator2 = jsonObject2.keys();
							while (iterator2.hasNext()) {
								key = (String) iterator2.next();
								if (key.equals("classes")) {
									
									classifier=new Classifier();
									//classifier.setImage(image);
									for (int j = 0; j < jsonObject2.optJSONArray(key).length(); j++) {
										jsonObject3 = jsonObject2.optJSONArray(key).getJSONObject(j);
										classifier.setInformation(jsonObject3.get("class").toString(), jsonObject3.get("score").toString());//set Image its Class and Confidence score 
//										if(Double.parseDouble(jsonObject3.get("score").toString())>0.7){
//											classifier1.setInformation(jsonObject3.get("class").toString(), jsonObject3.get("score").toString());
//										}
//										//System.out.println("\n" + jsonObject3.get("class") + " " + jsonObject3.get("score"));
									}
									classifierList.add(classifier);
								//	classifierList1.add(classifier1);
									if(num==0)
										MediaUIController.transition2.play();
									num++;
									
									System.out.println("rec no"+num);
									if(num>=WatsonThread.listOfImages.size()){
										MediaUIController.transition2.stop();
									}
									
									
								}
							}
						}
					}

				}

			}
			
			//viewController.setCharts(classifierList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
