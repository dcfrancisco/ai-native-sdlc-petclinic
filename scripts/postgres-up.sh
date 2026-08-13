#!/usr/bin/env bash
set -euo pipefail

export POSTGRES_PORT="${POSTGRES_PORT:-55432}"
echo "Starting PostgreSQL on host port ${POSTGRES_PORT}"
docker compose up -d postgres
docker compose ps postgres
