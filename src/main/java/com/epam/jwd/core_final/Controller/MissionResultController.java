package com.epam.jwd.core_final.Controller;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResultController {
    public static void checkMissionsResult() {
        List<FlightMission> missionResults = MissionServiceImpl.getInstance().findAllMissions();
        missionResults.forEach(
                elem -> {
                    if (elem.getMissionResult() != MissionResult.FAILED ||
                            elem.getMissionResult() != MissionResult.CANCELLED) {
                        if (elem.getStartDate().isAfter(LocalDateTime.now())) {
                            elem.setMissionResult(MissionResult.IN_PROGRESS);
                            System.out.println("CHANGED TO IN PROGRESS");
                        }
                        if (elem.getEndDate().isBefore(LocalDateTime.now())) {
                            elem.setMissionResult(MissionResult.COMPLETED);
                            elem.getAssignedCrew().forEach(
                                    member -> member.setReadyForNextMissions(true)
                            );
                            elem.getAssignedSpaceShift().setReadyForNextMissions(true);
                            System.out.println("CHANGED TO COMPLETED");
                        }
                    }
                }
        );
    }
}
