package input;

public class ArgumentsValidator {

    private final String[] args;

    public ArgumentsValidator(String[] args) {
        this.args = args;
    }

    public String getInputFile(String defaultFileName) {
        return resolveFilePath(0, defaultFileName, true);
    }

    public String getOutputFile(String defaultFilename) {
        return resolveFilePath(1, defaultFilename, false);
    }

    private String resolveFilePath(int index, String defaultFileName, boolean isInputFile) {
        if (args.length > index) {
            String filePath = args[index];
            if (isValidFilePath(filePath, isInputFile)) {
                logFilePath(filePath, isInputFile);
                return filePath;
            }
        } else {
            logDefaultFilePath(defaultFileName, isInputFile);
        }
        return defaultFileName;
    }

    private boolean isValidFilePath(String filePath, boolean isInputFile) {
        if (filePath.trim().isEmpty()) {
            System.err.println("Error: File name cannot be empty.");
            System.exit(1);
            return false;
        }

        if (isInputFile && getClass().getClassLoader().getResource(filePath) == null) {
            System.err.printf("Error: Input file '%s' not found.\n", filePath);
            System.exit(1);
            return false;
        }
        return true;
    }

    private void logFilePath(String filePath, boolean isInputFile) {
        String type = isInputFile ? "input" : "output";
        System.out.printf("Info: '%s' file will be used for %s.\n", filePath, type);
    }

    private void logDefaultFilePath(String defaultFileName, boolean isInputFile) {
        String type = isInputFile ? "input" : "output";
        System.out.printf("Info: No %s file specified. Using default '%s'.\n", type, defaultFileName);
    }
}
