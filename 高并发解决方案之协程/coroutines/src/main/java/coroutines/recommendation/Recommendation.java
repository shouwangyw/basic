package coroutines.recommendation;

import java.util.HashMap;

public interface Recommendation {

	
	 HashMap<String,String> getRecommendedVideos(String[] accountIds);
}
