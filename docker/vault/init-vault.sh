#!/bin/sh
set -e

echo "Waiting for Vault..."

until curl -s http://vault:8200/v1/sys/health >/dev/null 2>&1; do
  sleep 2
done

echo "Vault is up. Seeding secrets..."

export VAULT_ADDR=http://vault:8200
export VAULT_TOKEN=my-root-token

vault kv put secret/test \
  db.password=password123 \
  jira.token=jira-secret-token \
  db.user=root \
  db.url=jdbc:mysql://mysql:3306/test

echo "Vault secrets seeded successfully."