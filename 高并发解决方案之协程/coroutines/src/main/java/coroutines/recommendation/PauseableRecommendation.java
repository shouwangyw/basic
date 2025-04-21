package coroutines.recommendation;

import java.util.ArrayDeque;
import java.util.HashMap;

import org.apache.log4j.Logger;

import kilim.Event;
import kilim.EventPublisher;
import kilim.EventSubscriber;
import kilim.ExitMsg;
import kilim.Mailbox;
import kilim.Pausable;
import kilim.Task;
import kilim.Task.Spawn; 

public class PauseableRecommendation implements Recommendation{
	static Logger log = Logger.getLogger(PauseableRecommendation.class);
 
		public static void main(String[] args) {
		PauseableRecommendation recommendation = new PauseableRecommendation();
		long start = System.nanoTime();
		log.info(recommendation.getRecommendedVideos(new String[]{"1","2","3","4","5"})+" task cost:" + (System.nanoTime()-start));
	    System.exit(0);
			
//			int procesorNum = Runtime.getRuntime().availableProcessors();
//			System.out.println("the machine has procesor:"+procesorNum);
//			ExecutorService executors = Executors.newFixedThreadPool(procesorNum);
//			for(int i=0;i<procesorNum*3;i++) {
//				executors.execute(new Recommended());
//			}
		
		
 	}
		
		public HashMap<String,String> getRecommendedVideos(String[] accountIds) {
			HashMap<String,String> result = new HashMap<String,String>();
			ArrayDeque<Recommended> ad = new ArrayDeque<Recommended>();  
			for(String accountId:accountIds) {
				Recommended task = new Recommended(accountId);
				task.run();
				ad.push(task);
			}
			while(!ad.isEmpty()) { // check all thread without block one by one
				Recommended item = ad.poll();
				ExitMsg<String> msg =  item.joinb();
				result.put(item.getAccountId(), msg.result);
			}
			 
			 return result;
			 
		}
		
   static class Recommended extends Spawn<String>{
	   private Mailbox<HashMap<String, Double>> tagbox=new Mailbox<HashMap<String, Double>>();
	   private Mailbox<HashMap<String, HashMap<String, Double>>> factorbox=new Mailbox<HashMap<String, HashMap<String, Double>>>();
	   private String accountId;
	  
	   public String getAccountId() {
		return accountId;
	}
	Recommended(String accountId){
		   this.accountId = accountId;
		   factorbox.addMsgAvailableListener(new EventSubscriber() {

			@Override
			public void onEvent(EventPublisher ep, Event e) {
				continueRun() ;
				
			}});
	   }
	   public void continueRun() {
		   log.info("continue run");
		   this.run();
	   }
		@Override
		public void execute() throws Pausable, Exception {
			new TagTask(accountId,tagbox).start();
			new FactorTask(factorbox).start();
//			while(true) {
//				if(tagbox.getnb()==null||factorbox.getnb()==null) {
//					log.info("rest for a while");
//					Task.sleep(2100);
//				}
//				break;
//			}
//			 
			log.info("rest for a while");
			yield();
			log.info("recommendation task begin");
			exitResult = Utils.getRecommendedVideos(tagbox.get(), factorbox.get());
		 
		}
 
   }
   
   static class TagTask extends Spawn<HashMap<String, Double>>{
	   private Mailbox<HashMap<String, Double>> mailbox;
	   private String accountId;
		public TagTask(String accountId,Mailbox<HashMap<String, Double>> mailbox) {
			this.accountId = accountId;
			this.mailbox = mailbox;
	}

		@Override
		public void execute() throws Pausable, Exception {
			log.info("tag task begin");
			long start = System.nanoTime();
			mailbox.put(Utils.getViewHistoryTag(accountId));
			log.info("tag task cost:" + (System.nanoTime()-start)); 
		}
   }
   
   static class FactorTask extends Task{
	   private Mailbox<HashMap<String, HashMap<String, Double>>> mailbox;
 		public FactorTask(Mailbox<HashMap<String, HashMap<String, Double>>> mailbox) {
		this.mailbox = mailbox;
	}

		@Override
 		public void execute() throws Pausable, Exception {
			log.info("factor task begin");
			long start = System.nanoTime();
			mailbox.put(Utils.getHottestMovie());
 			log.info("factor task cost:" + (System.nanoTime()-start)); 
 		}
    }
   


}
 