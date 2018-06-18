# DFT BLUE BADGE BETA - LA-WEBAPP - Acceptance tests

### Acceptance tests

In cases where you only edit code of the acceptance tests (rather than production code), usually a lot of time can
be saved by keeping an instance of the application running in the background and execute acceptance tests repeatedly,
without having to restart the application. To do so, make sure to have the application already started and running in a
standalone mode ([see instructions above](#RUN WITH GRADLE)) and then, to run the tests, execute (from project folder ..../la-webapp):

####Prerequisites
Start user management service
```
cd usermanagement-service
git pull
git checkout whateverbranch
cd model
gradle install
cd ../client
gradle install
cd ..
gradle build
gradle bootRun
```

Start la-webapp
```
gradle build
gradle bootRun
```

####Run the all acceptance tests

```
gradle acceptanceTest -PbuildProfile=local
```

-PbuildProfile is the profile for environment that you want to run tests against{Eg, local,dev,qa,prepod,prod}

OR you can run the shell script

Go to acceptance-tests folder & run (run_local.sh) shell script

```
./run_local.sh
```


By default acceptance tests will run on headless chrome. If you need to run it on headed mode, execute:

```
gradle acceptanceTest -PbuildProfile=local -Dheadless=false
```

If you need to run only speficied features, then add a tag to feature file & specify that in run command as below, execute:

Run a single feature

```
gradle acceptanceTest -PbuildProfile=local -Dheadless=false -Dcucumber.options="--tags @SignIn"
```

Run multiple features

```
gradle acceptanceTest -PbuildProfile=local -Dheadless=false -Dcucumber.options="--tags @SignIn,@ManageUsers"
```
Specify the relevant tag to run a feature file (Eg. @SignIn, @ManageUsers etc.)
