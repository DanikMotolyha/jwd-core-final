package com.epam.jwd.core_final.Controller;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.CreateMissionException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CreateMissionController {
    private static String missionName;
    private static String startDate;
    private static String endDate;
    private static Long distance;
    private static Spaceship ship;
    private static List<CrewMember> assignedCrew = new ArrayList<>();
    private static MissionResult missionResult = MissionResult.PLANNED;


    public static void start() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter mission name: ");
                missionName = scanner.nextLine();
                if (MissionServiceImpl.getInstance().findMissionByCriteria(
                        FlightMissionCriteria.builder().setMissionName(missionName).build()
                ).isPresent()) {
                    System.out.println("Mission with this name already exist");
                } else break;
            }
            while (true) {
                try {
                    System.out.println(String.format("Enter mission startDate (format %s): ", ApplicationProperties.getDateTimeFormat()));
                    startDate = scanner.nextLine();
                    String format = ApplicationProperties.getDateTimeFormat();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                            format
                    );
                    LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
                    if (startDateTime.isBefore(LocalDateTime.now())) {
                        System.out.println("date cannot be past");
                    } else break;
                } catch (Exception ex) {
                    System.out.println("incorrect format");
                }
            }
            while (true) {
                try {
                    System.out.println(String.format("Enter mission endDate (format %s): ", ApplicationProperties.getDateTimeFormat()));
                    endDate = scanner.nextLine();
                    String format = ApplicationProperties.getDateTimeFormat();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                            format
                    );
                    LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
                    LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
                    if (endDateTime.isBefore(LocalDateTime.now()) || startDateTime.isAfter(endDateTime)) {
                        System.out.println("date cannot be past or start date must be before end date");
                    } else break;
                } catch (Exception ex) {
                    System.out.println("incorrect format");
                }
            }
            while (true) {
                try {
                    System.out.println("Enter mission distance: ");
                    String str = scanner.nextLine();
                    distance = Long.valueOf(str);
                    break;
                } catch (Exception ex) {
                    System.out.println("incorrect format");
                }
            }
            while (true) {
                System.out.println("Enter mission spaceship(name): ");
                String name = scanner.nextLine();
                ship = SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(
                        SpaceshipCriteria
                                .builder()
                                .setName(name)
                                .build()).orElse(null);
                if (ship == null) {
                    System.out.println("ship with name " + name + " cannot find");
                } else if (distance > ship.getFlightDistance()) {
                    System.out.println("distance mission bigger than distance on ship");
                } else if(!ship.getReadyForNextMissions()){
                    System.out.println("ship dont ready for next mission");
                } else break;
            }
            ship.setReadyForNextMissions(false);
            assignedCrew.addAll(takeCrewTeam(ship));
            MissionServiceImpl.getInstance().createMission(
                    missionName,
                    startDate,
                    endDate,
                    distance,
                    ship,
                    assignedCrew);
        } catch (CreateMissionException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static List<CrewMember> takeCrewTeam(Spaceship ship) throws CreateMissionException {
        Map<Role, Short> crew = ship.getCrew();
        List<CrewMember> list = new ArrayList<>();
        for (Map.Entry<Role, Short> entry : crew.entrySet()) {
            List<CrewMember> members = CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(
                    CrewMemberCriteria
                            .builder()
                            .setRole(entry.getKey())
                            .setIsReadyForMission(true)
                            .build()
            );
            if (members.size() < entry.getValue()) {
                throw new CreateMissionException("Cannot create mission. Not enough free members");
            }
            for (int i = 0; i < entry.getValue(); i++) {
                members.get(i).setReadyForNextMissions(false);
                list.add(members.get(i));
            }
        }
        return list;
    }


}
