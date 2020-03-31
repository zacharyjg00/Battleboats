//Written by guldb016

public class BattleboatsBoard {

    private char[][] battleboatsBoard;
    private char[][] battleboatsBoardDisplay;
    private boolean standardOrExpert;
    private int numberOfShips;
    private int totalShots = 0;
    private int turns = 1;
    private int[] sunkCheckingArrayStandard = {2, 3, 3, 4, 5}; //This array will help with checking whether or not a boat is sunk on a standard board
    private int[] sunkStoringArrayStandard = new int[5]; //This array will help with storing hits to check against the sunkCheckingArrayStandard
    private int[] sunkCheckingArrayExpert = {2, 2, 3, 3, 3, 3, 4, 4, 5, 5}; //This array will help with checking whether or not a boat is sunk on an expert board
    private int[] sunkStoringArrayExpert = new int[10]; //This array will help with storing hits to check against the sunkCheckingArrayExpert
    private static int remainingShipsStandard = 5;
    private static int remainingShipsExpert = 10;

    public int getRemainingShipsStandard() {
        return remainingShipsStandard;
    }

    public int getRemainingShipsExpert() {
        return remainingShipsExpert;
    }

    public int getTurns() {
        return turns;
    }

    public int getTotalShots() {
        return totalShots;
    }
    public BattleboatsBoard(boolean standardOrExpert) {
        this.standardOrExpert = standardOrExpert;
        if(standardOrExpert) { //The boolean true will represent the standard board size. False will represent the expert board size
            numberOfShips = 5;
            battleboatsBoard = new char[8][8];
            for(int row = 0; row < 8; row ++) {
                for(int column = 0; column < 8; column ++){
                    battleboatsBoard[row][column] = '-';
                }
            }
            battleboatsBoardDisplay = new char[8][8];
            for(int row = 0; row < 8; row ++) {
                for(int column = 0; column < 8; column ++){
                    battleboatsBoardDisplay[row][column] = '-';
                }
            }
        } else { 
            numberOfShips = 9; //This number is nine as the interval that I will be labeling boats for the expert mode is 0-9 so all ten boats can be denoted by a single char
            battleboatsBoard = new char[12][12];
            for(int row = 0; row < 12; row ++) {
                for(int column = 0; column < 12; column ++) {
                    battleboatsBoard[row][column] = '-';
                }
            }
            battleboatsBoardDisplay = new char[12][12];
            for(int row = 0; row < 12; row ++) {
                for(int column = 0; column < 12; column ++) {
                    battleboatsBoardDisplay[row][column] = '-';
                }
            }
        }
    } //End of constructor

