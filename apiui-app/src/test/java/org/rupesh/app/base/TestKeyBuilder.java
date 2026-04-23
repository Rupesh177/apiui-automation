package org.rupesh.app.base;

import org.testng.ITestResult;

import java.util.Arrays;

public class TestKeyBuilder {

    private TestKeyBuilder() {
    }

    public static String build(ITestResult result) {

        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();

        Object[] params = result.getParameters();
        String paramPart = (params != null && params.length > 0)
                ? Arrays.deepToString(params)
                : "no-params";

        return className + "#" + methodName + "#" + paramPart;
    }
}