package com.electem.product.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electem.product.model.Car;
import com.electem.product.repository.CarRepository;

@RestController
class CarProductController {
    private CarRepository repository;

    public CarProductController(CarRepository repository) {
        this.repository = repository;
    }

    /**
     * 
     * @return list of cars
     */
    @GetMapping("/cool-cars")
    public Collection<Car> coolCars() {
        return repository.findAll().stream()
                .filter(this::isCool)
                .collect(Collectors.toList());
    }

    private boolean isCool(Car car) {
        return !car.getName().equals("AMC Gremlin") &&
                !car.getName().equals("Triumph Stag") &&
                !car.getName().equals("Ford Pinto") &&
                !car.getName().equals("Yugo GV");
    }
}