package com.epam.jwd.core_final.Controller;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class StatisticMissionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(StatisticMissionController.class);

    public static void start() {
        try {
            long duration;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter mission name for take statistic:");
            String name = scanner.nextLine();
            FlightMission mission = MissionServiceImpl.getInstance().findMissionByCriteria(
                    FlightMissionCriteria
                            .builder()
                            .setMissionName(name)
                            .build()).orElse(null);
            if (mission == null) {
                System.out.println("Cannot find mission: mission with this name does not exist");
            } else {
                Long distance = mission.getDistance();
                System.out.println("Enter statistics time(in seconds):");
                duration = Long.parseLong(scanner.nextLine());
                System.out.println(String.format("Mission name: %s\n SpaceShip name : %s total mission distance : %d",
                        mission.getMissionName(), mission.getAssignedSpaceShift().getName(), mission.getDistance()));
                Duration durationTime = Duration.between(mission.getStartDate(), mission.getEndDate());
                long passedTime;
                long percentCompleted;
                String msg;
                for (int i = 0; i < duration; i++) {
                    if (mission.getStartDate().isAfter(LocalDateTime.now()))
                        msg = "Mission dont start";
                    else if (mission.getEndDate().isBefore(LocalDateTime.now())) {
                        msg = "Mission end";
                    } else {
                        passedTime = Duration.between(mission.getStartDate(), LocalDateTime.now()).getSeconds();
                        percentCompleted = passedTime / (durationTime.getSeconds() / 100);
                        msg = percentCompleted + "%";
                    }
                    System.out.println(String.format("%s [ %s ] %s", mission.getStartDate(), msg, mission.getEndDate()));
                    Thread.sleep(1000);
                }
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
