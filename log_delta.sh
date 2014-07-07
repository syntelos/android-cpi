#!/bin/bash

if [ -n "${1}" ]&&[ -f "${1}" ]
then
    unset ts_prev
    for line in $(cat "${1}" | sed 's/ /%SP%/g')
    do
        line=$(echo "${line}" | sed 's/%SP%/ /g')
        ts_next=$(echo "${line}" | sed 's/.*[0-9][0-9]:[0-9][0-9]://; s/ .*//')

        if [ -n "${ts_prev}" ]
        then
            printf -v ts_delt "%2.4f" $(echo "${ts_next} - ${ts_prev}" | bc -l )
        else
            printf -v ts_delt "%2.4f" 0.0
        fi
        echo "${ts_delt} ${line}"

        ts_prev=${ts_next}
    done
else
    cat<<EOF>&2
usage
    $0 file.log
EOF
    exit 1
fi
