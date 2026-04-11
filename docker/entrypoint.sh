#!/bin/bash

set -e

echo "🚀 Starting Test Execution..."

BROWSER=${BROWSER:-chrome}
HEADLESS=${HEADLESS:-true}
REMOTE=${REMOTE:-true}
GRID_URL=${GRID_URL:-http://selenium-hub:4444/wd/hub}
SUITE=${SUITE:-testng.xml}
USE_TIA=${USE_TIA:-true}
DB_MIGRATE=${DB_MIGRATE:-true}

# -------------------------------
# WAIT FOR SELENIUM GRID
# -------------------------------
echo "⏳ Waiting for Selenium Grid..."
until curl -s "$GRID_URL/status" | grep -q "ready"; do
  sleep 2
done
echo "✅ Grid Ready"

# -------------------------------
# DB MIGRATION (Flyway)
# -------------------------------
if [ "$DB_MIGRATE" = "true" ]; then
  echo "🛢️ Running DB migration..."

  mvn flyway:migrate \
    -Dflyway.url=$DB_URL \
    -Dflyway.user=$DB_USER \
    -Dflyway.password=$DB_PASS \
    -Ddb.url=$DB_URL \
    -Ddb.user=$DB_USER \
    -Ddb.password=$DB_PASS \
    || { echo "❌ Flyway migration failed"; exit 1; }

  echo "✅ DB Migration completed"
else
  echo "⏭️ Skipping DB migration"
fi

# -------------------------------
# ALLURE HISTORY (if mounted)
# -------------------------------
if [ -d "/app/allure-history" ]; then
  echo "📊 Restoring Allure history..."
  mkdir -p /app/allure-results/history
  cp -r /app/allure-history/* /app/allure-results/history || true
fi

# -------------------------------
# CLEAN
# -------------------------------
rm -rf /app/target/*
rm -rf /app/allure-results/*

# -------------------------------
# RUN TESTS (TIA ENABLED)
# -------------------------------
echo "🧪 Running tests..."

if [ "$USE_TIA" = "true" ] && [ -f "changed-files.txt" ]; then
  echo "📂 Using Test Impact Analysis..."

  mvn exec:java \
    -Dexec.mainClass="runner.DynamicTestNGRunner" \
    -Dbrowser=$BROWSER \
    -Dheadless=$HEADLESS \
    -Dremote=$REMOTE \
    -Dgrid.url=$GRID_URL \
    -Dallure.results.directory=/app/allure-results \
    || TEST_EXIT_CODE=$?

else
  echo "📦 Running full test suite..."

  mvn clean test \
    -DsuiteXmlFile=$SUITE \
    -Dbrowser=$BROWSER \
    -Dheadless=$HEADLESS \
    -Dremote=$REMOTE \
    -Dgrid.url=$GRID_URL \
    -Dallure.results.directory=/app/allure-results \
    || TEST_EXIT_CODE=$?
fi

# -------------------------------
# GENERATE REPORT
# -------------------------------
echo "📊 Generating Allure report..."
mvn allure:report || true

# -------------------------------
# SAVE HISTORY
# -------------------------------
if [ -d "/app/target/site/allure-maven-plugin/history" ]; then
  echo "💾 Saving Allure history..."
  mkdir -p /app/allure-history
  cp -r /app/target/site/allure-maven-plugin/history/* /app/allure-history || true
fi

# -------------------------------
# EXIT CODE
# -------------------------------
if [ -z "$TEST_EXIT_CODE" ]; then
  TEST_EXIT_CODE=0
fi

echo "🏁 Finished with exit code $TEST_EXIT_CODE"

exit $TEST_EXIT_CODE