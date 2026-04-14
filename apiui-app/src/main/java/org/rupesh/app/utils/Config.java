package org.rupesh.app.utils;

import java.util.Optional;

public class Config {

    // -------------------------------
    // CORE RESOLVER
    // -------------------------------
    private static String get(String key, String defaultValue) {
        return Optional.ofNullable(System.getProperty(key))
                .orElseGet(() -> System.getenv().getOrDefault(key.toUpperCase().replace(".", "_"), defaultValue));
    }

    private static boolean getBoolean(String key, String defaultValue) {
        return Boolean.parseBoolean(get(key, defaultValue));
    }

    // -------------------------------
    // API
    // -------------------------------
    public static String getBaseUrl() {
        return get("baseUrl", "https://api.makemytrip.com");
    }

    // -------------------------------
    // Get TestData URL
    // -------------------------------
    public static String getTestDataUrl() {
        return get("testdata.url", "http://localhost:8081");
    }

    // -------------------------------
    // DB
    // -------------------------------
    public static String getDbUrl() {
        return get("db.url", "jdbc:mysql://localhost:3306/test");
    }

    public static String getDbUser() {
        return get("db.user", "root");
    }

    public static String getDbPassword() {
        return get("db.password", "password");
    }

    public static boolean shouldRunMigration() {
        return getBoolean("db.migrate", "true");
    }

    // -------------------------------
    // KAFKA
    // -------------------------------
    public static String getKafkaUrl() {
        return get("kafka.url", "localhost:9092");
    }

    // -------------------------------
    // FEATURE FLAGS
    // -------------------------------
    public static String getFeatureFlagUrl() {
        return get("feature.flag.url", "http://localhost:8090");
    }

    // -------------------------------
    // Grafana
    // -------------------------------
    public static boolean isMetricsEnabled() {
        return getBoolean("metrics.enabled", "true");
    }

    public static int getMetricsPort() {
        return Integer.parseInt(get("metrics.port", "9090"));
    }

    // -------------------------------
    // JIRA
    // -------------------------------
    public static boolean isJiraEnabled() {
        return getBoolean("jira.enabled", "false");
    }

    public static String getJiraUrl() {
        return get("jira.url", "");
    }

    public static String getJiraToken() {
        return get("jira.token", "");
    }

    public static String getJiraProjectKey() {
        return get("jira.project", "QA");
    }

    // -------------------------------
    // RETRY / STABILITY
    // -------------------------------
    public static int getMaxRetry() {
        return Integer.parseInt(get("retry.count", "2"));
    }

    // -------------------------------
    // AI Flag
    // -------------------------------
    public static boolean isAiEnabled() {
        return getBoolean("ai.enabled", "false");
    }

    // -------------------------------
    // UI / DRIVER
    // -------------------------------
    public static String getBrowser() {
        return get("browser", "chrome");
    }

    public static boolean isRemote() {
        return getBoolean("remote", "false");
    }

    public static boolean isHeadless() {
        return getBoolean("headless", "true");
    }

    public static boolean isHealingEnabled() {
        return getBoolean("healing", "false");
    }

    public static String getGridUrl() {
        return get("grid.url", "http://localhost:4444/wd/hub");
    }
}