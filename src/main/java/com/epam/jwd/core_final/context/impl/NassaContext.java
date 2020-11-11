package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.strategy.impl.ColumnReader;
import com.epam.jwd.core_final.strategy.impl.LineReader;
import com.epam.jwd.core_final.util.ReaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {
    private static NassaContext INSTANCE = new NassaContext();
    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<FlightMission> missions = new ArrayList<>();

    private NassaContext() {
    }

    public static NassaContext getInstance() {
        return INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if (CrewMember.class.getName().equals(tClass.getName())) {
            return (Collection<T>) crewMembers;
        } else if (Spaceship.class.getName().equals(tClass.getName())) {
            return (Collection<T>) spaceships;
        } else if (FlightMission.class.getName().equals(tClass.getName())) {
            return (Collection<T>) missions;
        }
        return null;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException, IOException {
        ReaderUtil reader = new ReaderUtil(
                LineReader.INSTANCE
        );
        CrewServiceImpl.getInstance().createAllCrewMember(
                reader.read(ApplicationProperties.getCrewFileName())
        );
        reader.setStrategy(ColumnReader.INSTANCE);
        SpaceshipServiceImpl.getInstance().createAllSpaceship(
                reader.read(ApplicationProperties.getSpaceshipsFileName())
        );
    }
}
