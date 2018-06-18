# DFT BLUE BADGE BETA - LA-WEBAPP - Acceptance tests

### Acceptance tests

In cases where you only edit code of the acceptance tests (rather than production code), usually a lot of time can
be saved by keeping an instance of the application running in the background and execute acceptance tests repeatedly,
without having to restart the application. To do so, make sure to have the application already started and running in a
standalone mode ([see instructions above](#RUN WITH GRADLE)) and then, to run the tests, execute (from project folder ..../la-webapp):

```
gradle acceptanceTest -PbuildProfile=local
```

-PbuildProfile is the profile for environment that you want to run tests against{Eg, local,dev,qa,prepod,prod}


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
