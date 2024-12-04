package br.com.gabriel.products_registration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.gabriel.products_registration.entity.Product;
import br.com.gabriel.products_registration.repository.ProductRepository;

@DataJpaTest
@ActiveProfiles("test") 
public class ProductRepositoryTest {	
	
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("should create product with success")
    void shouldCreateProduct() {
    	Product product = new Product();
    	 product.setName("Tv");
         product.setPrice(2839.70);
         product.setDescription("aaaa bbbb");

         Product savedProduct = productRepository.save(product);
         assertThat(savedProduct).isNotNull();
         assertThat(savedProduct.getId_product()).isNotNull();
         assertThat(savedProduct.getName()).isEqualTo("Tv");
         assertThat(savedProduct.getPrice()).isEqualTo(2839.70);
         assertThat(savedProduct.getDescription()).isEqualTo("aaaa bbbb");
    }    
    
    @Test
    @DisplayName("Should save and recovery product by id")
    void shouldSaveAndRetrieveProductById() {
        Product product = new Product();
        product.setName("Tv");
        product.setPrice(2839.70);
        product.setDescription("bahab ja js");

        Product savedProduct = productRepository.save(product);

        Optional<Product> retrievedProduct = productRepository.findById(savedProduct.getId_product());

        assertThat(retrievedProduct).isPresent();
        assertThat(retrievedProduct.get().getName()).isEqualTo("Tv");
        assertThat(retrievedProduct.get().getPrice()).isEqualTo(2839.70);
        assertThat(retrievedProduct.get().getDescription()).isEqualTo("bahab ja js");
    }
    
    @Test
    @DisplayName("Should return prducts added to database") 
    void shouldReturnAllProducts() {
    	  Product product = new Product();
          product.setName("Radio");
          product.setPrice(2839.70);
          product.setDescription("bahab ja js");

          productRepository.save(product);

          var products = productRepository.findAll();

          assertThat(products).isNotEmpty();      
    }
    
    @Test
    @DisplayName("Should return empty when products are not found") 
    void shouldReturnEmptyTogetAll() {
    	var products = productRepository.findAll();
    	assertThat(products).isEmpty();
    }
    
    @Test
    @DisplayName("Should return empty when product not found by id") 
    void shouldReturnEmptyById() {
    	Optional<Product> retrievedProduct = productRepository.findById(9999L);
    	assertThat(retrievedProduct).isNotPresent();
    }
    
    @Test
    @DisplayName("Should delete all products from database") 
    void shouldeleteAllProducts() {
    	 Product product1 = new Product();
    	    product1.setName("Tv");
    	    product1.setPrice(2839.70);
    	    product1.setDescription("Product 1");

    	    Product product2 = new Product();
    	    product2.setName("Radio");
    	    product2.setPrice(1234.56);
    	    product2.setDescription("Product 2");

    	    productRepository.save(product1);
    	    productRepository.save(product2);
          
    	    productRepository.deleteAll();
    	    
    	    var products = productRepository.findAll();
    	    assertThat(products).isEmpty();
    }
    
    @Test
    @DisplayName("Should delete product from database by id and return product remained in the database") 
    void shouldeleteProductById() {
    	 Product product1 = new Product();
    	    product1.setName("Tv");
    	    product1.setPrice(2839.70);
    	    product1.setDescription("Product 1");

    	    Product product2 = new Product();
    	    product2.setName("Radio");
    	    product2.setPrice(1234.56);
    	    product2.setDescription("Product 2");

    	    productRepository.save(product1);
    	    productRepository.save(product2);
          
    	    productRepository.deleteById(product1.getId_product());
    	    
    	    Optional<Product> deletedProduct = productRepository.findById(product1.getId_product());
    	    assertThat(deletedProduct).isNotPresent();
    	    
    	    Optional<Product> remainingProduct = productRepository.findById(product2.getId_product());
    	    assertThat(remainingProduct).isPresent();
    	    assertThat(remainingProduct.get().getName()).isEqualTo("Radio");
    }

   
}



