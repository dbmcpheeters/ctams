#!/bin/bash

mvn -Dctams.protocol=http -Dctams.host=ctams.wuspba.org -Dctams.port=80 -Dctams.uri=/ctams clean install
