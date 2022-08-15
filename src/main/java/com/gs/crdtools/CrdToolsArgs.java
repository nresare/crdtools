package com.gs.crdtools;

import io.vavr.collection.List;

/**
 * A class listing all the possible arguments for the CRDTools tool.
 */
class CrdToolsArgs {

    List<String> crdsPaths;
    String packageName;
    String outputPath;

    /**
     * Set default values for all the arguments.
     */
    protected void populateDefault() {
        this.crdsPaths = List.empty();
        this.packageName = "com.gs.crdtools.generated";
        this.outputPath = "generated.srcjar";
    }

    /**
     * Set the package name for the generated source code.
     * @param packageName The package name for the generated source code.
     */
    protected void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Set the output path for the generated source code.
     * @param outputPath The output path for the generated source code.
     */
    protected void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Set the list of input CRDs to use for the generator.
     * @param crdsPaths The list of input CRDs to use for the generator.
     */
    protected void setCrdsPaths(String[] crdsPaths) {
        this.crdsPaths = List.of(crdsPaths);
    }

    /**
     * Get the list of input CRDs to use for the generator.
     * @return The list of input CRDs to use for the generator.
     */
    public List<String> getCrdsPaths() {
        return this.crdsPaths;
    }

    /**
     * Get the package name for the generated source code.
     * @return The package name for the generated source code.
     */
    public String getPackageName() {
        return this.packageName;
    }

    /**
     * Get the output path for the generated source code.
     * @return The output path for the generated source code.
     */
    public String getOutputPath() {
        return this.outputPath;
    }
}
