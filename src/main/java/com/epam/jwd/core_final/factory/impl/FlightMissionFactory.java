package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.FactoryExceptions.InvalidFactoryParamsAmount;
import com.epam.jwd.core_final.factory.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class FlightMissionFactory implements EntityFactory<FlightMission> {
    private final static FlightMissionFactory INSTANCE = new FlightMissionFactory();
    private final static Logger LOGGER = LoggerFactory.getLogger(FlightMissionFactory.class);

    private FlightMissionFactory() {

    }

    public static FlightMissionFactory getInstance() {
        return INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FlightMission create(List<?> params) {
        try {
            if(params.size() != 6)
                throw new InvalidFactoryParamsAmount("params amount not 6");
            String missionName = params.get(0).toString();
            LocalDateTime startDate = (LocalDateTime) params.get(1);
            LocalDateTime endDate = (LocalDateTime) params.get(2);
            Long distance = (Long) params.get(3);
            Spaceship spaceship = (Spaceship) params.get(4);
            List<CrewMember> assignedCrew = (List<CrewMember>) params.get(5);
            return new FlightMission(
                    missionName,
                    startDate,
                    endDate,
                    distance,
                    spaceship,
                    assignedCrew
            );
        } catch (InvalidFactoryParamsAmount ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
}
