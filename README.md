# DFT BLUE BADGE BETA - LA-WEBAPP

## Getting Started in few 5? minutes
From comand line:
```
brew install node
npm install gulp
brew install gradle
git clone git@github.com:uk-gov-dft/la-webapp.git
cd la-webapp
gradle build
gradle bootRun
```
It will show 80% complete but it is ready to be tested in the browser

From browser:
(http://localhost:8080/user/showLogin)

## BUILD

### LIST OF TASKS AVAILABLE FOR THAT BUILD.GRADLE
```
./gradlew tasks --all
```

### BUILD
```
./gradlew build
```

## RUN
###RUN FROM INTELLIJ
Go to this class and run from the intellij context menu with a mouse right click:
org.dft.bluebadge.seed.BlueBadgeApplication

###RUN FROM COMMAND LINE
From your parent project directory:
```
java -jar build/libs/la-webapp-0.0.1-SNAPSHOT.jar
```

### RUN WITH GRADLE
```
./gradlew bootRun
```

## PLAY WITH IT

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



## TECHNOLOGIES

### TESTING
Spring MVC Testing (to test the controllers):
(https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework)

### SONARQUBE SERVER INSTALLATION AND CONFIGURATION IN LOCAL
(https://docs.sonarqube.org/display/SONAR/Get+Started+in+Two+Minutes)

### SONARQUBE PLUGIN FOR INTELLIJ
(https://github.com/sonar-intellij-plugin/sonar-intellij-plugin)
