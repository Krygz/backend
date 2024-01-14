package com.krug.teste.repositories;

import com.krug.teste.model.Category;
import com.krug.teste.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
