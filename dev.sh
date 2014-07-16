#!/bin/bash

case "${1}" in
u)
    set -x
    adb -d uninstall com.johnpritchard.cpi
    ;;
i)
    set -x
    adb -d logcat -c
    adb -d install bin/CPI-debug.apk
    adb -d logcat | tee /tmp/logcat | grep CPI
    ;;
*)
    cat<<EOF>&2
usage
  $0 [u|i]
EOF
    exit 1
esac
