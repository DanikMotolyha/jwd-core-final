package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface SpaceshipService {

    List<Spaceship> findAllSpaceships();

    List<Spaceship> findAllSpaceshipsByCriteria(SpaceshipCriteria criteria);

    Optional<Spaceship> findSpaceshipByCriteria(SpaceshipCriteria criteria);

    Spaceship updateSpaceshipDetails(String name, Long distance);

    // todo create custom exception for case, when spaceship is not able to be assigned
    void assignSpaceshipOnMission(Spaceship crewMember) throws RuntimeException;

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // spaceship unique criteria - only name!
    Spaceship createSpaceship(String name, Long distance, Map<Role, Short> crew) throws RuntimeException;

    List<? super Spaceship> createAllSpaceship(List<List<String>> parameters);
}