    public void randomBoatPlacement() {
        if(standardOrExpert) { //Random boat placement for a standard board size
            int currentNumberOfBoats = 0;
            int randomXCoord;

            int randomYCoord;

            int[][][] possibleCoords;
            //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
            //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.

            int[] goodDirections;
            //This will store all of the good possible directions. What this means is that every directions' coordinates will be checked to make sure that they are not conflicting with other boats and are within the bounds of the board. 
            //This array will help to store the good directions that the boat can be placed

            int numberOfPossibleDirections; //This int will help to scale and randomly select a direction for the boat to be placed

            int lengthOfShip; //This is to keep track of the length of the ship being placed

            boolean placedSuccessfully; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred

            while(currentNumberOfBoats != numberOfShips) {
                
                if(currentNumberOfBoats == 0) { //Boat 0 will be the two length boat
            
                    goodDirections = new int[4]; 
                    
                    numberOfPossibleDirections = 0; 

                    lengthOfShip = 2; //This is to keep track of the length of the ship being placed

                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred

                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 8));
                        randomYCoord = (int) Math.floor((Math.random() * 8));

                        possibleCoords = new int[4][2][2];
                        
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;

                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;


                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;

                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;


                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;

                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;


                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;

                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 2; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 8 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 8 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections)); 
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 2; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }
                    }
                }
                    
                if(currentNumberOfBoats == 1 || currentNumberOfBoats == 2) { //Boats 1 and 2 will be the three length boats
                    goodDirections = new int[4]; 
                        
                    numberOfPossibleDirections = 0;
    
                    lengthOfShip = 3; //This is to keep track of the length of the ship being placed
    
                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred
    
                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 8));
                        randomYCoord = (int) Math.floor((Math.random() * 8));

                        possibleCoords = new int[4][3][2];
                        //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
                        //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;
        
                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;

                        possibleCoords[0][2][0] = randomXCoord;
                        possibleCoords[0][2][1] = randomYCoord - 2;
        
        
                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;
        
                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;

                        possibleCoords[1][2][0] = randomXCoord - 2;
                        possibleCoords[1][2][1] = randomYCoord;
        
        
                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;
        
                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;

                        possibleCoords[2][2][0] = randomXCoord;
                        possibleCoords[2][2][1] = randomYCoord + 2;
        
        
                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;
        
                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        possibleCoords[3][2][0] = randomXCoord + 2;
                        possibleCoords[3][2][1] = randomYCoord;
                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 3; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 8 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 8 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections)); 
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 3; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }      
                    }
                }

                if(currentNumberOfBoats == 3) { //Boat 3 will be the four length boat
                    goodDirections = new int[4]; 
                        
                    numberOfPossibleDirections = 0;
    
                    lengthOfShip = 4; //This is to keep track of the length of the ship being placed
    
                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred
    
                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 8));
                        randomYCoord = (int) Math.floor((Math.random() * 8));

                        possibleCoords = new int[4][4][2];
                        //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
                        //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;
        
                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;

                        possibleCoords[0][2][0] = randomXCoord;
                        possibleCoords[0][2][1] = randomYCoord - 2;

                        possibleCoords[0][3][0] = randomXCoord;
                        possibleCoords[0][3][1] = randomYCoord - 3;
        
        
                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;
        
                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;

                        possibleCoords[1][2][0] = randomXCoord - 2;
                        possibleCoords[1][2][1] = randomYCoord;

                        possibleCoords[1][3][0] = randomXCoord - 3;
                        possibleCoords[1][3][1] = randomYCoord;
        
        
                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;
        
                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;

                        possibleCoords[2][2][0] = randomXCoord;
                        possibleCoords[2][2][1] = randomYCoord + 2;

                        possibleCoords[2][3][0] = randomXCoord;
                        possibleCoords[2][3][1] = randomYCoord + 3;
        
        
                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;
        
                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        possibleCoords[3][2][0] = randomXCoord + 2;
                        possibleCoords[3][2][1] = randomYCoord;
                        
                        possibleCoords[3][3][0] = randomXCoord + 3;
                        possibleCoords[3][3][1] = randomYCoord;
                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 4; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 8 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 8 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            } 
                                        }
                                    }
                                }
                            }
                        }

                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections)); 
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 4; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }
                    }
                }

                if(currentNumberOfBoats == 4) { //Boat 4 will be the five length boat
    
                    goodDirections = new int[4]; 
                        
                    numberOfPossibleDirections = 0;
    
                    lengthOfShip = 5; //This is to keep track of the length of the ship being placed
    
                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred
    
                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 8));
                        randomYCoord = (int) Math.floor((Math.random() * 8));
                        possibleCoords = new int[4][5][2];
                        //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
                        //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;
        
                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;

                        possibleCoords[0][2][0] = randomXCoord;
                        possibleCoords[0][2][1] = randomYCoord - 2;

                        possibleCoords[0][3][0] = randomXCoord;
                        possibleCoords[0][3][1] = randomYCoord - 3;

                        possibleCoords[0][4][0] = randomXCoord;
                        possibleCoords[0][4][1] = randomYCoord - 4;
        
        
                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;
        
                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;

                        possibleCoords[1][2][0] = randomXCoord - 2;
                        possibleCoords[1][2][1] = randomYCoord;

                        possibleCoords[1][3][0] = randomXCoord - 3;
                        possibleCoords[1][3][1] = randomYCoord;

                        possibleCoords[1][4][0] = randomXCoord - 4;
                        possibleCoords[1][4][1] = randomYCoord;
        
        
                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;
        
                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;

                        possibleCoords[2][2][0] = randomXCoord;
                        possibleCoords[2][2][1] = randomYCoord + 2;

                        possibleCoords[2][3][0] = randomXCoord;
                        possibleCoords[2][3][1] = randomYCoord + 3;
                        
                        possibleCoords[2][4][0] = randomXCoord;
                        possibleCoords[2][4][1] = randomYCoord + 4;
        
        
                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;
        
                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        possibleCoords[3][2][0] = randomXCoord + 2;
                        possibleCoords[3][2][1] = randomYCoord;
                        
                        possibleCoords[3][3][0] = randomXCoord + 3;
                        possibleCoords[3][3][1] = randomYCoord;

                        possibleCoords[3][4][0] = randomXCoord + 4;
                        possibleCoords[3][4][1] = randomYCoord;
                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 5; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 8 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 8 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections)); 
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 5; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }
                    }
                }
            }
        } else { //Random boat placement for an expert board size
            int currentNumberOfBoats = 0;
            int randomXCoord;

            int randomYCoord;
            
            int[][][] possibleCoords;
            //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
            //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.

            int[] goodDirections;
            //This will store all of the good possible directions. What this means is that every directions' coordinates will be checked to make sure that they are not conflicting with other boats and are within the bounds of the board. 
            //This array will help to store the good directions that the boat can be placed

            int numberOfPossibleDirections; //This int will help to scale and randomly select a direction for the boat to be placed

            int lengthOfShip; //This is to keep track of the length of the ship being placed

            boolean placedSuccessfully; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred

            while(currentNumberOfBoats != (numberOfShips + 1)) { //The numberOfShips must have a 1 added to it so that it still places 10 boats. Because there are two five length boats, the +1 is still added to currentNumberOfBoats once a boat has been successfully added. In order for the tenth boat to be added, numberOfShips must have a 1 added.
                
                if(currentNumberOfBoats == 0 || currentNumberOfBoats == 1) { //Boats 0 and 1 will be the two length boats
            
                    goodDirections = new int[4]; 
                    
                    numberOfPossibleDirections = 0; 

                    lengthOfShip = 2; //This is to keep track of the length of the ship being placed

                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred

                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 12));
                        randomYCoord = (int) Math.floor((Math.random() * 12));

                        possibleCoords = new int[4][2][2];
                        
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;

                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;


                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;

                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;


                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;

                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;


                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;

                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 2; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 12 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 12 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections));
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 2; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }
                    }
                }
                    
                if(currentNumberOfBoats == 2 || currentNumberOfBoats == 3 || currentNumberOfBoats == 4 || currentNumberOfBoats == 5) { //Boats 2 and 3 will be the three length boats
                    goodDirections = new int[4]; 
                        
                    numberOfPossibleDirections = 0;
    
                    lengthOfShip = 3; //This is to keep track of the length of the ship being placed
    
                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred
    
                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 12));
                        randomYCoord = (int) Math.floor((Math.random() * 12));

                        possibleCoords = new int[4][3][2];
                        //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
                        //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;
        
                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;

                        possibleCoords[0][2][0] = randomXCoord;
                        possibleCoords[0][2][1] = randomYCoord - 2;
        
        
                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;
        
                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;

                        possibleCoords[1][2][0] = randomXCoord - 2;
                        possibleCoords[1][2][1] = randomYCoord;
        
        
                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;
        
                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;

                        possibleCoords[2][2][0] = randomXCoord;
                        possibleCoords[2][2][1] = randomYCoord + 2;
        
        
                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;
        
                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        possibleCoords[3][2][0] = randomXCoord + 2;
                        possibleCoords[3][2][1] = randomYCoord;
                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 3; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 12 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 12 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections)); 
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 3; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }      
                    }
                }

                if(currentNumberOfBoats == 6 || currentNumberOfBoats == 7) { //Boat 4 will be the four length boat
                    goodDirections = new int[4]; 
                        
                    numberOfPossibleDirections = 0;
    
                    lengthOfShip = 4; //This is to keep track of the length of the ship being placed
    
                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred
    
                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 12));
                        randomYCoord = (int) Math.floor((Math.random() * 12));

                        possibleCoords = new int[4][4][2];
                        //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
                        //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;
        
                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;

                        possibleCoords[0][2][0] = randomXCoord;
                        possibleCoords[0][2][1] = randomYCoord - 2;

                        possibleCoords[0][3][0] = randomXCoord;
                        possibleCoords[0][3][1] = randomYCoord - 3;
        
        
                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;
        
                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;

                        possibleCoords[1][2][0] = randomXCoord - 2;
                        possibleCoords[1][2][1] = randomYCoord;

                        possibleCoords[1][3][0] = randomXCoord - 3;
                        possibleCoords[1][3][1] = randomYCoord;
        
        
                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;
        
                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;

                        possibleCoords[2][2][0] = randomXCoord;
                        possibleCoords[2][2][1] = randomYCoord + 2;

                        possibleCoords[2][3][0] = randomXCoord;
                        possibleCoords[2][3][1] = randomYCoord + 3;
        
        
                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;
        
                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        possibleCoords[3][2][0] = randomXCoord + 2;
                        possibleCoords[3][2][1] = randomYCoord;
                        
                        possibleCoords[3][3][0] = randomXCoord + 3;
                        possibleCoords[3][3][1] = randomYCoord;
                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 4; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 12 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 12 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            } 
                                        }
                                    }
                                }
                            }
                        }

                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections)); 
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 4; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }
                    }
                }

                if(currentNumberOfBoats == 8 || currentNumberOfBoats == 9) { //Boat 5 will be the five length boat
    
                    goodDirections = new int[4]; 
                        
                    numberOfPossibleDirections = 0;
    
                    lengthOfShip = 5; //This is to keep track of the length of the ship being placed
    
                    placedSuccessfully = false; //This boolean is made so that the while loop will keep going until a successful boat placement has occurred
    
                    while(placedSuccessfully != true) {
                        randomXCoord = (int) Math.floor((Math.random() * 12));
                        randomYCoord = (int) Math.floor((Math.random() * 12));
                        possibleCoords = new int[4][5][2];
                        //The first array position will determine the direction. 0 is left. 1 is up. 2 is right. 3 is down. The second array position will be the length of the boat. The third array position will be either the x-coordinate or the y-coordinate.
                        //This will account for every possible position that the boat can be placed. These coordinates will then be checked later to make sure that they are valid positions on the board.
                        possibleCoords[0][0][0] = randomXCoord;
                        possibleCoords[0][0][1] = randomYCoord;
        
                        possibleCoords[0][1][0] = randomXCoord;
                        possibleCoords[0][1][1] = randomYCoord - 1;

                        possibleCoords[0][2][0] = randomXCoord;
                        possibleCoords[0][2][1] = randomYCoord - 2;

                        possibleCoords[0][3][0] = randomXCoord;
                        possibleCoords[0][3][1] = randomYCoord - 3;

                        possibleCoords[0][4][0] = randomXCoord;
                        possibleCoords[0][4][1] = randomYCoord - 4;
        
        
                        possibleCoords[1][0][0] = randomXCoord;
                        possibleCoords[1][0][1] = randomYCoord;
        
                        possibleCoords[1][1][0] = randomXCoord - 1;
                        possibleCoords[1][1][1] = randomYCoord;

                        possibleCoords[1][2][0] = randomXCoord - 2;
                        possibleCoords[1][2][1] = randomYCoord;

                        possibleCoords[1][3][0] = randomXCoord - 3;
                        possibleCoords[1][3][1] = randomYCoord;

                        possibleCoords[1][4][0] = randomXCoord - 4;
                        possibleCoords[1][4][1] = randomYCoord;
        
        
                        possibleCoords[2][0][0] = randomXCoord;
                        possibleCoords[2][0][1] = randomYCoord;
        
                        possibleCoords[2][1][0] = randomXCoord;
                        possibleCoords[2][1][1] = randomYCoord + 1;

                        possibleCoords[2][2][0] = randomXCoord;
                        possibleCoords[2][2][1] = randomYCoord + 2;

                        possibleCoords[2][3][0] = randomXCoord;
                        possibleCoords[2][3][1] = randomYCoord + 3;
                        
                        possibleCoords[2][4][0] = randomXCoord;
                        possibleCoords[2][4][1] = randomYCoord + 4;
        
        
                        possibleCoords[3][0][0] = randomXCoord;
                        possibleCoords[3][0][1] = randomYCoord;
        
                        possibleCoords[3][1][0] = randomXCoord + 1;
                        possibleCoords[3][1][1] = randomYCoord;

                        possibleCoords[3][2][0] = randomXCoord + 2;
                        possibleCoords[3][2][1] = randomYCoord;
                        
                        possibleCoords[3][3][0] = randomXCoord + 3;
                        possibleCoords[3][3][1] = randomYCoord;

                        possibleCoords[3][4][0] = randomXCoord + 4;
                        possibleCoords[3][4][1] = randomYCoord;
                        for(int d = 0; d < 4; d ++) { //The direction
                            int goodLengthOfShip = 0; //This int is used to make sure that a direction is a good direction with no issues. It is iterated by one everytime a good coordinate goes through all of the checks. Once it equals the length of the boat, that direction is deemed good
                            for(int l = 0; l < 5; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) { //This check allows me to check my x and y coordinates at the same time. In order to do this I only want to check every other iteration so I make sure to skip one so I am not rechecking the same coordinates
                                        if(possibleCoords[d][l][c] < 12 && (possibleCoords[d][l][c] >= 0) && possibleCoords[d][l][c + 1] < 12 && possibleCoords[d][l][c + 1] >= 0) { //This if statement checks each and every coordinate to make sure that they are within the bounds of the board
                                            if(battleboatsBoard[possibleCoords[d][l][c]][possibleCoords[d][l][c + 1]] == '-') { //This if statement checks to make sure that there is not another boat in any of the generated coordinates
                                                goodLengthOfShip += 1;
                                                if(goodLengthOfShip == lengthOfShip) {
                                                    goodDirections[numberOfPossibleDirections] = d;
                                                    numberOfPossibleDirections += 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(numberOfPossibleDirections > 0) {
                            int randomDirection = (int) Math.floor((Math.random() * numberOfPossibleDirections));
                            //Now that every direction is checked, one has to randomly be generated and selected. 
                            //This will generate a random index for a good direction that is stored in the goodDirections int array.
                            for(int l = 0; l < 5; l ++) { //Length of the ship
                                for(int c = 0; c < 2; c ++) { //The coordinate
                                    if((c + 1) % 2 != 0) {
                                        battleboatsBoard[possibleCoords[goodDirections[randomDirection]][l][c]][possibleCoords[goodDirections[randomDirection]][l][c + 1]] = String.valueOf(currentNumberOfBoats).charAt(0); 
                                        //This statment actually places the boats into the 2D array. Each boat is denoted by a number which is stated at the beginning of each if statement. The part at the end turns the currenNumberOfBoats into a string and then into a char as you can not explicitly turn an int into a char.
                                    }
                                }
                            }
                            placedSuccessfully = true;
                            currentNumberOfBoats += 1;
                        }
                    }
                }
            }
        }
    } //End of randomBoatPlacement

    public void fire(int x, int y) {
        totalShots += 1;
        if(standardOrExpert) {
            if(x >= 8 || x < 0 || y >= 8 || y < 0) {
                System.out.println("Penalty");
                turns += 2;
            } else if(battleboatsBoard[x][y] == '-') {
                System.out.println("Miss");
                battleboatsBoard[x][y] = 'O';
                battleboatsBoardDisplay[x][y] = 'O';
                turns += 1;
            } else if(battleboatsBoard[x][y] == 'O' || battleboatsBoard[x][y] == 'X') {
                System.out.println("Penalty");
                turns += 2;
            } else {
                System.out.println("Hit!");
                turns += 1;
                for(int i = 0; i < 5; i ++) {
                    if(battleboatsBoard[x][y] == String.valueOf(i).charAt(0)) {
                        sunkStoringArrayStandard[i] += 1;
                        if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                            System.out.println("Sunk!");
                            remainingShipsStandard -= 1;
                        }
                    }
                }
                battleboatsBoard[x][y] = 'X';
                battleboatsBoardDisplay[x][y] = 'X';
            }
        
        } else {
            if(x >= 12 || x < 0 || y >= 12 || y < 0) {
                System.out.println("Penalty");
                turns += 2;
            } else if(battleboatsBoard[x][y] == '-') {
                System.out.println("Miss");
                battleboatsBoard[x][y] = 'O';
                battleboatsBoardDisplay[x][y] = 'O';
                turns += 1;
            } else if(battleboatsBoard[x][y] == 'O' || battleboatsBoard[x][y] == 'X') {
                System.out.println("Penalty");
                turns += 2;
            } else {
                System.out.println("Hit!");
                turns += 1;
                for(int i = 0; i < 10; i ++) {
                    if(battleboatsBoard[x][y] == String.valueOf(i).charAt(0)) {
                        sunkStoringArrayExpert[i] += 1;
                        if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                            System.out.println("Sunk!");
                            remainingShipsExpert -= 1;
                        }
                    }
                }
                battleboatsBoard[x][y] = 'X';
                battleboatsBoardDisplay[x][y] = 'X';
            }
        }
    }

    public void display() {
        for (int i = 0; i < battleboatsBoard.length; i ++) {
            for (int j = 0; j < battleboatsBoard.length; j ++) {
                System.out.print(battleboatsBoardDisplay[i][j]);
            }
        System.out.println();
        }
    }

    public void print() {
        for (int i = 0; i < battleboatsBoard.length; i ++) {
            for (int j = 0; j < battleboatsBoard.length; j ++) {
                System.out.print(battleboatsBoard[i][j]);
            }
        System.out.println();
        }
    }

    public void missile(int x, int y) {
        if(standardOrExpert) { //Missile for a standard board. It manually checks each and every coordinate if it is in the bounds of the board. It is assumed that the passed in coordinate is valid as the validity of passed in coordinates will be checked in the game part of this assignment
            if(battleboatsBoard[x][y] == '-' || battleboatsBoard[x][y] == 'O') {
                battleboatsBoard[x][y] = 'O';
                battleboatsBoardDisplay[x][y] = 'O';
            } else {
                for(int i = 0; i < 5; i ++) {
                    if(battleboatsBoard[x][y] == String.valueOf(i).charAt(0)) {
                        sunkStoringArrayStandard[i] += 1;
                        if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                            System.out.println("Sunk!");
                            remainingShipsStandard -= 1;
                        }
                    }
                }
                battleboatsBoard[x][y] = 'X';
                battleboatsBoardDisplay[x][y] = 'X';
            }

            if(x - 1 >= 0 && x - 1  < 8 && y - 1 >= 0 && y - 1 < 8) {
                if(battleboatsBoard[x - 1][y - 1] == '-' || battleboatsBoard[x - 1][y - 1] == 'O') {
                    battleboatsBoard[x - 1][y - 1] = 'O';
                    battleboatsBoardDisplay[x - 1][y - 1] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x - 1][y - 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x - 1][y - 1] = 'X';
                    battleboatsBoardDisplay[x - 1][y - 1] = 'X';
                }
            }

            if(x - 1 >= 0 && x - 1  < 8 && y >= 0 && y < 8) {
                if(battleboatsBoard[x - 1][y] == '-' || battleboatsBoard[x - 1][y] == 'O') {
                    battleboatsBoard[x - 1][y] = 'O';
                    battleboatsBoardDisplay[x - 1][y] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x - 1][y] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x - 1][y] = 'X';
                    battleboatsBoardDisplay[x - 1][y] = 'X';
                }
            }

            if(x - 1 >= 0 && x - 1  < 8 && y + 1 >= 0 && y + 1 < 8) {
                if(battleboatsBoard[x - 1][y + 1] == '-' || battleboatsBoard[x - 1][y + 1] == 'O') {
                    battleboatsBoard[x - 1][y + 1] = 'O';
                    battleboatsBoardDisplay[x - 1][y + 1] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x - 1][y + 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x - 1][y + 1] = 'X';
                    battleboatsBoardDisplay[x - 1][y + 1] = 'X';
                }
            }

            if(x >= 0 && x < 8 && y - 1 >= 0 && y - 1 < 8) {
                if(battleboatsBoard[x][y - 1] == '-' || battleboatsBoard[x][y - 1] == 'O') {
                    battleboatsBoard[x][y - 1] = 'O';
                    battleboatsBoardDisplay[x][y - 1] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x][y - 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x][y - 1] = 'X';
                    battleboatsBoardDisplay[x][y - 1] = 'X';
                }
            }

            if(x >= 0 && x < 8 && y + 1 >= 0 && y + 1 < 8) {
                if(battleboatsBoard[x][y + 1] == '-' || battleboatsBoard[x][y + 1] == 'O') {
                    battleboatsBoard[x][y + 1] = 'O';
                    battleboatsBoardDisplay[x][y + 1] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x][y + 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x][y + 1] = 'X';
                    battleboatsBoardDisplay[x][y + 1] = 'X';
                }
            }

            if(x + 1 >= 0 && x + 1  < 8 && y - 1 >= 0 && y - 1 < 8) {
                if(battleboatsBoard[x + 1][y - 1] == '-' || battleboatsBoard[x + 1][y - 1] == 'O') {
                    battleboatsBoard[x + 1][y - 1] = 'O';
                    battleboatsBoardDisplay[x + 1][y - 1] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x + 1][y - 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x + 1][y - 1] = 'X';
                    battleboatsBoardDisplay[x + 1][y - 1] = 'X';
                }
            }

            if(x + 1 >= 0 && x + 1  < 8 && y >= 0 && y < 8) {
                if(battleboatsBoard[x + 1][y] == '-' || battleboatsBoard[x + 1][y] == 'O') {
                    battleboatsBoard[x + 1][y] = 'O';
                    battleboatsBoardDisplay[x + 1][y] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x + 1][y] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x + 1][y] = 'X';
                    battleboatsBoardDisplay[x + 1][y] = 'X';
                }
            }

            if(x + 1 >= 0 && x + 1  < 8 && y + 1 >= 0 && y + 1 < 8) {
                if(battleboatsBoard[x + 1][y + 1] == '-' || battleboatsBoard[x + 1][y + 1] == 'O') {
                    battleboatsBoard[x + 1][y + 1] = 'O';
                    battleboatsBoardDisplay[x + 1][y + 1] = 'O';
                } else {
                    for(int i = 0; i < 5; i ++) {
                        if(battleboatsBoard[x + 1][y + 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayStandard[i] += 1;
                            if(sunkStoringArrayStandard[i] == sunkCheckingArrayStandard[i]) {
                                System.out.println("Sunk!");
                                remainingShipsStandard -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x + 1][y + 1] = 'X';
                    battleboatsBoardDisplay[x + 1][y + 1] = 'X';
                }
            }
        } else { //Missile for an expert board
            if(battleboatsBoard[x][y] == '-' || battleboatsBoard[x][y] == 'O') {
                battleboatsBoard[x][y] = 'O';
                battleboatsBoardDisplay[x][y] = 'O';
            } else {
                for(int i = 0; i < 10; i ++) {
                    if(battleboatsBoard[x][y] == String.valueOf(i).charAt(0)) {
                        sunkStoringArrayExpert[i] += 1;
                        if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                            System.out.println("Sunk!");
                            remainingShipsExpert -= 1;
                        }
                    }
                }
                battleboatsBoard[x][y] = 'X';
                battleboatsBoardDisplay[x][y] = 'X';
            }

            if(x - 1 >= 0 && x - 1  < 12 && y - 1 >= 0 && y - 1 < 12) {
                if(battleboatsBoard[x - 1][y - 1] == '-' || battleboatsBoard[x - 1][y - 1] == 'O') {
                    battleboatsBoard[x - 1][y - 1] = 'O';
                    battleboatsBoardDisplay[x - 1][y - 1] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x - 1][y - 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x - 1][y - 1] = 'X';
                    battleboatsBoardDisplay[x - 1][y - 1] = 'X';
                }
            }

            if(x - 1 >= 0 && x - 1  < 12 && y >= 0 && y < 12) {
                if(battleboatsBoard[x - 1][y] == '-' || battleboatsBoard[x - 1][y] == 'O') {
                    battleboatsBoard[x - 1][y] = 'O';
                    battleboatsBoardDisplay[x - 1][y] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x - 1][y] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x - 1][y] = 'X';
                    battleboatsBoardDisplay[x - 1][y] = 'X';
                }
            }

            if(x - 1 >= 0 && x - 1  < 12 && y + 1 >= 0 && y + 1 < 12) {
                if(battleboatsBoard[x - 1][y + 1] == '-' || battleboatsBoard[x - 1][y + 1] == 'O') {
                    battleboatsBoard[x - 1][y + 1] = 'O';
                    battleboatsBoardDisplay[x - 1][y + 1] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x - 1][y + 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x - 1][y + 1] = 'X';
                    battleboatsBoardDisplay[x - 1][y + 1] = 'X';
                }
            }

            if(x >= 0 && x < 12 && y - 1 >= 0 && y - 1 < 12) {
                if(battleboatsBoard[x][y - 1] == '-' || battleboatsBoard[x][y - 1] == 'O') {
                    battleboatsBoard[x][y - 1] = 'O';
                    battleboatsBoardDisplay[x][y - 1] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x][y - 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x][y - 1] = 'X';
                    battleboatsBoardDisplay[x][y - 1] = 'X';
                }
            }

            if(x >= 0 && x < 12 && y + 1 >= 0 && y + 1 < 12) {
                if(battleboatsBoard[x][y + 1] == '-' || battleboatsBoard[x][y + 1] == 'O') {
                    battleboatsBoard[x][y + 1] = 'O';
                    battleboatsBoardDisplay[x][y + 1] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x][y + 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x][y + 1] = 'X';
                    battleboatsBoardDisplay[x][y + 1] = 'X';
                }
            }

            if(x + 1 >= 0 && x + 1  < 12 && y - 1 >= 0 && y - 1 < 12) {
                if(battleboatsBoard[x + 1][y - 1] == '-' || battleboatsBoard[x + 1][y - 1] == 'O') {
                    battleboatsBoard[x + 1][y - 1] = 'O';
                    battleboatsBoardDisplay[x + 1][y - 1] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x + 1][y - 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x + 1][y - 1] = 'X';
                    battleboatsBoardDisplay[x + 1][y - 1] = 'X';
                }
            }

            if(x + 1 >= 0 && x + 1  < 12 && y >= 0 && y < 12) {
                if(battleboatsBoard[x + 1][y] == '-' || battleboatsBoard[x + 1][y] == 'O') {
                    battleboatsBoard[x + 1][y] = 'O';
                    battleboatsBoardDisplay[x + 1][y] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x + 1][y] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x + 1][y] = 'X';
                    battleboatsBoardDisplay[x + 1][y] = 'X';
                }
            }

            if(x + 1 >= 0 && x + 1  < 12 && y + 1 >= 0 && y + 1 < 12) {
                if(battleboatsBoard[x + 1][y + 1] == '-' || battleboatsBoard[x + 1][y + 1] == 'O') {
                    battleboatsBoard[x + 1][y + 1] = 'O';
                    battleboatsBoardDisplay[x + 1][y + 1] = 'O';
                } else {
                    for(int i = 0; i < 10; i ++) {
                        if(battleboatsBoard[x + 1][y + 1] == String.valueOf(i).charAt(0)) {
                            sunkStoringArrayExpert[i] += 1;
                            if(sunkStoringArrayExpert[i] == sunkCheckingArrayExpert[i]) {
                                System.out.println("Sunk!");
                                remainingShipsExpert -= 1;
                            }
                        }
                    }
                    battleboatsBoard[x + 1][y + 1] = 'X';
                    battleboatsBoardDisplay[x + 1][y + 1] = 'X';
                }
            }
        }
        turns += 1;
    }//Missile end

    public void drone(boolean direction, int index) {
        if(direction) { //True in this case will correspond to scanning a row
            int numberOfTargets = 0;
            for(int i = 0; i < battleboatsBoard.length; i ++) {
                if(battleboatsBoard[index][i] != '-' && battleboatsBoard[index][i] != 'O') {
                    numberOfTargets += 1;
                }
            }
            System.out.println("Drone has scanned " + numberOfTargets + " target(s) in the specified area");
            turns += 1;
        } else { //False will be scanning a column
            int numberOfTargets = 0;
            for(int i = 0; i < battleboatsBoard.length; i ++) {
                if(battleboatsBoard[i][index] != '-' && battleboatsBoard[i][index] != 'O') {
                    numberOfTargets += 1;
                }
            }
            System.out.println("Drone has scanned " + numberOfTargets + " target(s) in the specified area");
            turns += 1;
        }
    }
}