# Checkers - WIP
Game of checkers in 3 different variants:

**Table: Checkers game versions**
| Lp. | Version  | Board size | Obliagtion of taking pawns |
| :-: | :------: | :--------: | :------------------------: |
| 1   | Polish   | 10 x 10    | Best                       |
| 2   | Canadian | 12 x 12    | Best                       |
| 3   | Russian  | 8 x 8      | Any                        | 

<br />

### Project setup

1. Make sure you have [maven](https://maven.apache.org/download.cgi) installed. Installation guide can be found [here](https://phoenixnap.com/kb/install-maven-windows)

2. Clone the project repository by executing the following commands in a cmd prompt:

```
cd <project_directory>
git clone https://github.com/SpectraL519/checkers.git
```

3. Enter the maven project directory:

```
cd CheckersGame
```

<br />

### Running the project

1. First build the project with:

```
mvn package
```

<br />

2. Start the server:

```
java -cp target/CheckersGame-1.0-SNAPSHOT.jar com.PWr.app.Server.GameServer
```

<br />

3. Run the application:

    * The console application:

    ```
    java -cp target/CheckersGame-1.0-SNAPSHOT.jar com.PWr.app.ConsoleApp
    ```

    * The GUI application:

    ```
    mvn clean javafx:run
    ```

<br />
<br />
<br />

# TODO:

* exit command handling to the GameClient class

* Change project structure. Separate maven projects for the game server and for the game client.

* Sending the game board to the client: 

```
// Example: 
// In classes Board, Version, Game add functions:
ArrayList <int> getBoardDescription () {...}

// In CommandLine.sendMessage() add: 
this.output.println(this.game.getBoardDescription().toString());

// In GameClient class add reading the board
```
