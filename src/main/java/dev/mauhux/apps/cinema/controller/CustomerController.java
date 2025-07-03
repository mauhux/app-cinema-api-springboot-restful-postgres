package dev.mauhux.apps.cinema.controller;

import dev.mauhux.apps.cinema.entity.CustomerEntity;
import dev.mauhux.apps.cinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<CustomerEntity> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomerById(@PathVariable UUID id) {
        return customerRepository
                .findById(id)
                .orElse(null);
    }

    @PostMapping
    public CustomerEntity create(@RequestBody CustomerEntity client) {
        return customerRepository.save(client);
    }

    @PutMapping("/{id}")
    public CustomerEntity update(@RequestBody CustomerEntity client,
                                 @PathVariable UUID id) {
        client.setId(id);
        return customerRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        customerRepository.deleteById(id);
    }
}
