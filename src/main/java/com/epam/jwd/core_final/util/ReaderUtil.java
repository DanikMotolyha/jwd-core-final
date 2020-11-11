package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.strategy.StrategyReadFileOperation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ReaderUtil {
    private StrategyReadFileOperation strategy;

    public ReaderUtil(StrategyReadFileOperation strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(StrategyReadFileOperation strategy) {
        this.strategy = strategy;
    }

    public List<List<String>> read(String fileName) throws InvalidStateException, IOException {
        return strategy.read(fileName);
    }
}
