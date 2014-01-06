#!/bin/bash

mvn -Dctams.protocol=http -Dctams.host=localhost -Dctams.port=8080 -Dctams.uri=/ctams clean install
