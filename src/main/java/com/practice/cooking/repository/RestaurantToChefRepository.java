package com.practice.cooking.repository;

import com.practice.cooking.model.RestaurantToChef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantToChefRepository extends JpaRepository<RestaurantToChef, Long>  {
}
