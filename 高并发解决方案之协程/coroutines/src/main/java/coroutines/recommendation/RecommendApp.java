package coroutines.recommendation;

import org.apache.log4j.Logger;

public class RecommendApp {

	static Logger log = Logger.getLogger(RecommendApp.class);
 
	 
	public static void main(String[] args) {
		Recommendation recommend = null;
		if(args==null||args.length==0) {
			recommend = new MultiThreadRecommendation();
		}else {
			recommend = new PauseableRecommendation();
		}
		long start = System.nanoTime();
		String[] accountIds = new String[]{"1","2","3","4","5"};
		log.info(recommend.getRecommendedVideos(accountIds));
		log.info("total task cost:" + (System.nanoTime()-start)); 
		System.exit(0);
	}

}
