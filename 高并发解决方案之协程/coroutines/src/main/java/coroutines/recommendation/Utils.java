package coroutines.recommendation;

import java.util.HashMap;

public class Utils {

	public static String getRecommendedVideos(HashMap<String,Double> usertag,HashMap<String,HashMap<String,Double>> movieFactor) {
		double maxScore = 0;
		String result = null;
		for(String movie:movieFactor.keySet()) {
			HashMap<String,Double> factor = movieFactor.get(movie);
			double score = 0;
			for(String tag:usertag.keySet()) {
				if(factor.containsKey(tag)) {
					score+=factor.get(tag)*usertag.get(tag);
				}
			}
			if(score>maxScore) {
				maxScore =  score;
				result = movie;
			}
		}
		return result;
	}

	
	public static HashMap<String,Double> getViewHistoryTag(String accountId){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,Double> result = new HashMap<String,Double>();
		result.put("战争",1.0);
		result.put("爱情",0.8);
		return result;
	}
	
	public static HashMap<String,HashMap<String,Double>> getHottestMovie(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,Double> lj = new HashMap<String,Double>();
		lj.put("战争",0.9);
		lj.put("爱情",0.8);
		
		HashMap<String,Double> zl = new HashMap<String,Double>();
		zl.put("战争",0.7);
		zl.put("爱情",0.6);
		zl.put("爱国",0.8);
		
		HashMap<String,HashMap<String,Double>> movieFactor = new HashMap<String,HashMap<String,Double>>();
		movieFactor.put("乱世佳人", lj);
		movieFactor.put("战狼", zl);
		return movieFactor;
	}
}
