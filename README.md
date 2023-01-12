# Checkers - WIP
Game of checkers in 3 different variants:

| Lp. | Version  | Board size | Obliagtion of taking pawns |
| :-: | :------: | :--------: | :------------------------: |
| 1   | Polish   | 10 x 10    | Best                       |
| 2   | Canadian | 12 x 12    | Best                       |
| 3   | Russian  | 8 x 8      | Any                        | 

<br />

### Authors

* Jakub Musiał: `Spectral519`
* Krzysztof Dobrucki: `xywa5000`

<br />

### Project setup

1. Make sure you have [maven](https://maven.apache.org/download.cgi) installed. Installation guide can be found [here](https://phoenixnap.com/kb/install-maven-windows)

2. Clone the project repository by executing the following commands in a cmd prompt:

```
cd <project_directory>
git clone https://github.com/SpectraL519/checkers.git
```

<br />
<br />


### Running the project

1. Start the server:

```
cd <project_directory>
```

<br />

Use one of the following methods:

```
cd CheckersServer
mvn package
mvn clean compile exec:run
```

```
cd CheckersServer
mvn package
java -cp target/CheckersServer-1.0-SNAPSHOT.jar com.CheckersGame.Server.GameServer
```

<br />


2. Run the client application:

```
cd <project_directory>
cd CheckersClient
mvn package
mvn clean javafx:run
```