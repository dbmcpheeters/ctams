#!/bin/bash

mvn -Dctams.protocol=http -Dctams.host=wuspba.org -Dctams.port=8080 -Dctams.uri=/ctams clean install
