#!/usr/bin/env bash
set -euo pipefail

echo "Java"
java -version
echo "Maven"
./mvnw -version
echo "Tests"
./mvnw test
