#!/bin/bash
set -e

TEST_FILE="$KARAF_BASE/.config-was-copied"

if [ ! -f $TEST_FILE ]; then
   echo "File not found: $TEST_FILE. \nInitialize etc directory under $KARAF_BASE"
   cp -R /opt/karaf/etc $KARAF_BASE
   cp -R /opt/karaf/data $KARAF_BASE
   cp -R /opt/karaf/deploy $KARAF_BASE
   touch $TEST_FILE
fi

exec "$@"