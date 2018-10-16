package com.vynaloze.timebank.ws;

import com.vynaloze.timebank.common.Service;

import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {
    private List<Service> services = new ArrayList<>();

    @Override
    public List<Service> getAll() {
        return services;
    }

    @Override
    public synchronized Service postService(Service service) {
        if (!services.contains(service)) {
            services.add(service);
        }
        return service;
    }

    @Override
    public synchronized Service reserveService(int id, String user) throws ServiceNotFoundException, ServiceAlreadyReservedException {
        for (Service service : services) {
            if (service.getId() == id) {
                if (service.getPrincipal() != null) {
                    throw new ServiceAlreadyReservedException();
                }
                service.setPrincipal(user);
                return service;
            }
        }
        throw new ServiceNotFoundException();
    }

    @Override
    public synchronized Service deleteService(int id) throws ServiceNotFoundException {
        for (Service service : services) {
            if (service.getId() == id) {
                services.remove(service);
                return service;
            }
        }
        throw new ServiceNotFoundException();
    }
}
