package com.tabsnotspaces.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    @GetMapping
    public List<ServiceProvider> getAllServiceProviders() {
        List<ServiceProvider> providers = serviceProviderService.getAllServiceProviders();
        return providers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceProvider> getServiceProviderById(@PathVariable Long id) {
        ServiceProvider provider = serviceProviderService.getServiceProviderById(id);
        if (provider == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(provider);
    }

    @PostMapping
    public ResponseEntity<ServiceProvider> createServiceProvider(@RequestBody ServiceProvider provider) {
        ServiceProvider createdProvider = serviceProviderService.createServiceProvider(provider);
        return ResponseEntity.status(201).body(createdProvider);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceProvider> updateServiceProvider(@PathVariable Long id, @RequestBody ServiceProvider provider) {
        ServiceProvider updatedProvider = serviceProviderService.updateServiceProvider(id, provider);
        if (updatedProvider == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProvider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        boolean deleted = serviceProviderService.deleteServiceProvider(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
