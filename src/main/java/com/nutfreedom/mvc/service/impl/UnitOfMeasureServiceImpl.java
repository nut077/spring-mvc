package com.nutfreedom.mvc.service.impl;

import com.nutfreedom.mvc.command.UnitOfMeasureCommand;
import com.nutfreedom.mvc.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.nutfreedom.mvc.repository.UnitOfMeasureRepository;
import com.nutfreedom.mvc.service.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public List<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toList());
    }
}
