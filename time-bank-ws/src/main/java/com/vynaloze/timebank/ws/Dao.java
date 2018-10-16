package com.vynaloze.timebank.ws;

import com.vynaloze.timebank.common.Service;

import java.util.List;

public interface Dao {
    List<Service> getAll();

    Service postService(Service service);

    Service reserveService(int id, String user) throws ServiceNotFoundException, ServiceAlreadyReservedException;

    Service deleteService(int id) throws ServiceNotFoundException;
}
