package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {

    List<Chef> findAllByName(String name);

    @Query(value = "SELECT * FROM chefs WHERE c_name LIKE 'Chef. %' ", nativeQuery = true)
    List<Chef> findAllByNameStartingWithChefPrefix();
}
