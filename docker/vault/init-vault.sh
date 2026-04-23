#!/bin/sh
set -e

echo "Waiting for Vault..."

until curl -s http://vault:8200/v1/sys/health >/dev/null 2>&1; do
  sleep 2
done

echo "Vault is up. Seeding secrets..."

export VAULT_ADDR=http://vault:8200
export VAULT_TOKEN=my-root-token

# -------------------------------
# DEV
# -------------------------------
vault kv put secret/dev/apiui \
  db.password=dev_password \
  db.user=root \
  db.url=jdbc:mysql://dev-db:3306/test \
  jira.token=dev-jira-token

# -------------------------------
# STAGE
# -------------------------------
vault kv put secret/stage/apiui \
  db.password=stage_password \
  db.user=root \
  db.url=jdbc:mysql://stage-db:3306/test \
  jira.token=stage-jira-token

# -------------------------------
# PILOT
# -------------------------------
vault kv put secret/pilot/apiui \
  db.password=pilot_password \
  db.user=root \
  db.url=jdbc:mysql://pilot-db:3306/test \
  jira.token=pilot-jira-token

# -------------------------------
# PROD
# -------------------------------
vault kv put secret/prod/apiui \
  db.password=prod_password \
  db.user=root \
  db.url=jdbc:mysql://prod-db:3306/test \
  jira.token=prod-jira-token

echo "Vault secrets seeded successfully."