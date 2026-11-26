package se.yrgo.domain.dto;

import java.util.List;

public record CustomerDto(Long id, String name, String phoneNumber,
                          List<VehicleDto> vehicles) {}