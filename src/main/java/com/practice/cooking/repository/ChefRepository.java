package com.practice.cooking.repository;

import com.practice.cooking.model.Chef;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends MongoRepository<Chef, Long> {

}
