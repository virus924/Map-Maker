# Map-Maker
Simple AI to create a fully connected map on a grid

Run the MapAI.java file in order to run the program. The size of the map is currently adjusted from within the file itself.

NOTE - omitting original Node.java file created for this project. That path was not followed, so it should be obsolete in terms of where the project is

Large portion of this project uses a theme I call infecting.
  The idea is that an "infection" will spread to all the adjecent, connected tiles, but will fail to spread to tiles that are not direcelty connected.
  Infecting uses unique variables and thus allows the program to determine when the infection is complete by having every single tile infected.
