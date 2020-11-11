package com.epam.jwd.core_final.strategy;

import com.epam.jwd.core_final.exception.InvalidStateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface StrategyReadFileOperation {
    List<List<String>> read(String FileName) throws InvalidStateException, IOException;
}
