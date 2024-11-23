package br.com.gabriel.products_registration.service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.com.gabriel.products_registration.entity.Product;
import br.com.gabriel.products_registration.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> getAll() {
		return productRepository.findAll();
	}
	
	public ResponseEntity<Product> getById(Long id_product) {
		Optional<Product> product = productRepository.findById(id_product);
		
		if(product.isPresent()) {
			return ResponseEntity.ok(product.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	public ResponseEntity<String> create(Product product) {		
		 	var savedProduct = productRepository.save(product);
		    return ResponseEntity.status(HttpStatus.CREATED).body("Product created with success ");
	}
	
	public ResponseEntity<String> updateById(Product product, Long id_product) {
		Optional<Product> productToUpdate = productRepository.findById(id_product);
		if(productToUpdate.isPresent()) {
			Product existingProduct = productToUpdate.get();
			existingProduct.setName(product.getName());
			existingProduct.setPrice(product.getPrice());
			existingProduct.setDescription(product.getDescription());
            productRepository.save(existingProduct);
            return ResponseEntity.ok("Product updated with success");
		}
		
		return ResponseEntity.status(404).body("No product with this id");
	}
	
	public ResponseEntity<String> deleteAll() {
		var products = productRepository.count();
		if(products == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no products to delete");
		}
		
		productRepository.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("All products deleted with success");
	}
	
	public ResponseEntity<String> deleteById(Long id_product) {
		 Optional<Product> ProductToDelete = productRepository.findById(id_product);
	        if (ProductToDelete.isPresent()) {
	            productRepository.deleteById(id_product);
	            return ResponseEntity.ok("Product deleted with success");
	        }
	        return ResponseEntity.status(404).body("Product doesn't exist");
	}
}
