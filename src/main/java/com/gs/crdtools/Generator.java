package com.gs.crdtools;

import com.resare.nryaml.YAMLUtil;
import com.resare.nryaml.YAMLValue;
import io.vavr.collection.List;
import io.vavr.collection.Map;

import java.io.IOException;
import java.nio.file.Path;

import static com.gs.crdtools.SourceGenFromSpec.toZip;

public class Generator {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: Generator -o GENERATED_SRC_ZIP -p PACKAGE_NAME -i CRD_YAML [CRD_YAML...]");
        }

        var argsParser = new CrdToolsArgsParser();
        argsParser.parseArgs(args);

        var crdsList = parseCrds(argsParser.getArgs().getCrdsPaths().map(Path::of));
        var result = generate(crdsList, argsParser.getArgs().getPackageName());

        toZip(result, Path.of(argsParser.getArgs().getOutputPath()));
    }

    static List<YAMLValue> parseCrds(List<Path> inputs) {
        return inputs.flatMap(YAMLUtil::allFromPath);
    }

    static Map<Path, String> generate(List<YAMLValue> crds, String packageName) throws IOException {
        var specs = SourceGenFromSpec.extractSpecs(crds);
        return SourceGenFromSpec.generateSourceCodeFromSpecs(specs, packageName);
    }

}
