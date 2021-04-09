package com.practice.cooking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.mapper.ChefDtoToEntityMapper;
import com.practice.cooking.mapper.ChefEntityToDtoMapper;
import com.practice.cooking.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChefService {

    private final ChefRepository chefRepository;
    
    private final ChefDtoToEntityMapper dtoToEntityMapper;

    private final ChefEntityToDtoMapper entityToDtoMapper;


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

    public ChefDto add(ChefDto chef) {
        return entityToDtoMapper.entityToDto(
            chefRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(chef)))
        );
    }

    public ChefDto update(Long id, ChefDto chefDetails) {
        ChefDto chef = getById(id);
        chef.setName(chefDetails.getName());
        chefRepository.save(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(chef)));
        return chef;
    }

    public Map<String, Boolean> delete(Long id) {
        ChefDto chef = getById(id);
        chefRepository.delete(Objects.requireNonNull(dtoToEntityMapper.dtoToEntity(chef)));
        Map<String, Boolean> chefMap = new HashMap<>();
        chefMap.put("Chef with id " + id + " is deleted ", Boolean.TRUE);
        return chefMap;
    }

}
