package com.practice.cooking.repository;

import java.util.List;

import com.practice.cooking.model.Chef;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends MongoRepository<Chef, Long> {
    
    List<Chef> findAllByName(String name);
    
    @Query("{name : { $regex: '^Chef.'}}")
    List<Chef> findAllByNameStartingWithChefPrefix();
}
