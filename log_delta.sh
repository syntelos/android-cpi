#!/bin/bash

if [ -n "${1}" ]&&[ -f "${1}" ]
then
    unset ts_prev
    for line in $(cat "${1}" | sed 's/ /%SP%/g')
    do
        line=$(echo "${line}" | sed 's/%SP%/ /g')
        ts_next=$(echo "${line}" | awk '{print $2}' | sed 's/.*[0-9][0-9]:[0-9][0-9]://')

        if [ -n "${ts_prev}" ]
        then
            bc_result=$(echo "${ts_next} - ${ts_prev}" | bc -l );
        else
            bc_result="0.0"
        fi

        printf "%2.4f\t%s\n" "${bc_result}" "${line}"

        ts_prev=${ts_next}
    done
else
    cat<<EOF>&2
usage
    $0 file.log
EOF
    exit 1
fi
