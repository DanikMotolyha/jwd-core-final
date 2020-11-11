package com.epam.jwd.core_final.Controller;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.strategy.UpdateStrategy;
import com.epam.jwd.core_final.strategy.impl.CrewMemberUpdate;
import com.epam.jwd.core_final.strategy.impl.FlightMissionUpdate;
import com.epam.jwd.core_final.strategy.impl.SpaceshipUpdate;
import com.epam.jwd.core_final.util.ShortReader;

import java.util.Scanner;

public class UpdateController {
    private static Short choice;

    public static void start() {
        choice = ShortReader.read("1 - Update crewMembers\n" +
                "2 - Update spaceship\n" +
                "3 - Update flightMission\n");
        switch (choice) {
            case 1:
                updateByType(CrewMember.class);
                break;
            case 2:
                updateByType(Spaceship.class);
                break;
            case 3:
                updateByType(FlightMission.class);
                break;
            default:
                System.out.println("Incorrect input!!!\n");
                break;
        }
    }

    private static <T extends BaseEntity> void updateByType(Class<T> tClass) {
        System.out.println("Enter entity name witch you want to update");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        UpdateStrategy strategy = null;
        if (CrewMember.class.getName().equals(tClass.getName())) {
            if (CrewServiceImpl.getInstance().findCrewMemberByCriteria(
                    CrewMemberCriteria
                            .builder()
                            .setName(name)
                            .build())
                    .isPresent())
                strategy = CrewMemberUpdate.INSTANCE;
        } else if (Spaceship.class.getName().equals(tClass.getName())) {
            if (SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(
                    SpaceshipCriteria
                            .builder()
                            .setName(name)
                            .build())
                    .isPresent())
                strategy = SpaceshipUpdate.INSTANCE;
        } else if (FlightMission.class.getName().equals(tClass.getName())) {
            if (MissionServiceImpl.getInstance().findMissionByCriteria(
                    FlightMissionCriteria
                            .builder()
                            .setMissionName(name)
                            .build())
                    .isPresent())
                strategy = FlightMissionUpdate.INSTANCE;
        }

        if (strategy == null) {
            System.out.println("Cannot find entity with name " + name);
        } else
            strategy.update(name);
    }
}
