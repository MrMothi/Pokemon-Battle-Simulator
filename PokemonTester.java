/**
 * This is the tester class for the 
 * @author Mohit Sharma
 * Oct 27th 2020
 * @version 1
 */

//importing scanner and arraylist
import java.util.Scanner;
import java.util.ArrayList;

public class PokemonTester {
    
    //setting up the variables
    //Array for all the pokemon accesable by the user
    public static ArrayList<Pokemon> pokedeck = new ArrayList<Pokemon>();  
    //Array for all the pokemon accesable by the computer
    public static ArrayList<Pokemon> pokedeckPC = new ArrayList<Pokemon>();
    


    /**
     * This is the main method of the pokemon tester, this is where the algorithm for the pokemon 
     * choosing, battle and more is coded and run
     * @param args
     */
    public static void main(String [] args){
        
        //setting up scanner
        Scanner in = new Scanner(System.in);
        
        //setting the error variable
        int x = 0; //0 for false  1 for true  

        //Telling the user the intro -----
        //printing ascii art logo of pokemon
        printAscii(1);
        System.out.println();
        System.out.println();
        System.out.println("Welcome to Pokemon Fighting Simulator!");
        System.out.println("Today you will be fighting against the Randomizer1000");
        System.out.println("You will have to choose 3 pokemon  out of 8 pokemon");
        System.out.println("Then you will be in all out battle!");
        System.out.println("________________________________________________________________________");
        System.out.println();
        System.out.println("Firstly, please enter your name: ");
        String name = in.next();
        System.out.println("");
        System.out.println("Now "+ name + ", here are the availible pokemon, please select three:");
        System.out.println("");
        System.out.println("");
        
        //pokemon selection code-----
        //setting up the selectable pokemon
        setUpPokemon(); //calling the method to set up all the pokemon with all their moves
        printPokemon(); //Printing out all the pokemon
        System.out.println("________________________________________________________________________");
        System.out.println();

        //creating player object
        PokemonPlayer player = new PokemonPlayer(name);
        //creating the CPU object
        PokemonPlayer cpu = new PokemonPlayer("Randomizer1000");

        //creating choice variables to not get dupes
        int c1 = -1;
        int c2 = -1;
        int c3 = -1;

        //user input variable
        int userChoice = -1;

        //sentinel variable
        int valid = 0;

        //asking the user 3 choices and error checking
        for(int i = 0; i < 3; i++){
            valid = 0; //setting sentinel
            while (valid == 0){  //within integer range
                System.out.println("Please choose a pokemon (Enter an integer from 1-8, no duplicates): ");
                userChoice = in.nextInt();
                if(userChoice < 0 || userChoice > 9){    //telling wrong input msgs  this one is for out of range
                    System.out.println("Please enter a number between 0 and 9");
                }
                else if(userChoice == c1 || userChoice == c2 || userChoice == c3){  //if dupes
                    System.out.println("Please choose a pokemon which you havent choosen yet");
                }
                else{   //setting input to choices
                    if(i == 0){
                        c1 = userChoice;
                    }
                    else if(i == 1){
                        c2 = userChoice;
                    }
                    else{
                        c3 = userChoice;
                    }
                    valid = 1; //setting sentinel
                }
            }
        } 
        System.out.println("________________________________________________________________________");
        System.out.println("Great! These are the pokemon you have chosen: ");   

        //setting the users choices
        player.addPokemon(pokedeck.get(c1-1));
        player.addPokemon(pokedeck.get(c2-1));
        player.addPokemon(pokedeck.get(c3-1));

        //Printing their choices
        System.out.println();
        System.out.println(player);

        //resetting choice variables to not get dupes            
        c1 = -1;
        c2 = -1;
        c3 = -1;

        //setting up the pokedeckPC arrary and setting the pokemon for the pc
        setUpPokemonPC();

        //setting the computers choices  (reused userChoice variable)
        for(int i = 0; i < 3; i++){
            valid = 0; //resetting sentinel
            while(valid == 0){
                userChoice = (int)(Math.random() * 8) + 1;  //getting random integers from 1 to 8
                if(userChoice != c1 && i == 0){   //setting random to choices if not duplicates
                    c1 = userChoice;
                    cpu.addPokemon(pokedeckPC.get(c1-1));
                    valid++; //iterating the sentinel to break the while
                }
                else if(userChoice != c2 && userChoice != c1 && i == 1){
                    c2 = userChoice;
                    cpu.addPokemon(pokedeckPC.get(c2-1));
                    valid++;
                }
                else if(userChoice != c3 && userChoice != c2 && userChoice != c1 && i == 2){
                    c3 = userChoice;
                    cpu.addPokemon(pokedeckPC.get(c3-1));
                    valid++;
                }
            }
        }   

        //showing the user the enemy(CPU) pokemon
        System.out.println("________________________________________________________________________");
        System.out.println("Your opponent has chosen its own pokemon...");
        System.out.println("Here they are");
        System.out.println();
        System.out.println(cpu);

        //Battle code -----
        System.out.println();
        System.out.println("________________________________________________________________________");
        System.out.println("Now time to begin the battle...");
        printAscii(2); //printing battle ascii

        //setting the battle sentinel to 0
        int battleOver = 0;

        //setting the winner variable to 0, if its 1 then player wins, if its 2 then cpu wins
        int winner = 0;

        //setting current pokemon in use index variables for player and pc 
        int pokePlayer = 0;
        int pokeCpu = 0;

        //setting the move input variable number
        int moveChoice = -1;

        //setting up cpu move variable
        int moveCpu = -1;

        //running a loop of the battle until battle is over
        while(battleOver == 0){
            //Running if both sides have alive pokemon
            if(isPokemonAlive(cpu) && isPokemonAlive(player)){
                System.out.println("________________________________________________________________________"); //line break for each move
                System.out.println("|Randomizer1000|"); //printing name of cpu
                System.out.println();
                
                //printing opponent pokemon's ascii
                System.out.println(cpu.getPokemon(pokeCpu).printThisAscii());
                //printing opponent pokemon's health
                System.out.println(cpu.getPokemon(pokeCpu).printHealth());

                System.out.println();
                System.out.println("____________________________________"); //break between cpu and player printouts
                System.out.println("|" + player.getName() + "|"); //printing name of player
                System.out.println();

                //printing the player pokemon in play
                System.out.println(player.getPokemon(pokePlayer).printThisAscii());
                //printing out the pokemon's health
                System.out.println(player.getPokemon(pokePlayer).printHealth());
                //printing out the pokemon's moves
                System.out.println(player.getPokemon(pokePlayer));
                System.out.println();
                //asking the user 
                System.out.println("Choose a move to use (Enter an integer corresponding to the move numbers 1-4):");
                
                //asking the user for the move they want to use against the opponent
                while(moveChoice > 4 || moveChoice < 0){
                    moveChoice = in.nextInt();
                    //telling error msg if the input is incorrect
                    if(moveChoice > 4 || moveChoice < 0){
                        System.out.println("Please enter a valid move number (1-4)!");
                    }
                }

                //getting cpu moves (getting within 0 and 4)
                while(moveCpu > 4 || moveCpu < 0){
                    moveCpu = (int)(Math.random()*4) + 1;
                }

                System.out.println();
                System.out.println("__________MOVES__________"); //printing out divider
                System.out.println();


                //carrying out the moves     //All of the commented code can be uncommented and also be used, the current implementation is more efficient
                //accounting for speed
                System.out.println();
                if(player.getPokemon(pokePlayer).getSpeed() > cpu.getPokemon(pokeCpu).getSpeed()){ //if player pokemon is faster
                    //carrying out the move by the player
                    //printing the move choice
                    //System.out.println(player.getPokemon(pokePlayer).getName() + " uses " + player.getPokemon(pokeCpu).getMoveName(moveChoice-1));
                    // first the receiving pokemon      next the refrences to the attacking pokemon
                    //cpu.getPokemon(pokeCpu).applyDamage(player.getPokemon(pokePlayer).getMoveDamage(moveChoice-1), player.getPokemon(pokePlayer).getMoveEffect(moveChoice-1), player.getPokemon(pokePlayer));
                    

                    //doing player move
                    player.getPokemon(pokePlayer).attack(cpu.getPokemon(pokeCpu), moveChoice-1);
                    System.out.println();
                    System.out.println();
                    
                    //printing the move of the cpu
                    //System.out.println(cpu.getPokemon(pokeCpu).getName() + " uses " + cpu.getPokemon(pokeCpu).getMoveName(moveCpu-1));
                    //carrying out cpu move
                    //player.getPokemon(pokePlayer).applyDamage(cpu.getPokemon(pokeCpu).getMoveDamage(moveCpu-1), cpu.getPokemon(pokeCpu).getMoveEffect(moveCpu-1), cpu.getPokemon(pokeCpu));
                    

                    //doing cpu move
                    cpu.getPokemon(pokeCpu).attack(player.getPokemon(pokePlayer), moveCpu-1); 
                    System.out.println();


                }
                else{ // if the cpu is faster
                    //printing the move of the cpu
                    //System.out.println(cpu.getPokemon(pokeCpu).getName() + " uses " + cpu.getPokemon(pokeCpu).getMoveName(moveCpu-1));
                    //carrying out cpu move
                    //player.getPokemon(pokePlayer).applyDamage(cpu.getPokemon(pokeCpu).getMoveDamage(moveCpu-1), cpu.getPokemon(pokeCpu).getMoveEffect(moveCpu-1), cpu.getPokemon(pokeCpu));
                    
                    
                    //doing cpu move
                    cpu.getPokemon(pokeCpu).attack(player.getPokemon(pokePlayer), moveCpu-1);  
                    System.out.println();
                    System.out.println();

                    //carrying out the move by the player
                    //printing the move choice
                    //System.out.println(player.getPokemon(pokePlayer).getName() + " uses " + player.getPokemon(pokeCpu).getMoveName(moveChoice-1));
                    // first the receiving pokemon      next the refrences to the attacking pokemon
                    //cpu.getPokemon(pokeCpu).applyDamage(player.getPokemon(pokePlayer).getMoveDamage(moveChoice-1), player.getPokemon(pokePlayer).getMoveEffect(moveChoice-1), player.getPokemon(pokePlayer));
                    
                    
                    //doing player move
                    player.getPokemon(pokePlayer).attack(cpu.getPokemon(pokeCpu), moveChoice-1);
                    System.out.println();
                }

                //checking if any of the pokemon passes out
                //checking if the cpu pokemon passes out
                if(cpu.getPokemon(pokeCpu).getHealthLeft() <= 0){
                    //telling the user that the pokemon has passed out
                    System.out.println(cpu.getPokemon(pokeCpu).getName() + " has passed out!");
                    System.out.println();
                    pokeCpu++; //increasing the pokemon index
                    if(pokeCpu > 2){ //if there is no pokemon left, the win variable is set to player win
                        winner = 1;
                        battleOver = 1; //ending the main loop
                    }
                }
                //checking if the player pokemon passes out
                else if(player.getPokemon(pokePlayer).getHealthLeft() <= 0){
                    //telling the user that the pokemon has passed out
                    System.out.println(player.getPokemon(pokePlayer).getName() + " has passed out!");
                    System.out.println();
                    pokePlayer++; //increasing the pokemon index
                    if(pokePlayer > 2){ //if there is no pokemon left, the win variable is set to cpu win
                        winner = 2;
                        battleOver = 1; //ending the main loop
                    }
                }

                //resetting moveChoice
                moveChoice = -1;
                //resetting moveCpu
                moveCpu = -1;
            }


            else{ //if one side of the battle has no alive pokemon
                if(isPokemonAlive(cpu) == false){ //ending battle if player wins
                    battleOver = 1;
                    winner = 1;
                }
                else{ //ending battle if cpu wins
                    battleOver = 1;
                    winner = 2; 
                }
            }
        }
    

        //Printing out the winner and other text ______________________________________________________________Do this and all the ascii implementation along with error checking and stuff
        System.out.println();
        System.out.println("________________________________________________________________________"); //line break 
        System.out.println();
        //determining the winner string and then printing it, then finishing the main method
        String winnerName = "";
        if(winner == 1){
            winnerName = player.getName();
            printAscii(3); //printing winner ascii art
            System.out.println();
        }   
        else{
            winnerName = "Ranomizer1000";
            printAscii(4); //printing winner ascii art
            System.out.println();
        }
        System.out.println("THE WINNER IS " + winnerName);
        System.out.println("Please be free to play again!");
        System.out.println("________________________________________________________________________"); //line break 
    }


