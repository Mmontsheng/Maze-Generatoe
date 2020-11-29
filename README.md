# Introduction
A maze generator based on <a href="https://en.wikipedia.org/wiki/Maze_generation_algorithm#Iterative_implementation" target="_blank">Randomized depth-first search</a>  inspired by <a href="https://thecodingtrain.com/CodingChallenges/010.1-maze-dfs-p5.html" target="_blank">The coding Train</a>


## Hacking
### Editing
Uhm, your idea might freak out because of [lombok](https://projectlombok.org), please do yourself a favour by adding it to your IDE. 
- Ensure you have java 11 installed. 
- You can do by using your favourite cmd tool, run `java -version`
![java version](https://github.com/Mmontsheng/Maze-Generator/blob/master/java_version.PNG)
#### Build
This is a mvn project, so a `mvn clean package` should do.

#### Runing
- `cd` into `target`
- Run `java -jar MazeGenerator-jar-with-dependencies.jar`
- Ew, long jar file name :poop:
- ![Running instance](https://github.com/Mmontsheng/Maze-Generator/blob/master/maze.PNG)
## TODO
- [X] Build a jar
- [ ] Dockerize 
- Already have a docker file, just having problems rendering the GUI of the maze 