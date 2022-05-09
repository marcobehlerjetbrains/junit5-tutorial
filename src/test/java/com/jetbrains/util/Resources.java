package com.jetbrains.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Resources {

    public static List<Xml> toStrings(String pattern) {
        List<Xml> result = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph().scan()) {
            scanResult
                    .getResourcesMatchingPattern(Pattern.compile(pattern))
                    .forEachByteArrayIgnoringIOException((Resource res, byte[] fileContent) -> {
                        result.add(new Xml(res.getPath(), new String(fileContent, StandardCharsets.UTF_8)));
                    });
        }
        return result;
    }
}
