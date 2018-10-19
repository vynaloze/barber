package com.vynaloze.timebank.ws.dao;

import com.vynaloze.timebank.ws.pojo.Service;

import java.util.List;

public interface Dao {
    List<Service> getAll();

    Service postService(Service service);

    Service reserveService(int id, String user) throws ServiceNotFoundException, ServiceAlreadyReservedException;

    Service deleteService(int id) throws ServiceNotFoundException;
}
