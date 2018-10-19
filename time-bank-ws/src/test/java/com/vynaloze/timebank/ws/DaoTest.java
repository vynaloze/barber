package com.vynaloze.timebank.ws;

import com.vynaloze.timebank.ws.dao.Dao;
import com.vynaloze.timebank.ws.dao.DaoImpl;
import com.vynaloze.timebank.ws.dao.ServiceAlreadyReservedException;
import com.vynaloze.timebank.ws.dao.ServiceNotFoundException;
import com.vynaloze.timebank.ws.pojo.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DaoTest {
    private Dao dao;
    private final Service service1 = new Service("Jules", LocalDate.now(), "Bible reading", "Say what again?");
    private final Service service2 = new Service("Vincent", LocalDate.now(), "Twist Dance", "Wanna dance?");

    @Before
    public void setUp() {
        dao = new DaoImpl();
    }

    @Test
    public void happyPath() {
        //when
        dao.postService(service1);
        //then
        assertThat(dao.getAll()).containsOnly(service1);
    }

    @Test
    public void shouldSetPrincipal() throws Exception {
        //given
        final String principal = "Mia";
        //when
        dao.postService(service2);
        dao.reserveService(service2.getId(), principal);
        //then
        assertThat(dao.getAll()).first().hasFieldOrPropertyWithValue("principal", principal);
    }

    @Test
    public void shouldDeleteService() throws Exception {
        //given
        dao.postService(service1);
        dao.postService(service2);
        //when
        dao.deleteService(service1.getId());
        //then
        assertThat(dao.getAll()).containsOnly(service2);
    }

    @Test
    public void shouldThrowExceptionOnNotFoundService() {
        //given
        dao.postService(service2);
        //then
        assertThatExceptionOfType(ServiceNotFoundException.class).isThrownBy(() -> dao.reserveService(service1.getId(), "User"));
        assertThatExceptionOfType(ServiceNotFoundException.class).isThrownBy(() -> dao.deleteService(service1.getId()));
    }

    @Test
    public void shouldThrowExceptionOnReservedService() throws Exception {
        //given
        dao.postService(service2);
        dao.reserveService(service2.getId(), "Butch ");
        //then
        assertThatExceptionOfType(ServiceAlreadyReservedException.class).isThrownBy(() -> dao.reserveService(service2.getId(), "Ringo"));
    }

    @After
    public void tearDown() {
        dao = null;
    }
}
