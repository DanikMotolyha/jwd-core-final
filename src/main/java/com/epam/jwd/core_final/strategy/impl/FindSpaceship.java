package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.strategy.FindEntityStrategy;
import com.epam.jwd.core_final.util.ShortReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public enum FindSpaceship implements FindEntityStrategy {
    INSTANCE;
    private final static Logger LOGGER = LoggerFactory.getLogger(FindSpaceship.class);

    @Override
    public void find() {
        try {
        Scanner scanner = new Scanner(System.in);
        SpaceshipCriteria criteria = new SpaceshipCriteria();
        boolean flag = true;
        while (flag) {
            short choice = ShortReader.read(
                    "choose criteria to search\n" +
                            "1 - Name\n" +
                            "2 - flightDistance\n" +
                            "3 - isReadyForMission\n" +
                            "0 - find");
            switch (choice) {
                case 0:
                    if (criteria.getName() == null &&
                            criteria.getFlightDistance() == null &&
                            criteria.getReadyForMission() == null) {
                        System.out.println("You dont choose anything. cannot start find");
                    } else {
                        findByCriteria(criteria);
                        flag = false;
                    }
                    break;
                case 1:
                    criteria.setName(scanner.nextLine());
                    break;
                case 2:
                    criteria.setFlightDistance(Long.valueOf(scanner.nextLine()));
                    break;
                case 3:
                    criteria.setReadyForMission(Boolean.valueOf(scanner.nextLine()));
                    break;
            }
            if (!flag) break;
            System.out.println("Criteria:\n" + criteria);
        }}catch (NumberFormatException ex){
            LOGGER.error(ex.getMessage());
        }
    }

    private void findByCriteria(SpaceshipCriteria criteria) {
        List<Spaceship> members = SpaceshipServiceImpl.getInstance().findAllSpaceshipsByCriteria(criteria);
        if (members == null) {
            System.out.println("cannot find by this criteria");
        } else {
            System.out.println("Found: ");
            members.forEach(System.out::println);
        }
    }
}
