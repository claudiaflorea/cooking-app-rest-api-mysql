package com.practice.cooking.repository;

import com.practice.cooking.model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends MongoRepository<Dish, Long> {
}
