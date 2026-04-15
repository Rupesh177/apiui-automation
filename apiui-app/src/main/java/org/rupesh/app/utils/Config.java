package org.rupesh.app.utils;

public class Config {

    private Config() {
    }

    // -------------------------------
    // CORE RESOLVER
    // -------------------------------
    public static String get(String key, String defaultValue) {

        String value = System.getProperty(key);

        if (value == null || value.isBlank()) {
            value = System.getenv(key.toUpperCase().replace(".", "_"));
        }

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value.trim();
    }

    public static boolean getBoolean(String key, String defaultValue) {
        String value = get(key, defaultValue);
        return value.equalsIgnoreCase("true")
                || value.equalsIgnoreCase("1")
                || value.equalsIgnoreCase("yes");
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid integer config for key: " + key, e);
        }
    }

    // -------------------------------
    // ENVIRONMENT
    // -------------------------------
    public static String getEnv() {
        return get("env", "dev");
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
    // VAULT
    // -------------------------------
    public static boolean isVaultEnabled() {
        return getBoolean("vault.enabled", "false");
    }

    public static String getVaultUrl() {
        return get("vault.url", "http://localhost:8200");
    }

    public static String getVaultToken() {
        return get("vault.token", "");
    }

    public static String getVaultSecretPath() {
        return get("vault.secret.path", "/v1/secret/data/test");
    }

    // -------------------------------
    // JMeter
    // -------------------------------
    public static String getJMeterPath() {
        return get("jmeter.path", "jmeter");
    }

    public static String getJMeterTestPlan() {
        return get("jmeter.testplan", "test-plan.jmx");
    }

    public static String getJMeterResult() {
        return get("jmeter.result", "results.jtl");
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
    public static String getDriverType() {
        return get("driver", "selenium");
    }

    // -------------------------------
    // Retry
    // -------------------------------
    public static boolean isRetryEnabled() {
        return getBoolean("retry.enabled", "true");
    }

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