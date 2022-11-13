/**
 * This is the player class for the pokemon battle game, this class works with Pokemon and PokemonTester 
 * @author Mohit Sharma
 * @version 1
 */


import java.util.ArrayList;

public class PokemonPlayer {
    
    //Array for the three pokemon in this player
    private ArrayList<Pokemon> pokedeck = new ArrayList<Pokemon>();
    //variable for the amount of pokemon that are selected
    private int index = 0;
    //instance variable for the name of the player
    private String name = "";


    /**
     * This is the default constructor for this class
     */
    public PokemonPlayer(){
        name = "No Name";
    }


    /**
     * This is overload constructor of Pokemon which sets the name
     * @param name of user
     */
    public PokemonPlayer(String name){
        this.name = name; //setting the name
    }

    
    /**
     * Method returns the name
     * @return name which is the name of the player
     */
    public String getName(){
        return name;
    }


    /**
     * this method adds the pokemon that the user selects in the tester class
     * @param p is the pokemon that was generated in the tester class
     */
    public void addPokemon(Pokemon p){
        //creating a new pokemon identical to the one passed
        if(index == 0){
            Pokemon a = p; //new Pokemon(p.getName(), p.getType(), p.getHealthTotal(), p.getHealthLeft(), p.getSpeed(), p.getDodgeChance())  -- excess code for adding copy
            index++;
            pokedeck.add(a);
        }
        else if(index == 1){
            Pokemon b = p;
            index++;
            pokedeck.add(b);
        }
        else if(index == 2){
            Pokemon c = p; 
            index++;
            pokedeck.add(c);
        }
    }


    /**
     * This method lets the Tester class access a pokemon at the index
     * @param index of the pokemon of the player pokedeck
     * @return the pokemon which is requested
     */
    public Pokemon getPokemon(int index){
        return pokedeck.get(index);
    }


    /**
     * this method sets a pokemon to the index
     * @param index is tht index of the pokemon
     * @param a is the pokemon being sent to be stored
     */
    public void setPokemon(int index, Pokemon a){
        pokedeck.set(index, a);
    }

    /**
     * This method prints out the pokemon in the player's pokedeck
     */
    public String toString(){
        String out = "";    
        for(int i = 0; i < 3; i++){    //setting the string then returning it
            out += pokedeck.get(i).toString() + "\n"; //calling the to string of every pokemon
            out += pokedeck.get(i).printThisAscii(); //adding the pokemon ascii
            out += "\n";
        }
        return out;
    }

    

}//end of program
