package se.yrgo.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.domain.dto.CustomerDto;
import se.yrgo.domain.dto.VehicleBrandDto;
import se.yrgo.domain.dto.VehicleDto;
import se.yrgo.domain.entity.Customer;
import se.yrgo.domain.entity.Vehicle;
import se.yrgo.domain.repository.CustomerRepository;
import se.yrgo.domain.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class VerkstadController{

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public VerkstadController(CustomerRepository customerRepository,
                              VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @PostMapping("/customers")
    public CustomerDto createCustomer(@RequestParam String name, @RequestParam String phone) {
        Customer saved = customerRepository.save(new Customer(name, phone));
        return toDto(saved);
    }

    @GetMapping("/customer-id")
    public ResponseEntity<Long> getCustomerId(@RequestParam String name) {
        return customerRepository.findByName(name)
                .map(c -> ResponseEntity.ok(c.getId()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAllWithVehicles().stream()
                .map(this::toDto)
                .toList();
    }

    @PostMapping("/vehicles")
    public VehicleDto createVehicle(@RequestParam String registrationNumber,
                                    @RequestParam String brand,
                                    @RequestParam String model,
                                    @RequestParam int productionYear) {
        Vehicle saved = vehicleRepository.save(
                new Vehicle(registrationNumber, brand, model, productionYear));
        return toDto(saved);
    }

    @GetMapping("/vehicles")
    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/vehicles-by-brand")
    public List<VehicleDto> getVehiclesByBrand(@RequestParam String brand) {
        return vehicleRepository.findByBrand(brand).stream().map(this::toDto).toList();
    }

    @PostMapping("/addvehicletocustomer")
    public ResponseEntity<VehicleDto> addVehicleToCustomer(@RequestParam Long customerId,
                                                           @RequestParam Long vehicleId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        if (customer.isEmpty() || vehicle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vehicle.get().setCustomer(customer.get());
        return ResponseEntity.ok(toDto(vehicleRepository.save(vehicle.get())));
    }

    @PutMapping("/vehicles/{id}/brand")
    public ResponseEntity<String> updateBrand(@PathVariable Long id, @RequestParam String brand) {
        int updated = vehicleRepository.updateBrand(id, brand);
        if (updated == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Bilmärke uppdaterat till " + brand);
    }

    @GetMapping("/vehicle-brand-info")
    public List<VehicleBrandDto> getVehicleBrandInfo() {
        return vehicleRepository.findAllBrandInfo().stream()
                .map(p -> new VehicleBrandDto(p.getRegistrationNumber(), p.getBrand()))
                .toList();
    }

    // ---- Mappning entity -> DTO ----
    private VehicleDto toDto(Vehicle v) {
        return new VehicleDto(v.getId(), v.getRegistrationNumber(), v.getBrand(),
                v.getModel(), v.getProductionYear());
    }

    private CustomerDto toDto(Customer c) {
        List<VehicleDto> vehicles = c.getVehicles().stream().map(this::toDto).toList();
        return new CustomerDto(c.getId(), c.getName(), c.getPhoneNumber(), vehicles);
    }
}
