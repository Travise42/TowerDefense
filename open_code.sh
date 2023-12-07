#!/bin/zsh

PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
WORKSPACE=$( basename $(ls -1 ${PROJECT_DIR}/*.code-workspace ) )

code ${WORKSPACE}
