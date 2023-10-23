package com.tabsnotspaces.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public List<ServiceProvider> getAllServiceProviders() {
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        for (ServiceProvider provider : serviceProviderRepository.findAll()) {
            serviceProviderList.add(provider);
        }
        return serviceProviderList;
    }

    public ServiceProvider getServiceProviderById(Long id) {
        return serviceProviderRepository.findById(id).orElse(null);
    }

    public ServiceProvider createServiceProvider(ServiceProvider provider) {
        // Add any additional validation or business logic here
        return serviceProviderRepository.save(provider);
    }

    public ServiceProvider updateServiceProvider(Long id, ServiceProvider provider) {
        ServiceProvider existingProvider = serviceProviderRepository.findById(id).orElse(null);
        if (existingProvider != null) {
            // Update the existing provider with new data
            //            // Add any additional validation or business logic here
            existingProvider.setProviderName(provider.getProviderName());
            existingProvider.setServices(provider.getServices()); // Update services, if needed
            return serviceProviderRepository.save(existingProvider);
        }
        return null; // Not found
    }

    public boolean deleteServiceProvider(Long id) {
        ServiceProvider existingProvider = serviceProviderRepository.findById(id).orElse(null);
        if (existingProvider != null) {
            serviceProviderRepository.delete(existingProvider);
            return true;
        }
        return false; // Not found
    }
}
