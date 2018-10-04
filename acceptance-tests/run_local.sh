#!/bin/bash
gradle acceptanceTests -Dheadless=false -DbaseUrl=http://localhost:8080 -Dcucumber.options="--tags @SignOut"