package coroutines.recommendation;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

public class MultiThreadRecommendation implements Recommendation{
	static Logger log = Logger.getLogger(MultiThreadRecommendation.class);
	 ExecutorService executor = Executors.newCachedThreadPool();
	 
	public String getRecommendedVideos(String accountId) {
		
		Callable<HashMap<String,HashMap<String,Double>>> hotmovie = new Callable<HashMap<String,HashMap<String,Double>>>(){

			@Override
			public HashMap<String,HashMap<String,Double>> call() throws Exception {
			 
				return Utils.getHottestMovie();
			}};
		FutureTask<HashMap<String,HashMap<String,Double>>> task1 = new FutureTask<HashMap<String,HashMap<String,Double>>>(hotmovie);
		log.info("submit task for hot movie data");
	    executor.submit(task1);
	    
		 Callable<HashMap<String,Double>> history = new Callable<HashMap<String,Double>>(){

			@Override
			public HashMap<String, Double> call() throws Exception {
				 
				return Utils.getViewHistoryTag(accountId);
			}};
		FutureTask<HashMap<String,Double>> task2 = new FutureTask<HashMap<String,Double>>(history);
		log.info("submit task for hot view history data");
	    executor.submit(task2);
	     
		try {
			log.info("return the result...");
			return Utils.getRecommendedVideos(task2.get(), task1.get());
		} catch (InterruptedException e) {
			log.error(e);
		} catch (ExecutionException e) {
			log.error(e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		MultiThreadRecommendation m = new MultiThreadRecommendation();
		long start = System.nanoTime();
		System.out.println(m.getRecommendedVideos("")+" task cost:" + (System.nanoTime()-start));
	}

	@Override
	public HashMap<String, String> getRecommendedVideos(String[] accountIds) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		for(String account:accountIds) {
			result.put(account, getRecommendedVideos(account));
		}
		
		return result;
	}

}
