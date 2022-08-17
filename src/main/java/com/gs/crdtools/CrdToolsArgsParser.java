package com.gs.crdtools;

import io.vavr.collection.List;

import java.util.Arrays;

/**
 * A parser class for the CrdTools possible arguments.
 */
class CrdToolsArgsParser {

    static final String INPUT_ARG = "-i";
    static final String OUTPUT_ARG = "-o";
    static final String PACKAGE_ARG = "-p";
    public static final String DEFAULT_TARGET_PACKAGE = "com.gs.crdtools.generated";
    public static final String DEFAULT_OUTPUT = "generated.srcjar";

    /**
     * Parse the given arguments.
     * @param args The arguments to parse.
     */
    public static CrdToolsArgs parseArgs(String[] args) {
        int parseIndex = 0;
        List<String> crdPaths = List.empty();
        String packageName = DEFAULT_TARGET_PACKAGE;
        String outputPath = DEFAULT_OUTPUT;

        while (hasNext(args, parseIndex) != -1) {
            String currentArg = args[parseIndex];

            switch (currentArg) {
                case INPUT_ARG -> crdPaths = getCrdsList(args, parseIndex);
                case PACKAGE_ARG -> packageName = getCorrespondingValue(args, parseIndex);
                case OUTPUT_ARG -> outputPath = getCorrespondingValue(args, parseIndex);
            }
            parseIndex++;
        }
        return new CrdToolsArgs(crdPaths, packageName, outputPath);
    }

    /**
     * Find the next argument in the list and return its index.
     * Return -1 if there are no more arguments.
     * @param args The list of arguments to search.
     * @return The index of the next argument in the list, or -1 if none.
     */
    protected static int hasNext(String[] args, int index) {
        int i = index + 1;
        while (i < args.length) {
            if (args[i].startsWith("-")) {
                return i;
            } else if (i == args.length - 1) {
                return i + 1;
            }
            i++;
        }
        return -1;
    }

    /**
     * Get the values for the input argument.
     * @param args The list of arguments to search.
     * @return The values for the input argument.
     */
    protected static List<String> getCrdsList(String[] args, int index) {
        return List.of(Arrays.copyOfRange(args, index + 1, hasNext(args, index)));
    }

    /**
     * Get the value for the current argument; this only finds one successive value.
     * @param args The list of arguments to search.
     * @return The value for the current argument.
     */
    protected static String getCorrespondingValue(String[] args, int index) {
        return args[index + 1];
    }

}
