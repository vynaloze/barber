package com.vynaloze.timebank.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RoleTest {
    @Test
    public void shouldResolveRole() {
        //given
        String principalRole = "R";
        String contractorRole = "contr";
        //when
        User.Role assumedPrincipal = User.Role.getRole(principalRole);
        User.Role assumedContractor = User.Role.getRole(contractorRole);
        //then
        assertThat(assumedPrincipal.equals(User.Role.PRINCIPAL)).isTrue();
        assertThat(assumedContractor.equals(User.Role.CONTRACTOR)).isTrue();
    }

    @Test
    public void shouldThrowExceptionOnWrongString() {
        //given
        String badString = "BADDIE";
        //then
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> User.Role.getRole(badString));
    }
}
