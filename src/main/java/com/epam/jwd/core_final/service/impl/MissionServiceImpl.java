package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.ServiceException.CannotUpdateException;
import com.epam.jwd.core_final.exception.ServiceException.ServiceException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService {
    private final static MissionServiceImpl INSTANCE = new MissionServiceImpl();
    private final static Logger LOGGER = LoggerFactory.getLogger(MissionServiceImpl.class);

    private MissionServiceImpl() {

    }

    public static MissionServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return (List<FlightMission>) NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(FlightMissionCriteria criteria) {
        Collection<FlightMission> stream = new ArrayList<>(NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class));
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
        if (criteria.getMissionName() != null) {
            stream = stream.stream().filter(item ->
                    item.getMissionName().equals(criteria.getMissionName()))
                    .collect(Collectors.toList());
        }
        if (criteria.getDistance() != null) {
            stream = stream.stream().filter(item ->
                    item.getDistance().equals(criteria.getDistance()))
                    .collect(Collectors.toList());
        }
        if (criteria.getMissionResult() != null) {
            stream = stream.stream().filter(item ->
                    item.getMissionResult() == criteria.getMissionResult())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(stream);
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(FlightMissionCriteria criteria) {
        return findAllMissionsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public FlightMission updateFlightMissionDetails(String name, Long distance) {
        Optional<FlightMission> member = findMissionByCriteria(
                FlightMissionCriteria
                        .builder()
                        .setMissionName(name)
                        .build());
        if (member.isEmpty())
            throw new CannotUpdateException("Cannot find mission with name", name);
        FlightMission updatedMission = member.get();
        if (distance != null) {
            updatedMission.setDistance(distance);
        }
        return updatedMission;
    }

    @Override
    public FlightMission createMission(String missionName,
                                       String startDate,
                                       String endDate,
                                       Long distance,
                                       Spaceship assignedSpaceShift,
                                       List<CrewMember> assignedCrew) throws DateTimeParseException {
        if (findMissionByCriteria(
                FlightMissionCriteria
                        .builder()
                        .setMissionName(missionName)
                        .build())
                .isPresent()) {
            throw new ServiceException("Cannot create new object: duplicate name");
        }
        String format = ApplicationProperties.getDateTimeFormat();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                format
        );
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
        FlightMission mission = FlightMissionFactory.getInstance().create(
                Arrays.asList(missionName,
                        startDateTime,
                        endDateTime,
                        distance,
                        assignedSpaceShift,
                        assignedCrew)
        );
        NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class).add(mission);
        return mission;
    }
}
