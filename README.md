# DFT BLUE BADGE BETA - LA-WEBAPP

## Getting Started in few minutes
From command line:
```
brew install node
npm install gulp
brew install gradle
git clone git@github.com:uk-gov-dft/la-webapp.git
cd la-webapp
gradle wrapper
gradle build
gradle bootRun
```
It will show 80% complete but it is ready to be tested in the browser

From browser:
(http://localhost:8080/sign-in)

## BUILD

### LIST OF TASKS AVAILABLE FOR THAT BUILD.GRADLE
```
./gradlew tasks --all
```

### BUILD

#### DEPENDENCIES
This project depends on some usermanagement-service artifacts that you have to build and install in your maven local
repository before (Eventually this will not be needed but for the time being it works like this):

```
cd usermanagement-service
git pull
git checkout whateverbranch
cd model
gradle install
cd ../client
gradle install
```


```
./gradlew build

```

### CONTINUOUS BUILD
Rebuilds if there is a change
```
./gradlew build -t
```

## RUN
### RUN FROM INTELLIJ
Go to this class and run from the intellij context menu with a mouse right click:
org.dft.bluebadge.seed.BlueBadgeApplication

### RUN FROM COMMAND LINE
From your parent project directory:
```
java -jar build/libs/la-webapp-0.0.1-SNAPSHOT.jar
```

### RUN WITH GRADLE
```
./gradlew bootRun
```

## PLAY WITH IT
(http://localhost:8080/sign-in)
(http://localhost:8080/applications/1/show)
(http://localhost:8080/applications/showAll)
(http://localhost:8080/applications/showCreate)
(http://localhost:8080/applications/1/showUpdate)


### SWAGGER
(http://localhost:8080/v2/api-docs)
(http://localhost:8080/swagger-ui.html)

### SONARQUBE:
You need the sonarqube server running in this place:
(http://localhost:9000/about)

## CODE QUALITY
(some of them are run as part of the build task, so no need to run them separately)

### FORMAT CODE STYLE USING GOOGLE STANDARDS
```
./gradlew goJF
```

### VERIFY CODE STYLE USING GOOGLE STANDARDS
```
./gradlew verGJF
```

### PMD CODE QUALITY CHECK
```
./gradlew pmdMain
./gradlew pmdTest
```

### FINDBUGS CODE QUALITY CHECK
```
./gradlew findbugsMain
./gradlew findbugsTest
```

### CHECKSTYLE CODE QUALITY CHECK
```
./gradlew checkstyleMain
./gradlew checkstyleTest
```

### CODE QUALITY CHECK (includes checkstyle, pmd, findbugs, etc)
```
./gradlew check
```

### SONARQUEBE
```
./gradlew sonarqube
```

### GULP
```
./gradlew gulp_build
./gradlew gulp_compile
```


## TOOLING

### ENABLE LIVE RELOAD
It means if you change a template in your IDE, you want to see the changes in your browser.
You just need to install an extension in your browser
[More Info](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html#using-boot-devtools-livereload)
[Extension for chrome](https://chrome.google.com/webstore/search/livereload)

### RELOAD STATIC CONTENT WHEN THERE IS A CHANGE
(https://docs.spring.io/spring-boot/docs/current/reference/html/howto-hotswapping.html)

#### In Gradle
```$xslt
./gradlew bootRun
```
It is already configured to do that. You have to reload the page in the browser.

#### In Intellij
You have to trigger a rebuild with CMD+F9 and then reload the page.

### SONARQUBE SERVER INSTALLATION AND CONFIGURATION IN LOCAL
(https://docs.sonarqube.org/display/SONAR/Get+Started+in+Two+Minutes)

### SONARQUBE PLUGIN FOR INTELLIJ
(https://github.com/sonar-intellij-plugin/sonar-intellij-plugin)

## TECHNOLOGIES

### THYMELEAF
* [Thymeleaf official docs](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
* [Thymeleaf + Spring official docs](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)
* [Thymeleaf: Creating forms](https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html#creating-a-form)
* [Thymeleaf: Validation and error messages](https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html#validation-and-error-messages)
* [Thymeleaf: Layouts](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#template-layout)
* [Baeldung: Thymeleaf layout dialet](http://www.baeldung.com/thymeleaf-spring-layouts)
* [Thymeleaf layout dialect](https://ultraq.github.io/thymeleaf-layout-dialect/)

### SPRING
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/)
* [Spring reference](https://docs.spring.io/spring/docs/current/spring-framework-reference/)
* [Article about Spring Internationalization](http://www.baeldung.com/spring-boot-internationalization)

### TESTING
[Spring MVC Testing (to test the controllers)](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework)

### Acceptance tests

In cases where you only edit code of the acceptance tests (rather than production code), usually a lot of time can
be saved by keeping an instance of the application running in the background and execute acceptance tests repeatedly,
without having to restart the application. To do so, make sure to have the application already started and running in a
standalone mode ([see instructions above](#RUN WITH GRADLE)) and then, to run the tests, execute (from project folder ..../la-webapp):
```
mvn verify -f acceptance-tests/pom.xml -Pacceptance-test,local
```

By default acceptance tests will run on headless chrome. If you need to run it on headed mode, execute:
```
mvn verify -f acceptance-tests/pom.xml -Pacceptance-test,local -Dheadless=false
```

If you need to run only speficied features, then add a tag to feature file & specify that in run command as below, execute:
```
mvn verify -f acceptance-tests/pom.xml -Pacceptance-test,local -Dheadless=false -Dcucumber.options="--tags @SignIn"
```
