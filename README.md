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

2. Run the application:

```
mvn clean javafx:run
```