    /**
     * This method checks and tells if the pokemonPlayer class has any alive pokemon
     * @param a is the pokemonplayer class being checked
     * @return boolean is true if pokemon is alive and vice versa
     */
    public static boolean isPokemonAlive(PokemonPlayer a){
        if(a.getPokemon(0).getHealthLeft() > 0  || a.getPokemon(1).getHealthLeft() > 0 || a.getPokemon(2).getHealthLeft() > 0){
            return true;
        }
        return false;
    }


    /**
     * This method sets upp all the pokemon for the player along with their stats and moves
     */
    public static void setUpPokemon(){
        //setting up the random health variable
        int rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up pikachu 
        Pokemon pikachu = new Pokemon("Pikachu", "Electric", rand, rand, 95, 8);
        pikachu.addMove("Thunder Punch", 80, 0);
        pikachu.addMove("Thunder Shock", 60, 0);
        pikachu.addMove("Scratch   ", 80, 0);
        pikachu.addMove("Thunder Bolt", 30, 2);
        pokedeck.add(pikachu);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up weedle
        Pokemon weedle = new Pokemon("Weedle", "Bug Poison", rand, rand, 30, 5);
        weedle.addMove("Twinneedle", 80, 0);
        weedle.addMove("Pin Missle", 90, 0);
        weedle.addMove("Poison Sting", 20, 3);
        weedle.addMove("String Shot", 80, 0);
        pokedeck.add(weedle);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up charizard
        Pokemon charmander = new Pokemon("Charmander", "Fire", rand, rand, 80, 6);
        charmander.addMove("Flame Thrower", 80, 1);
        charmander.addMove("Ember   ", 80, 1);
        charmander.addMove("Scratch   ", 80, 0);
        charmander.addMove("Roar    ", 20, 0);
        pokedeck.add(charmander);

        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50


        //setting up squirtle
        Pokemon squirtle = new Pokemon("Squirtle", "Water", rand, rand, 85, 7);
        squirtle.addMove("Water Cannon", 80, 0);
        squirtle.addMove("Bubble   ", 20, 2);
        squirtle.addMove("Raindance", 60, 0);
        squirtle.addMove("Waterfall", 40, 0);
        pokedeck.add(squirtle);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up bulbasaur
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass Poison", rand, rand, 50, 5);
        bulbasaur.addMove("Poison Powder", 20, 3);
        bulbasaur.addMove("Absorb   ", 20, 2);
        bulbasaur.addMove("Mega Drain", 100, 0);
        bulbasaur.addMove("Razor Leaf", 55, 0);
        pokedeck.add(bulbasaur);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up mewtwo
        Pokemon mewtwo = new Pokemon("Mewtwo", "Psychic", rand, rand, 90, 5);
        mewtwo.addMove("Psybeam   ", 65, 0);
        mewtwo.addMove("Psychic   ", 10, 2);
        mewtwo.addMove("Confusion", 0, 2);
        mewtwo.addMove("Light Screen", 40, 0);
        pokedeck.add(mewtwo);

        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up machop
        Pokemon machop = new Pokemon("Machop", "Fighting", rand, rand, 70, 10);
        machop.addMove("High Jump Kick", 90, 0);
        machop.addMove("Mach Punch", 60, 0);
        machop.addMove("Triple Kick", 95, 0);
        machop.addMove("Dynamic Punch", 30, 2);
        pokedeck.add(machop);

        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up ghastly 
        Pokemon ghastly = new Pokemon("Ghastly", "Ghost Poison", rand, rand, 60, 11);
        ghastly.addMove("Poison Fang", 20, 3);
        ghastly.addMove("Shadow Claw", 85, 0);
        ghastly.addMove("Shadow Sneak", 45, 0);
        ghastly.addMove("Hex        ", 10, 2);
        pokedeck.add(ghastly);
    }

    
    /**
     * This is a duplicate method as the last one but this time it sets upp all the pokemon for the pc along with their stats and moves
     */
    public static void setUpPokemonPC(){
        //setting up the random health variable
        int rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up pikachu 
        Pokemon pikachu = new Pokemon("Pikachu", "Electric", rand, rand, 95, 8);
        pikachu.addMove("Thunder Punch", 80, 0);
        pikachu.addMove("Thunder Shock", 60, 0);
        pikachu.addMove("Scratch   ", 80, 0);
        pikachu.addMove("Thunder Bolt", 30, 2);
        pokedeckPC.add(pikachu);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up weedle
        Pokemon weedle = new Pokemon("Weedle", "Bug Poison", rand, rand, 30, 5);
        weedle.addMove("Twinneedle", 80, 0);
        weedle.addMove("Pin Missle", 90, 0);
        weedle.addMove("Poison Sting", 20, 3);
        weedle.addMove("String Shot", 80, 0);
        pokedeckPC.add(weedle);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up charizard
        Pokemon charmander = new Pokemon("Charmander", "Fire", rand, rand, 80, 6);
        charmander.addMove("Flame Thrower", 80, 1);
        charmander.addMove("Ember   ", 80, 1);
        charmander.addMove("Scratch   ", 80, 0);
        charmander.addMove("Roar    ", 20, 0);
        pokedeckPC.add(charmander);

        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50


        //setting up squirtle
        Pokemon squirtle = new Pokemon("Squirtle", "Water", rand, rand, 85, 7);
        squirtle.addMove("Water Cannon", 80, 0);
        squirtle.addMove("Bubble   ", 20, 2);
        squirtle.addMove("Raindance", 60, 0);
        squirtle.addMove("Waterfall", 40, 0);
        pokedeckPC.add(squirtle);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up bulbasaur
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass Poison", rand, rand, 50, 5);
        bulbasaur.addMove("Poison Powder", 20, 3);
        bulbasaur.addMove("Absorb   ", 20, 2);
        bulbasaur.addMove("Mega Drain", 100, 0);
        bulbasaur.addMove("Razor Leaf", 55, 0);
        pokedeckPC.add(bulbasaur);
        
        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up mewtwo
        Pokemon mewtwo = new Pokemon("Mewtwo", "Psychic", rand, rand, 90, 5);
        mewtwo.addMove("Psybeam   ", 65, 0);
        mewtwo.addMove("Psychic   ", 10, 2);
        mewtwo.addMove("Confusion ", 0, 2);
        mewtwo.addMove("Light Screen", 40, 0);
        pokedeckPC.add(mewtwo);

        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up machop
        Pokemon machop = new Pokemon("Machop", "Fighting", rand, rand, 70, 10);
        machop.addMove("High Jump Kick", 90, 0);
        machop.addMove("Mach Punch", 60, 0);
        machop.addMove("Triple Kick", 95, 0);
        machop.addMove("Dynamic Punch", 30, 2);
        pokedeckPC.add(machop);

        rand = (int)(Math.random() * 6) + 3;
        rand *= 50; //multiplying health by 50

        //setting up ghastly 
        Pokemon ghastly = new Pokemon("Ghastly", "Ghost Poison", rand, rand, 60, 11);
        ghastly.addMove("Poison Fang", 20, 3);
        ghastly.addMove("Shadow Claw", 85, 0);
        ghastly.addMove("Shadow Sneak", 45, 0);
        ghastly.addMove("Hex        ", 10, 2);
        pokedeckPC.add(ghastly);
    }


