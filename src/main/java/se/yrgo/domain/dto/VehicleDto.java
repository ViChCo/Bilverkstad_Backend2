package se.yrgo.domain.dto;

public record VehicleDto(Long id, String registrationNumber, String brand,
                         String model, int productionYear) {}