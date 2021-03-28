package com.practice.cooking.service;

import java.util.List;

import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Chef;
import com.practice.cooking.repository.ChefRepository;
import com.practice.cooking.utils.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class ChefService {

    private ChefRepository chefRepository;

    private MongoOperations mongoOperations;

    @Autowired
    private DatabaseSequenceGenerator sequenceGenerator;

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Autowired
    public void setChefRepository(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

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

    public void update(Long id, Chef chefDetails) {
        Chef chef = getById(id);
        chef.setName(chefDetails.getName());
        chefRepository.save(chef);
    }

    public void delete(Long id) {
        Chef chef = getById(id);
        chefRepository.delete(chef);
    }

}
