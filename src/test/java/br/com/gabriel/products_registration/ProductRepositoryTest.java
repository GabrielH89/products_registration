package br.com.gabriel.products_registration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.gabriel.products_registration.entity.Product;
import br.com.gabriel.products_registration.repository.ProductRepository;

@DataJpaTest
@ActiveProfiles("test") 
public class ProductRepositoryTest {

	
	
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Deve salvar e recuperar um produto pelo ID")
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
}
