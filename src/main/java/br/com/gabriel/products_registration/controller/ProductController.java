package br.com.gabriel.products_registration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.products_registration.entity.Product;
import br.com.gabriel.products_registration.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	 public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll(); // Retorna a lista de produtos
        return ResponseEntity.ok(products); // Retorna a lista de produtos dentro de ResponseEntity
    }
	
	@GetMapping("/{id_product}")
	public ResponseEntity<Product> getUserById(@PathVariable("id_product") Long id) {
		return productService.getById(id);
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
	   return productService.create(product);
	}
	
	@PutMapping("/{id_product}")
	public ResponseEntity<String> updateProductById(@PathVariable("id_product") Long id, @Valid @RequestBody Product product) {
		return productService.updateById(product, id);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllProduct() {
		return productService.deleteAll();
	}
	
	@DeleteMapping("/{id_product}")
	public ResponseEntity<String> deleteProductById(@PathVariable("id_product") Long id) {
		return productService.deleteById(id);
	}
}
