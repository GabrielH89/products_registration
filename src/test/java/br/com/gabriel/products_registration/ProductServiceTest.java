package br.com.gabriel.products_registration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.gabriel.products_registration.entity.Product;
import br.com.gabriel.products_registration.repository.ProductRepository;
import br.com.gabriel.products_registration.service.ProductService;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all products")
    void shouldReturnAllProducts() {
        Product product1 = new Product();
        product1.setId_product(1L);
        product1.setName("TV");
        product1.setPrice(2000.0);
        product1.setDescription("Smart TV");

        Product product2 = new Product();
        product2.setId_product(2L);
        product2.setName("Radio");
        product2.setPrice(300.0);
        product2.setDescription("FM Radio");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        var products = productService.getAll();

        assertThat(products).hasSize(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return a product by ID")
    void shouldReturnProductById() {
        Product product = new Product();
        product.setId_product(1L);
        product.setName("TV");
        product.setPrice(2000.0);
        product.setDescription("Smart TV");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productService.getById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("TV");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return NOT_FOUND if product does not exist by ID")
    void shouldReturnNotFoundById() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productService.getById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should create a product")
    void shouldCreateProduct() {
        Product product = new Product();
        product.setName("TV");
        product.setPrice(2000.0);
        product.setDescription("Smart TV");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productService.create(product);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("TV");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should update a product by ID")
    void shouldUpdateProductById() {
        Product existingProduct = new Product();
        existingProduct.setId_product(1L);
        existingProduct.setName("Old TV");
        existingProduct.setPrice(1500.0);
        existingProduct.setDescription("Old model");

        Product updatedProduct = new Product();
        updatedProduct.setName("New TV");
        updatedProduct.setPrice(2000.0);
        updatedProduct.setDescription("Smart TV");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        ResponseEntity<String> response = productService.updateById(updatedProduct, 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Product updated with success");
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    @DisplayName("Should delete all products")
    void shouldDeleteAllProducts() {
        when(productRepository.count()).thenReturn(5L);

        ResponseEntity<String> response = productService.deleteAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("All products deleted with success");
        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Should delete a product by ID")
    void shouldDeleteProductById() {
        Product product = new Product();
        product.setId_product(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<String> response = productService.deleteById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Product deleted with success");
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return NOT_FOUND if trying to delete a non-existent product by ID")
    void shouldReturnNotFoundOnDeleteById() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = productService.deleteById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Product doesn't exist");
        verify(productRepository, never()).deleteById(anyLong());
 
    }
}
 