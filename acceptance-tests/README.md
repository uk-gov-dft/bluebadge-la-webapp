# DFT BLUE BADGE BETA - LA-WEBAPP - Acceptance tests

## Acceptance tests

In cases where you only edit code of the acceptance tests (rather than production code), usually a lot of time can
be saved by keeping an instance of the application running in the background and execute acceptance tests repeatedly,
without having to restart the application. To do so, make sure to have the application already started and running in a
standalone mode ([see instructions above](#RUN WITH GRADLE)) and then, to run the tests, execute (from project folder ..../la-webapp):

### Prerequisites
Start all the services & applications

#### Start user management service
```
cd usermanagement-service
git checkout whateverbranch
git pull
./gradlew install
./gradlew build
./gradlew bootRun
```

#### Start authorisation service
```
cd authorisation-service
git checkout whateverbranch
git pull
./gradlew build
./gradlew bootRun
```

#### Start message service
```
cd message-service
git checkout whateverbranch
git pull
./gradlew build
./gradlew bootRun
```

#### Start la-webapp
```
./gradlew build
./gradlew bootRun
```

## Run the all acceptance tests

```
cd acceptance-tests
./gradlew acceptanceTest -PbuildProfile=local
```

-PbuildProfile is the profile for environment that you want to run tests against{Eg, local,dev,qa,prepod,prod}

OR you can run the shell script

Go to acceptance-tests folder & run (run_local.sh) shell script

```
./run_local.sh
```

To specify your operating system; By default it will use as 'mac'

```
./gradlew acceptanceTest -PbuildProfile=local -Dos=mac
```
os parameter values can be 'mac' or 'linux' or 'windows'


By default acceptance tests will run on headless chrome. If you need to run it on headed mode, execute:

```
./gradlew acceptanceTest -PbuildProfile=local -Dheadless=false
```

If you need to run only specified features, then add a tag to feature file & specify that in run command as below, execute:

Run a single feature

```
./gradlew acceptanceTest -PbuildProfile=local -Dheadless=false -Dcucumber.options="--tags @SignIn"
```

Run multiple features
Specify the relevant tag to run a feature file (Eg. @SignIn, @ManageUsers etc.)

```
./gradlew acceptanceTest -PbuildProfile=local -Dheadless=false -Dcucumber.options="--tags @SignIn,@ManageUsers"
```
To ignore certain features, first you need to mark your feature 
with specific tag, let's say @ignore and then:

```
./gradlew acceptanceTest -PbuildProfile=local -Dheadless=false -Dcucumber.options="--tags ~@ignore"
```
Test results:
You can find the results in following location on la-webapp project:
```
output/report.html
```
