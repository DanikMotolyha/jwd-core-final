package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.strategy.StrategyReadFileOperation;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ColumnReader implements StrategyReadFileOperation {
    INSTANCE;
    private List<List<String>> entities = new ArrayList<>();

    @Override
    public List<List<String>> read(String fileName) throws InvalidStateException, IOException {
        try (FileReader reader = new FileReader(
                ApplicationProperties.getInputRootDir() + "/" + fileName)) {
            int paramsCount;
            Scanner scanner = new Scanner(reader);
            paramsCount = readParamInfo(scanner, fileName);
            while (scanner.hasNextLine()) {
                entities.add(Stream
                        .of(scanner.nextLine()
                                .replaceAll("[{}]", "")
                                .split("[,;]"))
                        .collect(Collectors.toList())
                );
            }
            /*for (List<String> a : entities) {
                for (String c : a) {
                    System.out.print(c + " ");
                }
                System.out.println(".");
            }*/
            for (List<String> params : entities) {
                if (params.size() != paramsCount)
                    throw new InvalidStateException(
                            String.format("Incorrect size of params in file %s", fileName));
            }
            return entities;
        }
    }

    private int readParamInfo(Scanner scanner, String fileName) throws InvalidStateException {
        int paramsCount = 0;
        for (int i = 1; i <= 3; i++) {
            String paramString = scanner.nextLine();
            if (paramString.charAt(0) != '#')
                throw new InvalidStateException(
                        String.format("Incorrect params line in file %s : line %d" +
                                "(not find symbol #)", fileName, i));
            switch (i) {
                case 1:
                    paramsCount = paramString
                            .substring(1, paramString.indexOf('}'))
                            .split("[;,{}]")
                            .length;
                    paramsCount -= paramString.replaceAll("[^{]", "").length();
                    if (paramsCount == 0) {
                        throw new InvalidStateException(
                                String.format("Incorrect first line in input file %s " +
                                        "(number of parameters can not be 0)", fileName)
                        );
                    }
                    break;
                case 3:
                    if (!paramString.endsWith("..."))
                        throw new InvalidStateException(
                                String.format("Incorrect first line in file %s " +
                                        "(not find symbols ...)", fileName));
                    break;
            }
        }
        return paramsCount;
    }
}
