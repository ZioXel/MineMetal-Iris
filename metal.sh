#!/usr/bin/env bash
# MineMetal-Iris helper:  ./metal.sh build   -> builds iris-fabric jar and installs it into ../MineMetal/run/mods-stage0
set -o pipefail
cd "$(dirname "$0")"
export JAVA_HOME="${JAVA_HOME:-/opt/homebrew/opt/openjdk@25/libexec/openjdk.jdk/Contents/Home}"
LOG=../MineMetal/.reference/iris-build.log
case "${1:-build}" in
  build)
    ./gradlew :fabric:build -x test 2>&1 | tee "$LOG" || { echo "BUILD FAILED (log: $LOG)"; exit 1; }
    jar=$(ls -t fabric/build/libs/iris-fabric-*.jar | grep -v -- '-sources' | head -1)
    dest=../MineMetal/run/mods-stage0
    mkdir -p ../MineMetal/run/iris-upstream
    for old in "$dest"/iris-fabric-*.jar; do [ -e "$old" ] && mv "$old" ../MineMetal/run/iris-upstream/; done
    cp "$jar" "$dest/"
    echo "== installed $(basename "$jar") into $dest (original Iris moved to run/iris-upstream)" | tee -a "$LOG" ;;
  *) echo "usage: ./metal.sh build"; exit 2 ;;
esac
