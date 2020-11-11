package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.strategy.FindEntityStrategy;
import com.epam.jwd.core_final.util.ShortReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public enum FindFlightMission implements FindEntityStrategy {
    INSTANCE;
    private final static Logger LOGGER = LoggerFactory.getLogger(FindFlightMission.class);
    @Override
    public void find() {
        try {
            Scanner scanner = new Scanner(System.in);
            FlightMissionCriteria criteria = new FlightMissionCriteria();
            boolean flag = true;
            while (flag) {
                short choice = ShortReader.read(
                        "choose criteria to search\n" +
                                "1 - Name\n" +
                                "2 - flightDistance\n" +
                                "3 - missionResult\n" +
                                "0 - find");
                switch (choice) {
                    case 0:
                        if (criteria.getMissionName() == null &&
                                criteria.getDistance() == null &&
                                criteria.getMissionResult() == null) {
                            System.out.println("You dont choose anything. cannot start find");
                        } else {
                            findByCriteria(criteria);
                            flag = false;
                        }
                        break;
                    case 1:
                        criteria.setMissionName(scanner.nextLine());
                        break;
                    case 2:
                        criteria.setDistance(Long.valueOf(scanner.nextLine()));
                        break;
                    case 3:
                        criteria.setMissionResult(MissionResult.valueOf(scanner.nextLine()));
                        break;
                }
                if (!flag) break;
                System.out.println("Criteria:\n" + criteria);
            }
        } catch (IllegalArgumentException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    private void findByCriteria(FlightMissionCriteria criteria) {
        List<FlightMission> members = MissionServiceImpl.getInstance().findAllMissionsByCriteria(criteria);
        if (members == null) {
            System.out.println("cannot find by this criteria");
        } else {
            System.out.println("Found: ");
            members.forEach(System.out::println);
        }
    }
}
