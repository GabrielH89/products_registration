package br.com.gabriel.products_registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabriel.products_registration.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