    /**
     * This method prints out the availible pokemon
     */
    public static void printPokemon(){
        for(int i = 0; i < pokedeck.size(); i++){
            System.out.print("Pokemon " + (i+1) + ":\n");
            System.out.print(pokedeck.get(i));
            System.out.println();
        }
    }


    /**
     * this is the ascii method for this class
     * @param is the call for the spefic ascii art
     */
    public static void printAscii(int num){
        if(num == 1){   //printing pokemon
            System.out.println("                                  ,'\\");
            System.out.println("    _.----.        ____         ,'  _\\   ___    ___     ____");
            System.out.println("_,-'       `.     |    |  /`.   \\,-'    |   \\  /   |   |    \\  |`.");
            System.out.println("\\      __    \\    '-.  | /   `.  ___    |    \\/    |   '-.   \\ |  |");
            System.out.println(" \\.    \\ \\   |  __  |  |/    ,','_  `.  |          | __  |    \\|  |");
            System.out.println("   \\    \\/   /,' _`.|      ,' / / / /   |          ,' _`.|     |  |");
            System.out.println("    \\     ,-'/  /   \\    ,'   | \\/ / ,`.|         /  /   \\  |     |");
            System.out.println("     \\    \\ |   \\_/  |   `-.  \\    `'  /|  |    ||   \\_/  | |\\    |");
            System.out.println("      \\    \\ \\      /       `-.`.___,-' |  |\\  /| \\      /  | |   |");
            System.out.println("       \\    \\ `.__,'|  |`-._    `|      |__| \\/ |  `.__,'|  | |   |");
            System.out.println("        \\_.-'       |__|    `-._ |              '-.|     '-.| |   |");
            System.out.println("                                `'                            '-._|");
            
            
        }
        else if(num == 2){ //printing battle
            System.out.println("   ___       _   _   _      ");
            System.out.println("  / __\\ __ _| |_| |_| | ___ ");
            System.out.println(" /__\\/// _` | __| __| |/ _ \\");
            System.out.println("/ \\/  \\ (_| | |_| |_| |  __/");
            System.out.println("\\_____/\\__,_|\\__|\\__|_|\\___|");
            System.out.println("");
        }

        else if(num == 3){ //printing winner
            System.out.println(" _    _ _____ _   _  _   _  ___________ ");
            System.out.println("| |  | |_   _| \\ | || \\ | ||  ___| ___ \\");
            System.out.println("| |  | | | | |  \\| ||  \\| || |__ | |_/ /");
            System.out.println("| |/\\| | | | | . ` || . ` ||  __||    / ");
            System.out.println("\\  /\\  /_| |_| |\\  || |\\  || |___| |\\ \\ ");
            System.out.println(" \\/  \\/ \\___/\\_| \\_/\\_| \\_/\\____/\\_| \\_|");
        }

        else if(num == 4){ //printing loser
            System.out.println(" _ ");
            System.out.println("| |     ");
            System.out.println("| |     ___   ___  ___  ___ _ __ ");
            System.out.println("| |    / _ \\ / _ \\/ __|/ _ \\ '__|");
            System.out.println("| |___| (_) | (_) \\__ \\  __/ | ");
            System.out.println("\\_____/\\___/ \\___/|___/\\___|_|  ");
            System.out.println("");
        }
    } 
}//end of code
