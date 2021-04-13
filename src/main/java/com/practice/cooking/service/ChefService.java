package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.jms.Queue;
import javax.jms.Topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.ChefDtoToEntityMapper;
import com.practice.cooking.mapper.ChefEntityToDtoMapper;
import com.practice.cooking.publisher.Publisher;
import com.practice.cooking.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChefService {

    private static final Queue CHEF_QUEUE = new ActiveMQQueue("chef_queue");
    private static final Topic CHEF_TOPIC = new ActiveMQTopic("chef_topic");
    
    private final ChefRepository chefRepository;
    
    private final ChefDtoToEntityMapper dtoToEntityMapper;

    private final ChefEntityToDtoMapper entityToDtoMapper;

    private final Publisher publisher;

    public List<ChefDto> getAll() {
        return chefRepository.findAll().stream()
            .map(chef -> entityToDtoMapper.entityToDto(chef))
            .collect(Collectors.toList());
    }

    public ChefDto getById(Long id) {
        return chefRepository.findById(id)
            .map(chef -> entityToDtoMapper.entityToDto(chef))
            .orElseThrow(() -> new NotFoundException("Chef not found with id " + id));
    }

    public List<ChefDto> getChefsByName(String name) {
        return chefRepository.findAllByName(name)
            .stream()
            .map(chef -> entityToDtoMapper.entityToDto(chef))
            .collect(Collectors.toList());
    }

    public ChefDto add(ChefDto chef) throws JsonProcessingException {
        publisher.sendToConsumerWhenAddingNewRecord(CHEF_QUEUE, chef);
        publisher.notifyAllSubscribersWhenAddingRecord(CHEF_TOPIC, chef);
        return entityToDtoMapper.entityToDto(
            chefRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(chef)))
        );
    }

    public ChefDto update(Long id, ChefDto chefDetails) throws JsonProcessingException {
        ChefDto chef = getById(id);
        chef.setName(chefDetails.getName());
        chefRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(chef)));
        publisher.sendToConsumerWhenUpdatingRecord(CHEF_QUEUE, chef);
        return chef;
    }

    public Map<String, Boolean> delete(Long id) throws JsonProcessingException {
        ChefDto chef = getById(id);
        chefRepository.delete(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(chef)));
        publisher.sendToConsumerWhenDeletingRecord(CHEF_QUEUE, chef);
        Map<String, Boolean> chefMap = new HashMap<>();
        chefMap.put("Chef with id " + id + " is deleted ", Boolean.TRUE);
        return chefMap;
    }
 
}
