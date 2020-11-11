package com.epam.jwd.core_final.Controller;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;

import java.util.Scanner;

public class CancelMissionController {
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter mission name: ");
        String missionName = scanner.nextLine();
        FlightMission mission = MissionServiceImpl.getInstance().findMissionByCriteria(
                FlightMissionCriteria
                        .builder()
                        .setMissionName(missionName)
                        .build()
        ).orElse(null);
        if(mission == null){
            System.out.println("Cannot cancel mission: mission with this name does not exist");
        } else {
            mission.setMissionResult(MissionResult.CANCELLED);
            mission.getAssignedCrew().forEach(
                    member -> member.setReadyForNextMissions(true)
            );
            mission.getAssignedSpaceShift().setReadyForNextMissions(true);
        }
    }
}
