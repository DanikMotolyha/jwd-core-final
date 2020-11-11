package com.epam.jwd.core_final.Controller;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.strategy.FindEntityStrategy;
import com.epam.jwd.core_final.strategy.impl.FindCrewMember;
import com.epam.jwd.core_final.strategy.impl.FindFlightMission;
import com.epam.jwd.core_final.strategy.impl.FindSpaceship;
import com.epam.jwd.core_final.util.ShortReader;

public class FindController {
    private static Short choice;

    public static void start() {
        choice = ShortReader.read("1 - Show crewMembers\n" +
                "2 - Show spaceships\n" +
                "3 - Show flightMissions\n");
        switch (choice) {
            case 1:
                showByType(CrewMember.class);
                break;
            case 2:
                showByType(Spaceship.class);
                break;
            case 3:
                showByType(FlightMission.class);
                break;
            default:
                System.out.println("Incorrect input!!!\n");
                break;
        }
    }

    private static <T extends BaseEntity> void showByType(Class<T> tClass) {
        choice = ShortReader.read("1 - ALL\n" +
                "2 - By criteria");
        switch (choice) {
            case 1:
                if (NassaContext.getInstance()
                        .retrieveBaseEntityList(tClass).isEmpty())
                    System.out.println("Collection is empty");
                else {
                    NassaContext.getInstance()
                            .retrieveBaseEntityList(tClass).forEach(System.out::println);
                }
                break;
            case 2:
                showByTypeCriteria(tClass);
                break;
        }
    }

    private static <T extends BaseEntity> void showByTypeCriteria(Class<T> tClass) {
        FindEntityStrategy strategy = null;
        if (CrewMember.class.getName().equals(tClass.getName())) {
            strategy = FindCrewMember.INSTANCE;
        } else if (Spaceship.class.getName().equals(tClass.getName())) {
            strategy = FindSpaceship.INSTANCE;
        } else if (FlightMission.class.getName().equals(tClass.getName())) {
            strategy = FindFlightMission.INSTANCE;
        }
        if (strategy != null)
            strategy.find();
    }
}
