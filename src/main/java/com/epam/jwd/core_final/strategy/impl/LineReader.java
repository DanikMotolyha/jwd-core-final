package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.strategy.StrategyReadFileOperation;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LineReader implements StrategyReadFileOperation {
    INSTANCE;
    private List<List<String>> entities;

    @Override
    public List<List<String>> read(String fileName) throws InvalidStateException, IOException {
        try (FileReader reader = new FileReader(
                ApplicationProperties.getInputRootDir() + "/" + fileName)) {
            Scanner scanner = new Scanner(reader);
            String firstParamLine = scanner.nextLine();
            if (firstParamLine.charAt(0) != '#')
                throw new InvalidStateException(
                        String.format("Incorrect first line in file %s (not find symbol #)", fileName));
            if (!firstParamLine.endsWith("..."))
                throw new InvalidStateException(
                        String.format("Incorrect first line in file %s (not find symbols ...)", fileName));
            int paramsCount = firstParamLine
                    .substring(1, firstParamLine.indexOf(';'))
                    .split(",")
                    .length;

            entities = Stream
                    .of(scanner.nextLine().split(";"))
                    .map(elem -> Stream.of(elem.split(",")).collect(Collectors.toList()))
                    .collect(Collectors.toList());
            for (List<String> params : entities) {
                if (params.size() != paramsCount)
                    throw new InvalidStateException(
                            String.format("Incorrect size of params in file %s", fileName));
            }
            /*for (List<String> a : entities) {
                for (String c : a) {
                    System.out.print(c + " ");
                }
                System.out.println(".");
            }*/
            return entities;
        }
    }

}
