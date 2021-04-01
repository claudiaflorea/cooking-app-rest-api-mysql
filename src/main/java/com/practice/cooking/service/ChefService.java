package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Chef;
import com.practice.cooking.repository.ChefRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChefService {

    private final ChefRepository chefRepository;

    private final MongoOperations mongoOperations;

    private final DatabaseSequenceGenerator sequenceGenerator;

    public List<Chef> getAll() {
        return chefRepository.findAll();
    }

    public Chef getById(Long id) {
        return chefRepository.findById(id).orElseThrow(() -> new NotFoundException("Chef not found with id " + id));
    }

    public Chef add(Chef chef) {
        chef.setId(sequenceGenerator.generateSequence(Chef.SEQUENCE_NAME));
        return chefRepository.save(chef);
    }

    public Chef update(Long id, Chef chefDetails) {
        Chef chef = getById(id);
        chef.setName(chefDetails.getName());
        chefRepository.save(chef);
        return chef;
    }

    public Map<String, Boolean> delete(Long id) {
        Chef chef = getById(id);
        chefRepository.delete(chef);
        Map<String, Boolean> chefMap = new HashMap<>();
        chefMap.put("Chef with id " + id + " is deleted ", Boolean.TRUE);
        return chefMap;
    }

}
