package coroutines;

import kilim.Pausable;

public class SleepTest {

	static int processor = Runtime.getRuntime().availableProcessors();
	public static void main(String[] args) {
		 
		  if(args.length>0) {
			  createJavaTask();
		  }else {
			  createKilimTask();
		  }
		  
//		  try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  System.exit(0);
	}

	
	private static void createKilimTask() {
		for(int i=0;i<processor*3;i++) {
			new kilim.Task() {
				public void execute() throws Pausable {
					kilim.Task.sleep(1000);
					System.out.println(Thread.currentThread().getName()+" finished");
				 
			}}.start();
		}
		
	}
	
	private static void createJavaTask() {
		for(int i=0;i<processor*3;i++) {
			new Thread() {
				
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				System.out.println(Thread.currentThread().getName()+" finished");
				
			}}.start();
		}
		
	}
}
