load("@rules_junit5_external//:junit5.bzl", "java_junit5_test")

java_junit5_test(
    name = "tests",
    srcs = glob([
        "*Test.java",
    ]),
    deps = [
        "//src/main/java/com/gs/crdtools:spec-extractor",
        "//src/main/java/com/gs/crdtools:openapi-src-genner",
        "//src/main/java/com/gs/crdtools:gen-source-from-spec",
        "@maven//:io_vavr_vavr",
        "@bazel_tools//tools/java/runfiles"
    ],
    test_package = "com.gs.crdtools",
    size = "small",
    data = [
        "//src/test/resources:pet-store-yaml",
    ],
)
