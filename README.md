# Battleboats

This is **Battleboats**. It is a Battleships clone written in Java. You can choose to play in either standard or expert mode where ships are randomly generated and placed on the boards. It is your job to sink all of the ships in the least amount of turns and the least amount of shots possible. The BattleboatsBoard.java file is used to generate the randomly placed boats on the appropriate sized board as well as store all of the functions such as the missile ability and the normal fire shot function. The BattleboatsGame.java is the actual game file. In order to play, you must compile both files and then run the BattleboatsGame file. Enjoy!


![](Images/FireAndPrintExpert.JPG)
**Above is a picture that shows the fire and print function in action on an expert sized board.**


![](Images/MissileAndPrintStandard.JPG)
**Above is a picture that shows the missile and print function in action on a standard sized board.**


**TODO:** 
- Refactor to reduce repetitive code
- Figure out a solution to deal with non-int inputs when the user is prompted for coordinates
- Add in the ability to store all hits, misses, and powers in an SQL database
