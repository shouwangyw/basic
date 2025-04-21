package coroutines;

import kilim.Pausable;
import kilim.Task;

public class LotOfJavaTask {

	static int n = 800000;
	public static void main(String[] args) {
		 
//		  if(args.length>0) {
//			  createJavaTask();
//		  }else {
 			  createKilimTask();
//		  }
		  
//		  try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  System.exit(0);
	}

	public static void createJavaTask() {
		 long current = System.nanoTime();
		 for (int i = 1; i <= n; i++) {
             Thread t = new Thread();
             t.start();
            
             if (i % n == 0) {
                 System.out.println("  created " + i + " common tasks cost "+(System.nanoTime()-current));
             }
         }
	}
	public static void createKilimTask() {
		 long current = System.nanoTime();
		for(int i=1;i<=n;i++) {
			Task t = new ATask();
			t.start();
			 if (i % n == 0) {
                 System.out.println("  created " + i + " tasks .... (contd.) cost "+(System.nanoTime()-current));
             }
		}
	}
}

class ATask extends Task {
    
    public void execute() throws Pausable {
         
    }

}
