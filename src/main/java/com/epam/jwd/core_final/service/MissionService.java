package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MissionService {

    List<FlightMission> findAllMissions();

    List<FlightMission> findAllMissionsByCriteria(FlightMissionCriteria criteria);

    Optional<FlightMission> findMissionByCriteria(FlightMissionCriteria criteria);

    FlightMission updateFlightMissionDetails(String name, Long distance);

    FlightMission createMission(String missionName,
                                String startDate,
                                String endDate,
                                Long distance,
                                Spaceship assignedSpaceShift,
                                List<CrewMember> assignedCrew);
}
