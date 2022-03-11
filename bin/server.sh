#!/usr/bin/env bash

if [ ! command -v dtlv &> /dev/null ]; then
    echo "dtlv executable not found on your PATH, please install it."
    echo "https://github.com/juji-io/datalevin"
    exit 1
fi

DATA=$(dirname "$0")/../data

dtlv serv --root ${DATA}
