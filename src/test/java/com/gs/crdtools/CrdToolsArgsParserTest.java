package com.gs.crdtools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrdToolsArgsParserTest {

    @Test
    void testParseArgs() {
        var args = new String[] {"-p", "com.gs.crdtools.generated", "-o", "generated.srcjar", "-i", "example_crd_1.yaml"};
        var argsParser = new CrdToolsArgsParser();
        argsParser.parseArgs(args);
        assertEquals("com.gs.crdtools.generated", argsParser.getArgs().getPackageName());
        assertEquals("generated.srcjar", argsParser.getArgs().getOutputPath());
        assertEquals(1, argsParser.getArgs().getCrdsPaths().size());
        assertEquals("example_crd_1.yaml", argsParser.getArgs().getCrdsPaths().get(0));
    }

    @Test
    void testParseMultipleInputs() {
        var args = new String[] {"-i", "example_crd_1.yaml", "example_crd_2.yaml"};
        var argsParser = new CrdToolsArgsParser();
        argsParser.parseArgs(args);
        assertEquals(2, argsParser.getArgs().getCrdsPaths().size());
        assertEquals("example_crd_1.yaml", argsParser.getArgs().getCrdsPaths().get(0));
        assertEquals("example_crd_2.yaml", argsParser.getArgs().getCrdsPaths().get(1));
    }

    @Test
    void testParseArgsWithDefault() {
        var args = new String[] {"-i", "example_crd_1.yaml"};
        var argsParser = new CrdToolsArgsParser();
        argsParser.parseArgs(args);
        assertEquals("com.gs.crdtools.generated", argsParser.getArgs().getPackageName());
        assertEquals("generated.srcjar", argsParser.getArgs().getOutputPath());
        assertEquals(1, argsParser.getArgs().getCrdsPaths().size());
        assertEquals("example_crd_1.yaml", argsParser.getArgs().getCrdsPaths().get(0));
    }

    @Test
    void testHasNextInputFirst() {
        var args = new String[] {"-i", "example_crd_1.yaml", "example_crd_2.yaml", "-p", "this.is.a.package"};
        var argsParser = new CrdToolsArgsParser();
        argsParser.parseIndex = 0;
        assertEquals(3, argsParser.hasNext(args));
    }

    @Test
    void testHasNextInputMiddle() {
        var args = new String[] {"-p", "this.is.a.package", "-i", "example_crd_1.yaml", "example_crd_2.yaml", "-o", "generated.srcjar"};
        var argsParser = new CrdToolsArgsParser();
        argsParser.parseIndex = 2;
        assertEquals(5, argsParser.hasNext(args));
    }

    @Test
    void testHasNextInputLast() {
        var args = new String[] {"-p", "this.is.a.package", "-i", "example_crd_1.yaml", "example_crd_2.yaml"};
        var argsParser = new CrdToolsArgsParser();
        argsParser.parseIndex = 2;
        assertEquals(5, argsParser.hasNext(args));
    }
}
