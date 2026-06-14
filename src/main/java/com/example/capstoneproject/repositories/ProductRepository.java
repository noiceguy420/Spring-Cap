package com.example.capstoneproject.repositories;
import com.example.capstoneproject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
