package com.gs.crdtools.codegen;

import io.swagger.codegen.v3.templates.CodegenTemplateLoader;

import java.nio.file.Path;

public class OverrideTemplateLoader extends CodegenTemplateLoader {
    private static final String OVERRIDE_CLASSPATH_PREFIX = "swaggerTemplateOverloads";

    @Override
    public String resolve(String uri) {
        if (!uri.endsWith(this.getSuffix())) {
            uri = uri + this.getSuffix();
        }

        var overridePath = Path.of("/", OVERRIDE_CLASSPATH_PREFIX, uri).toString();
        var resource = this.getClass().getClassLoader().getResource(overridePath);
        if (resource != null) {
            return overridePath;
        }

        var val = super.resolve(uri);
        return val;
    }

}
