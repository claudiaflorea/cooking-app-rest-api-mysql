package com.practice.cooking.repository;

import com.practice.cooking.model.RestaurantToDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantToDishRepository extends JpaRepository<RestaurantToDish, Long>  {
}
