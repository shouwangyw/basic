package coroutines;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleCode2 {

	 
	public void changeProductImage() {
		for(int i=0;i<10000;i++) {
		int productId = i;
		CompletableFuture<Product> productFuture = CompletableFuture.supplyAsync(() -> {
			 Product p = retriveProduct(productId);
			 return p;
		});
		productFuture.whenComplete((product,e) ->{ 
			CompletableFuture<String> imageFuture = CompletableFuture.supplyAsync(() -> {
				String updateImageUrl = updateImage(product.imageUrl);
				 return updateImageUrl;
			});
			imageFuture.whenComplete((image,ee) ->{
				product.imageUrl = image;
				updateProduct(product);
			});
		});
		} 
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
	}

	class Product{
		String imageUrl;
	}
}
