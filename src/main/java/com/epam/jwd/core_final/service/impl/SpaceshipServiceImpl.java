package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.ServiceException.CannotBeAssignedToMissionException;
import com.epam.jwd.core_final.exception.ServiceException.CannotUpdateException;
import com.epam.jwd.core_final.exception.ServiceException.ServiceException;
import com.epam.jwd.core_final.exception.ServiceException.DuplicateObjectException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService {
    private final static SpaceshipService INSTANCE = new SpaceshipServiceImpl();
    private final static Logger LOGGER = LoggerFactory.getLogger(SpaceshipFactory.class);

    private SpaceshipServiceImpl() {

    }

    public static SpaceshipService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>) NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(SpaceshipCriteria criteria) {
        Collection<Spaceship> stream = new ArrayList<>(NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class));
        if (criteria.getId() != null) {
            stream = stream.stream().filter(item ->
                    item.getId().equals(criteria.getId()))
                    .collect(Collectors.toList());
        }
        if (criteria.getObjectName() != null) {
            stream = stream.stream().filter(item ->
                    item.getName().equals(criteria.getObjectName()))
                    .collect(Collectors.toList());
        }
        if (criteria.getName() != null) {
            stream = stream.stream().filter(item ->
                    item.getSpaceshipName().equals(criteria.getName()))
                    .collect(Collectors.toList());
        }
        if (criteria.getFlightDistance() != null) {
            stream = stream.stream().filter(item ->
                    item.getFlightDistance().equals(criteria.getFlightDistance()))
                    .collect(Collectors.toList());
        }
        if (criteria.getReadyForMission() != null) {
            stream = stream.stream().filter(item ->
                    item.getReadyForNextMissions().equals(criteria.getReadyForMission()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(stream);
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(SpaceshipCriteria criteria) {
        return findAllSpaceshipsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(String name, Long distance) {
        Optional<Spaceship> member = findSpaceshipByCriteria(
                SpaceshipCriteria
                        .builder()
                        .setName(name)
                        .build());
        if (member.isEmpty())
            throw new CannotUpdateException("Cannot find spaceship with name", name);
        Spaceship updatedShip = member.get();
        if (distance != null) {
            updatedShip.setFlightDistance(distance);
        }
        return updatedShip;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship ship) throws RuntimeException {
        if (!ship.getReadyForNextMissions())
            throw new CannotBeAssignedToMissionException("ship was assigned already", ship);
        ship.setReadyForNextMissions(false);
    }

    @Override
    public Spaceship createSpaceship(String name, Long distance, Map<Role, Short> crew) throws RuntimeException {
        if (findSpaceshipByCriteria(SpaceshipCriteria.builder().setName(name).build()).isPresent()) {
            throw new ServiceException("Cannot create new object: duplicate name");

        }
        List<String> crewMembers = new ArrayList<>();
        crew.forEach(
                (elem, key) -> crewMembers.add(elem.getId() + ":" + key)
        );
        Spaceship ship = SpaceshipFactory.getInstance().create(
                Arrays.asList(
                        name, distance.toString(),
                        crewMembers.get(0),
                        crewMembers.get(1),
                        crewMembers.get(2),
                        crewMembers.get(3))
                );
        NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class).add(ship);
        return ship;
    }

    @Override
    public List<? super Spaceship> createAllSpaceship(List<List<String>> parameters) {
        for (List<String> params : parameters) {
            try {
                Optional<Spaceship> spaceship = Optional.ofNullable(SpaceshipFactory.getInstance().create(params));
                if (spaceship.isPresent()) {
                    if (findSpaceshipByCriteria(
                            SpaceshipCriteria
                                    .builder()
                                    .setName(spaceship
                                            .get()
                                            .getSpaceshipName())
                                    .build()
                    ).isPresent()) {
                        throw new DuplicateObjectException("Duplicate :", spaceship.get());
                    }
                    NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class).add(spaceship.get());
                }
            } catch (DuplicateObjectException ex) {
                LOGGER.error(ex.getMessage());
            }

        }
        return (List<? super Spaceship>) NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class);
    }
}
