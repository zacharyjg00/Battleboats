//Written by guldb016
import java.util.Scanner;

public class BattleboatsGame {

    public static void main(String[] args) {
        BattleboatsBoard newBoard;
        Scanner gameInput = new Scanner(System.in);
        System.out.println("Welcome to Battleboats! Please enter s if you would like to play in standard mode or enter an e if you would like to play in expert mode.");
        System.out.print("Enter choice here: ");
        String standardOrExpert = gameInput.nextLine();

        while(!standardOrExpert.equals("s") && !standardOrExpert.equals("e")) {
            System.out.println("Invalid input. Please enter s to play in standard mode or e to play in expert mode.");
            System.out.print("Enter choice here: ");
            standardOrExpert = gameInput.nextLine();
        }
        if(standardOrExpert.equals("s")) { //Standard mode
            int droneUses = 1;
            int missileUses = 1;
            newBoard = new BattleboatsBoard(true);
            newBoard.randomBoatPlacement();
            newBoard.display();
            while(newBoard.getRemainingShipsStandard() != 0) {
                System.out.println("Enter f to fire a shot. Enter missile to fire a missile. Enter drone to launch a drone. Enter print to cheat and see where the boats are placed");
                System.out.print("Enter choice here: ");
                String action = gameInput.nextLine();
                switch(action) {
                    case "f":
                        System.out.print("Please enter an x coordinate: ");
                        int xCoord = gameInput.nextInt();
                        System.out.print("Please enter a y coordinate: ");
                        int yCoord = gameInput.nextInt();
                        gameInput.nextLine();
                        newBoard.fire(xCoord, yCoord);
                        newBoard.display();
                        break;
                    case "missile":
                        if(missileUses == 0) {
                            System.out.println("You are out of missile uses.");
                            break;
                        }
                        System.out.print("Please enter an x coordinate for the missile: ");
                        int missileXCoord = gameInput.nextInt();  
                        System.out.print("Please enter a y coordinate for the missile: ");
                        int missileYCoord = gameInput.nextInt();
                        gameInput.nextLine();
                        while(missileXCoord >= 8 || missileXCoord < 0 || missileYCoord >= 8 || missileYCoord < 0) {
                            System.out.println("Please enter valid coordinates.");
                            System.out.print("Please enter an x coordinate for the missile: ");
                            missileXCoord = gameInput.nextInt();
                            System.out.print("Please enter a y coordinate for the missile: ");
                            missileYCoord = gameInput.nextInt();
                            gameInput.nextLine();
                        }
                        newBoard.missile(missileXCoord, missileYCoord);
                        newBoard.display();
                        missileUses -= 1;
                        break;
                    case "print":
                        newBoard.print();
                        break;
                    case "drone":
                        if(droneUses == 0) {
                            System.out.println("You are out of drone uses.");
                            break;
                        } 
                        System.out.println("Would you like to scan a row or a column? Please enter an r for a row or a c for a column.");
                        String rowOrColumn = gameInput.nextLine();
                        while(!rowOrColumn.equals("r") && !rowOrColumn.equals("c")) {
                            System.out.println("Invalid input. Please enter r to scan a row or c to scan a column.");
                            System.out.print("Enter choice here: ");
                            rowOrColumn = gameInput.nextLine();
                        }
                        if(rowOrColumn.equals("r")) {
                            System.out.print("Please input the row you would like to scan: ");
                            int index = gameInput.nextInt();
                            gameInput.nextLine();
                            while(index >= 8 || index < 0) {
                                System.out.println("Invalid. Please enter valid row");
                                index = gameInput.nextInt();
                                gameInput.nextLine();
                            }
                            newBoard.drone(true, index);

                        } else {
                            System.out.print("Please input the column you would like to scan: ");
                            int index = gameInput.nextInt();
                            gameInput.nextLine();
                            while(index >= 8 || index < 0) {
                                System.out.println("Invalid. Please enter valid column");
                                index = gameInput.nextInt();
                                gameInput.nextLine();
                            }
                            newBoard.drone(false, index);
                        }
                        droneUses -= 1;
                        break;
                    default:
                        System.out.println("Please enter a valid choice.");

                }
            }
            System.out.println("Congratulations! You won in " + newBoard.getTurns() + " turns and " + newBoard.getTotalShots() + " amount of shots!" );
        } else { // Expert mode
            int droneUses = 2;
            int missileUses = 2;
            newBoard = new BattleboatsBoard(false);
            newBoard.randomBoatPlacement();
            newBoard.display();
            while(newBoard.getRemainingShipsExpert() != 0) {
                System.out.println("Enter f to fire a shot. Enter missile to fire a missile. Enter drone to launch a drone. Enter print to cheat and see where the boats are placed");
                System.out.print("Enter choice here: ");
                String action = gameInput.nextLine();
                switch(action) {
                    case "f":
                        System.out.print("Please enter an x coordinate: ");
                        int xCoord = gameInput.nextInt();
                        System.out.print("Please enter a y coordinate: ");
                        int yCoord = gameInput.nextInt();
                        gameInput.nextLine();
                        newBoard.fire(xCoord, yCoord);
                        newBoard.display();
                        break;
                    case "missile":
                        if(missileUses == 0) {
                            System.out.println("You are out of missile uses.");
                            break;
                        }
                        System.out.print("Please enter an x coordinate for the missile: ");
                        int missileXCoord = gameInput.nextInt();  
                        System.out.print("Please enter a y coordinate for the missile: ");
                        int missileYCoord = gameInput.nextInt();
                        gameInput.nextLine();
                        while(missileXCoord >= 12 || missileXCoord < 0 || missileYCoord >= 12 || missileYCoord < 0) {
                            System.out.println("Please enter valid coordinates.");
                            System.out.print("Please enter an x coordinate for the missile: ");
                            missileXCoord = gameInput.nextInt();
                            System.out.print("Please enter a y coordinate for the missile: ");
                            missileYCoord = gameInput.nextInt();
                            gameInput.nextLine();
                        }
                        newBoard.missile(missileXCoord, missileYCoord);
                        newBoard.display();
                        missileUses -= 1;
                        break;
                    case "print":
                        newBoard.print();
                        break;
                    case "drone":
                        if(droneUses == 0) {
                            System.out.println("You are out of drone uses.");
                            break;
                        } 
                        System.out.println("Would you like to scan a row or a column? Please enter an r for a row or a c for a column.");
                        String rowOrColumn = gameInput.nextLine();
                        while(!rowOrColumn.equals("r") && !rowOrColumn.equals("c")) {
                            System.out.println("Invalid input. Please enter r to scan a row or c to scan a column.");
                            System.out.print("Enter choice here: ");
                            rowOrColumn = gameInput.nextLine();
                        }
                        if(rowOrColumn.equals("r")) {
                            System.out.print("Please input the row you would like to scan: ");
                            int index = gameInput.nextInt();
                            gameInput.nextLine();
                            while(index >= 12 || index < 0) {
                                System.out.println("Invalid. Please enter valid row");
                                index = gameInput.nextInt();
                                gameInput.nextLine();
                            }
                            newBoard.drone(true, index);

                        } else {
                            System.out.print("Please input the column you would like to scan: ");
                            int index = gameInput.nextInt();
                            gameInput.nextLine();
                            while(index >= 12 || index < 0) {
                                System.out.println("Invalid. Please enter valid column");
                                index = gameInput.nextInt();
                                gameInput.nextLine();
                            }
                            newBoard.drone(false, index);
                        }
                        droneUses -= 1;
                        break;
                    default:
                        System.out.println("Please enter a valid choice.");

                }
            }
            System.out.println("Congratulations! You won in " + newBoard.getTurns() + " turns and " + newBoard.getTotalShots() + " amount of shots!" );
        }
    }
}