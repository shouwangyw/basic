package coroutines;

import kilim.Pausable;
import kilim.Task;

public class SimplePauseContine {

	
	 public static void main(String[] args) {
	        Task task = new Task() {
	            @Override
	            public void execute() throws Pausable {
	                System.out.print("hello");
	                yield();
	                System.out.print("Äã");
	                yield();
	                System.out.println("finish");
	            }
	        };
	      
	        task.run();
	        System.out.println(" world");
	        task.run();
	        System.out.println("ºÃ");
	        task.run();
	       
	    }
}
