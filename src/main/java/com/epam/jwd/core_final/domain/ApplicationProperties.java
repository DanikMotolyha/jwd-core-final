package com.epam.jwd.core_final.domain;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {
    //todo
    private static ApplicationProperties instance = null;
    private static String inputRootDir = null;
    private static String outputRootDir = null;
    private static String crewFileName = null;
    private static String missionsFileName = null;
    private static String spaceshipsFileName = null;
    private static Integer fileRefreshRate = null;
    private static String dateTimeFormat = null;


    private ApplicationProperties() {

    }

    private static void InitProperties(
            String input, String output, String crewFile,
            String missionsFile, String spaceshipsFile,
            Integer fileRefresh, String dateFormat) {
        inputRootDir = input;
        outputRootDir = output;
        crewFileName = crewFile;
        missionsFileName = missionsFile;
        spaceshipsFileName = spaceshipsFile;
        fileRefreshRate = fileRefresh;
        dateTimeFormat = dateFormat;
    }

    public static void init(String input, String output, String crewFile,
                            String missionsFile, String spaceshipsFile,
                            Integer fileRefresh, String dateFormat) {
        if (instance == null) {
            instance = new ApplicationProperties();
            InitProperties(
                    input, output, crewFile,
                    missionsFile, spaceshipsFile,
                    fileRefresh, dateFormat);
        }
    }

    public static String getInputRootDir() {
        return inputRootDir;
    }

    public static String getOutputRootDir() {
        return outputRootDir;
    }

    public static String getCrewFileName() {
        return crewFileName;
    }

    public static String getMissionsFileName() {
        return missionsFileName;
    }

    public static String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public static Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public static String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
