Call the main method with any number of comma separated doubles for starting coordinates
of the towers, e.g: 130,32.5 will put a tower at x=130,y=32.5, any formating errors will
lead to the input being disregarded.
(a.k.a. start with console command {java -jar "AoT Tower Defence.jar" 130,32.5 100,100}).

Click somewhere to add a new tower, it will be displayed as an icon along with range.
If the monster gets to close to a tower, it (the tower) gets destroyed.
One monster is automatically spawned upon launching the game, press m to add more.
The monsters will run djikstras algorithm to find the shortest path, there are dead ends.
If one monster makes it to the end of a road, the game over screen is displayed.
When all monsters are dead, the victory screen is displayed.

To switch road, make sure it has the name "road.ser" if none is present in (depending on ide) the same 
directory level as the classes, the program will not execute. Same goes for other resources namely images,
they to can be swapped provided the name does not change.

There is no test class as all tests have been done either through break-points or by printing temporary
variables during code execution. To hard code in tower positions check the main method in Game