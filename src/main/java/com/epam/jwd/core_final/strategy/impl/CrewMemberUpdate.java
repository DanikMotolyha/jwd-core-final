package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.strategy.UpdateStrategy;
import com.epam.jwd.core_final.util.ShortReader;

import java.util.Scanner;

public enum CrewMemberUpdate implements UpdateStrategy {
    INSTANCE;

    @Override
    public void update(String name) {
        Role role = null;
        Rank rank = null;
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            short choice = ShortReader.read(
                    "choose what to change\n" +
                            "1 - role\n" +
                            "2 - rank\n" +
                            "0 - update");
            switch (choice) {
                case 0:
                    if (role == null &&
                            rank == null) {
                        System.out.println("You dont choose anything. cannot update");
                    } else {
                        CrewServiceImpl.getInstance().updateCrewMemberDetails(role, name, rank);
                        flag = false;
                    }
                    break;
                case 1:
                    role = Role.valueOf(scanner.nextLine());
                    break;
                case 2:
                    rank = Rank.valueOf(scanner.nextLine());
                    break;
            }
            if (!flag) break;
            System.out.println("--- rank " + rank +
                    "\n--- role " + role);
        }
    }
}
