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
					// IO��������ʱ200����
				}

				private String updateImage(String imageUrl) {
					// ͼƬѹ���㷨����ʱ500����
					return null;
				}

				private Product retriveProduct(int productId) {
					// // IO��������ʱ100����
					return null;
				}});
		}
	}
	
	
	class Product{
		String imageUrl;
	}
}
