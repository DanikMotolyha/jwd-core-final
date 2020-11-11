package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.epam.jwd.core_final.strategy.UpdateStrategy;
import com.epam.jwd.core_final.util.ShortReader;

import java.util.Scanner;

public enum FlightMissionUpdate implements UpdateStrategy {
    INSTANCE;

    @Override
    public void update(String name) {
        Long distance = null;
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            short choice = ShortReader.read(
                    "choose what to change\n" +
                            "1 - distance\n" +
                            "0 - update");
            switch (choice) {
                case 0:
                    if (distance == null) {
                        System.out.println("You dont choose anything. cannot update");
                    } else {
                        MissionServiceImpl.getInstance().updateFlightMissionDetails(name, distance);
                        flag = false;
                    }
                    break;
                case 1:
                    distance = Long.valueOf(scanner.nextLine());
                    break;
            }
            if (!flag) break;
            System.out.println("--- distance " + distance);
        }
    }
}
