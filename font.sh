#!/bin/bash

export progn=$0
export scrd=$(cd $(dirname $0); pwd)
export srcd=${scrd}/tools
export tgtd=${scrd}/src/com/johnpritchard/cpi

export tgtn="View2DFontFutural"

function fv3_jar {
    root_dir=${HOME}/src
    if [ -n "${root_dir}" ]&&[ -d "${root_dir}" ]
    then
        fv3_dir=${root_dir}/syntelos/fv3
        if [ -d "${fv3_dir}" ]
        then

            jarf=$(ls ${fv3_dir}/fv3-*.jar | tail -n 1 )
            if [ -n "${jarf}" ]&&[ -f "${jarf}" ]
            then
                echo "${jarf}"
                return 0
            else
                cat<<EOF>&2
${progn}: error: jar file not found under ${fv3_dir}.
EOF
                exit 1
            fi
        else
            cat<<EOF>&2
${progn}: error: directory not found: ${fv3_dir}.
EOF
            exit 1
        fi
    else
        cat<<EOF>&2
${progn}: error: HOME/src not found.
EOF
        exit 1
    fi
}

function compile {
    if [ -f ${srcd}/font.class ]&&[ ${srcd}/font.class -nt ${srcd}/font.java ]
    then
        return 0
    else
        javac -cp ${fjar_fv3} -d ${srcd} ${srcd}/font.java
    fi
}
function run {
    java -cp ${fjar_fv3}:${srcd} font $*
}

if fjar_fv3=$(fv3_jar) &&[ -n "${fjar_fv3}" ]
then
    if compile && run ${tgtd}/${tgtn}.java
    then
        exit 0
    else
        exit 1
    fi
else
    exit 1
fi
