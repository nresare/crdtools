package com.gs.crdtools.codegen;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.gs.crdtools.codegen.OverrideTemplateLoader;
import io.swagger.codegen.v3.CodegenConfig;
import io.swagger.codegen.v3.templates.HandlebarTemplateEngine;

import java.io.IOException;
import java.util.Map;

// a customisation of HandlebarTemplateEngine that allows for side-loading certain templates
public class CustomOverrideTemplateEngine extends HandlebarTemplateEngine {
    private final CodegenConfig config;

    // copied from
    public CustomOverrideTemplateEngine(CodegenConfig config) {
        super(config);
        this.config = config;
    }

    // copied from https://github.com/swagger-api/swagger-codegen/blob/fcd24e736c513fbb5d08cd7362b01088b4942096/modules/swagger-codegen/src/main/java/io/swagger/codegen/v3/templates/HandlebarTemplateEngine.java
    @Override
    public String getRendered(String templateFile, Map<String, Object> templateData) throws IOException {
        Template hTemplate = this.getHandlebars(templateFile);
        return hTemplate.apply(templateData);
    }

    // copied from https://github.com/swagger-api/swagger-codegen/blob/fcd24e736c513fbb5d08cd7362b01088b4942096/modules/swagger-codegen/src/main/java/io/swagger/codegen/v3/templates/HandlebarTemplateEngine.java
    // with the change that OverrideTemplateLoader is used instead of CodegenTemplateLoader
    private Template getHandlebars(String templateFile) throws IOException {
        templateFile = templateFile.replace("\\", "/");
        String templateDir = this.config.templateDir().replace("\\", "/");
        String customTemplateDir = this.config.customTemplateDir() != null ? this.config.customTemplateDir().replace("\\", "/") : null;
        TemplateLoader templateLoader = (new OverrideTemplateLoader()).templateDir(templateDir).customTemplateDir(customTemplateDir);
        Handlebars handlebars = new Handlebars(templateLoader);
        handlebars.prettyPrint(true);
        this.config.addHandlebarHelpers(handlebars);
        return handlebars.compile(templateFile);
    }

}
