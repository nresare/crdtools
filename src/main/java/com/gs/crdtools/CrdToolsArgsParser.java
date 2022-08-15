package com.gs.crdtools;

import java.util.Arrays;
import static io.vavr.API.*;

/**
 * A parser class for the CrdTools possible arguments.
 */
public class CrdToolsArgsParser {

    static final String INPUT_ARG = "-i";
    static final String OUTPUT_ARG = "-o";
    static final String PACKAGE_ARG = "-p";
    CrdToolsArgs args;
    int parseIndex;

    /**
     * When constructed, the parser will initialise all the arguments
     * with default values.
     */
    public CrdToolsArgsParser() {
        args = new CrdToolsArgs();
        args.populateDefault();
        parseIndex = 0;
    }

    /**
     * When constructed, the parser will initialise all the arguments
     * with the given values.
     * @param args The arguments to use for this instance of CRDTools.
     */
    public CrdToolsArgsParser(String[] args) {
        this.args = new CrdToolsArgs();
        parseArgs(args);
        parseIndex = 0;
    }

    /**
     * Parse the given arguments.
     * @param args The arguments to parse.
     */
    public void parseArgs(String[] args) {
        while (hasNext(args) != -1) {
            String currentArg = getArg(args);

            Match(currentArg).of(
                    Case($(INPUT_ARG), () -> run(() -> {
                        var _crds = getCrdsList(args);
                        this.args.setCrdsPaths(_crds);
                    })),
                    Case($(PACKAGE_ARG), () -> run(() -> {
                        var _packageName = getCorrespondingValue(args);
                        this.args.setPackageName(_packageName);
                    })),
                    Case($(OUTPUT_ARG), () -> run(() -> {
                        var _outputName = getCorrespondingValue(args);
                        this.args.setOutputPath(_outputName);
                    })),
                    // otherwise do nothing
                    Case($(), () -> run(() -> {}))
            );
            incrementParseIndex();
        }
    }

    /**
     * Find the next argument in the list and return its index.
     * Return -1 if there are no more arguments.
     * @param args The list of arguments to search.
     * @return The index of the next argument in the list, or -1 if none.
     */
    protected int hasNext(String[] args) {
        int i = parseIndex + 1;
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
     * Increment the parse index counter.
     */
    protected void incrementParseIndex() {
        this.parseIndex++;
    }

    /**
     * Get the current argument.
     * @param args The list of arguments to search.
     * @return The current argument.
     */
    protected String getArg(String[] args) {
        return args[this.parseIndex];
    }

    /**
     * Get the values for the input argument.
     * @param args The list of arguments to search.
     * @return The values for the input argument.
     */
    protected String[] getCrdsList(String[] args) {
        return Arrays.copyOfRange(args, this.parseIndex + 1, hasNext(args));
    }

    /**
     * Get the value for the current argument; this only finds one successive value.
     * @param args The list of arguments to search.
     * @return The value for the current argument.
     */
    protected String getCorrespondingValue(String[] args) {
        return args[parseIndex + 1];
    }

    /**
     * Get the parsed arguments.
     * @return The parsed arguments.
     */
    public CrdToolsArgs getArgs() {
        return this.args;
    }

}
