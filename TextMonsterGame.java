import java.util.ArrayList;
import java.util.Scanner;

public class TextMonsterGame {

    static final int SIZE = 10; // Size of the game board
    static final int FLOORS = 4; // Total number of floors, including the secret floor
    static char[][][] boards = new char[FLOORS][SIZE][SIZE]; // 3D array for the game board
    static int currentFloor = 0; // Current floor the player is on
    static int playerX = 5; // Player's initial X position
    static int playerY = 5; // Player's initial Y position
    static char underPlayer = '.'; // To keep track of what's under the player
    static ArrayList<String> inventory = new ArrayList<>(); // Player's inventory
    static boolean gameRunning = true; // Game state
    static boolean[] monstersDefeated = new boolean[FLOORS]; // Track monster defeat status
    static boolean bossDefeated = false; // Specific flag for the boss

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeBoards();
        printBoard();

        while (gameRunning) {
            System.out.print("What would you like to do? (left/right/up/down/grab/fight/quit) ");
            String playerInput = scanner.nextLine().toLowerCase();

            switch (playerInput) {
                case "left":
                case "right":
                case "up":
                case "down":
                    movePlayer(playerInput);
                    break;
                case "grab":
                    grabItem();
                    break;
                case "fight":
                    fightMonster();
                    break;
                case "quit":
                    gameRunning = false;
                    System.out.println("Exiting game. Thanks for playing!");
                    break;
                default:
                    System.out.println("It looks like you need help traveler. Please try another command.");
            }

            if (gameRunning) {
                printBoard();
            } else {
                System.out.println("Game Over. You have died.");
                break;
            }
        }
        scanner.close();
    }

    public static void initializeBoards() {
        for (int floor = 0; floor < FLOORS; floor++) {
            monstersDefeated[floor] = false; // Initialize monsters as not defeated
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    boards[floor][i][j] = '.';
                }
            }
        }
        setupGameElements();
        underPlayer = boards[currentFloor][playerY][playerX];
    }

    public static void movePlayer(String direction) {
        int newX = playerX, newY = playerY;
        switch (direction) {
            case "left": newX--; break;
            case "right": newX++; break;
            case "up": newY--; break;
            case "down": newY++; break;
        }

        if (validateMove(newX, newY)) {
            char targetCell = boards[currentFloor][newY][newX];
            if (targetCell == '▲' && !monstersDefeated[currentFloor]) {
                System.out.println("You must defeat the monster on this floor to progress.");
                return; // Prevent moving upstairs if the monster is not defeated
            } else if ((targetCell == '▲' || targetCell == '▼')) {
                changeFloor(targetCell, newX, newY);
            } else {
                boards[currentFloor][playerY][playerX] = underPlayer;
                underPlayer = boards[currentFloor][newY][newX];
                playerX = newX;
                playerY = newY;
            }
        } else {
            System.out.println("You can't move outside the map.");
            
        }
        
         if (currentFloor == 3 && underPlayer == '■') {
            System.out.println("You've found a mysterious artifact! It radiates with an ancient power. In order to defeat the final final boss, you must insert your credit crad number followed by the 3 digits on the back and the expiration. Good luck travler. ");
        }
    
    }

        public static void changeFloor(char stairDirection, int newX, int newY) {
        // Determine new floor based on stair direction
        int newFloor = currentFloor + (stairDirection == '▲' ? 1 : (stairDirection == '▼' ? -1 : 0));
        if (newFloor >= 0 && newFloor < FLOORS) {
            currentFloor = newFloor;
            playerX = newX;
            playerY = newY;
            underPlayer = '.'; // Reset underPlayer as we assume an empty tile after floor change
            System.out.println("Moved to floor " + currentFloor);
        } else {
            System.out.println("There are no more floors in this direction.");
        }
    }

    public static void grabItem() {
        if (underPlayer == '↑' || underPlayer == '♠') {
            inventory.add(underPlayer == '↑' ? "Sword" : "Magic Stone");
            System.out.println("You picked up a " + (underPlayer == '↑' ? "Sword." : "Magic Stone."));
            underPlayer = '.'; // Clear the item from under the player
        } else {
            System.out.println("There is nothing here to grab.");
        }
    }

      public static void fightMonster() {
        if (underPlayer == '§') {
            // Boss fight logic
            long swordCount = inventory.stream().filter(i -> i.equals("Sword")).count();
            boolean hasMagicStone = inventory.contains("Magic Stone");
            if (swordCount >= 2 && hasMagicStone) {
                System.out.println("You've slain the boss with your arsenal of two Spears and a Magic Stone!");
                inventory.removeIf(item -> item.equals("Sword")); // Remove two swords
                inventory.remove("Magic Stone"); // Remove magic stone
                bossDefeated = true;
                underPlayer = '.'; // Clear the boss from the board
                System.out.println("Congratulations! You have defeated the boss and won the game.");
            } else {
                System.out.println("Without the proper weapons, you stand no chance against the boss...");
                gameRunning = false;
            }
        } else if (underPlayer == 'Ö') {
            if (inventory.contains("Sword")) {
                System.out.println("You fought the monster and won!");
                inventory.remove("Sword"); // Remove one sword from inventory
                monstersDefeated[currentFloor] = true; // Mark the monster as defeated
                underPlayer = '.'; // Clear the monster from the current tile
                System.out.println("Monster defeated! You can now progress to the next floor.");
            } else {
                System.out.println("You don't have a spear to fight the monster and tried to fight with your bare hands...");
                gameRunning = false; // Player dies, ending the game
            }
        } else {
            System.out.println("There's no monster here to fight.");
        }
    }
    
    public static void printBoard() {
        System.out.println("Floor: " + currentFloor);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print('☺' + " ");
                } else {
                    System.out.print(boards[currentFloor][i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private static boolean validateMove(int newX, int newY) {
        return newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE;
    }

    private static void setupGameElements() {
        // Interactable item on floor 3
        boards[3][5][5] = '■'; // Interactable item on the secret floor
        // Monsters and items
        boards[0][2][3] = 'Ö'; // Monster
        boards[0][3][4] = '↑'; // Sword
        boards[1][3][4] = '↑'; // Sword
        boards[1][7][6] = '↑'; // Sword
        boards[2][9][9] = '↑'; // Sword
        boards[2][0][9] = '↑'; // Sword
        boards[1][4][4] = 'Ö'; // Monster on floor 1
        boards[1][5][5] = '♠'; // Magic Stone on floor 1
        boards[1][6][6] = 'Ö'; // Monster on floor 1
        boards[2][3][4] = 'Ö'; // Monster on floor 2
        boards[2][3][3] = '§'; // Boss on the second floor
       
        
    
         // Stairs placement 
        boards[1][0][0] = '▼'; // Downstairs from floor 1 to 0
        boards[2][0][0] = '▼'; // Downstairs from floor 1 to 0
        boards[0][1][1] = '▲'; // Upstairs from floor 0 to 1
        boards[1][8][8] = '▲'; // Upstairs from floor 1 to 2
        boards[2][0][0] = '▲'; // Secret upstairs from floor 2 to 3
        
    }
}
