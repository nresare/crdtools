package com.gs.crdtools;

import com.resare.nryaml.YAMLMapping;
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

        var parsed = CrdToolsArgs.parseArgs(args);

        var crdsList = parseCrds(parsed.crdPaths().map(Path::of));
        var result = generate(crdsList, parsed.packageName());

        toZip(result, Path.of(parsed.outputPath()));
    }

    static List<YAMLMapping> parseCrds(List<Path> inputs) {
        return inputs.flatMap(YAMLUtil::allFromPath).map(YAMLValue::asMapping);
    }

    static Map<Path, String> generate(List<YAMLMapping> crds, String packageName) throws IOException {
        var spec = SpecExtractorHelper.createSpec(crds);
        return SourceGenFromSpec.generateSource(spec, packageName);
    }

}
