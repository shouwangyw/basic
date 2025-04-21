package coroutines;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleCode {

	
	ExecutorService executors = Executors.newFixedThreadPool(10);
	
	public void changeProductImage() {
		for(int i=0;i<10000;i++) {
			int productId = i;
			executors.submit(new Runnable() {

				@Override
				public void run() {
					Product p = retriveProduct(productId);
					String updateImageUrl = updateImage(p.imageUrl);
					p.imageUrl = updateImageUrl;
					updateProduct(p);
				}
				private void updateProduct(Product p) {
					// IO²Ù×÷£¬ºÄÊ±200ºÁÃë
				}

				private String updateImage(String imageUrl) {
					// Í¼Æ¬Ñ¹ËõËã·¨£¬ºÄÊ±500ºÁÃë
					return null;
				}

				private Product retriveProduct(int productId) {
					// // IO²Ù×÷£¬ºÄÊ±100ºÁÃë
					return null;
				}});
		}
	}
	
	
	class Product{
		String imageUrl;
	}
}
