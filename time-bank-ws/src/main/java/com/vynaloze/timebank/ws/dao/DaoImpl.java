package com.vynaloze.timebank.ws.dao;

import com.vynaloze.timebank.ws.pojo.Service;

import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {
    private final List<Service> services = new ArrayList<>();

    @Override
    public List<Service> getAll() {
        return services;
    }

    @Override
    public synchronized Service postService(final Service service) {
        if (!services.contains(service)) {
            services.add(service);
        }
        return service;
    }

    @Override
    public synchronized Service reserveService(final int id, final String user) throws ServiceNotFoundException, ServiceAlreadyReservedException {
        for (final Service service : services) {
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
    public synchronized Service deleteService(final int id) throws ServiceNotFoundException {
        for (final Service service : services) {
            if (service.getId() == id) {
                services.remove(service);
                return service;
            }
        }
        throw new ServiceNotFoundException();
    }
}
