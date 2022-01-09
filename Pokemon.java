/**
 * This is the Pokemon class which is the base class for all the pokemon being 
 * used in the PokemonMain class 
 * @author Mohit Sharma
 * Oct 27 2020
 * @version 1
 */
import java.util.*; //importing all classes
public class Pokemon{

    //Variables------------------------------------

    //initializing the instance variables
    //Three array lists for the 4 moves (Each index is a different move, all of one index is one move for the 3 arrays)
    private ArrayList<String> moveName = new ArrayList<String>(); //name of move
    private ArrayList<Integer> moveDamage = new ArrayList<Integer>(); //damage of move
    private ArrayList<Integer> moveEffect = new ArrayList<Integer>();  //0 for no effect //1 for burn //2 for sleep //3 poison 
    private String name;//name of the pokemon
    private String type; //Type of the pokemon
    private int healthTotal; //total health of the pokemon
    private int healthLeft; //health left of pokemon
    private int speed; //speed stat of the pokemon
    private int dodgeChance; //dodge chance of the pokemon
    private String effect = "none"; //this is the variable for keeping track of which effect is on the pokemon
    private int counter = 0;  //this variable is for keeping in track how long an effect lasts, 


    //Constructors------------------------------------

    /**
     * This is the default constructor for a pokemon
     */
    public Pokemon(){
        name = "No Name";
        type = "No Type";
        healthTotal = 1;   //setting all to one to not trip up if statements
        healthLeft = 1;
        speed = 1;
        dodgeChance = 1;
    }

    /**
     * This is the proper constructor for the pokemon
     * @param name name of the pokemon
     * @param type type of the pokemon
     * @param healthTotal this is the max health of the pokemon
     * @param healthLeft this is the given health left of the pokemon
     * @param speed This is the given speed of the pokemon
     * @param dodgeChance this is the dodge chance given by the player (higher the better)
     */
    public Pokemon(String name, String type, int healthTotal, int healthLeft, int speed, int dodgeChance){
        this.name = name;
        this.type = type;
        this.healthTotal = healthTotal;
        this.healthLeft = healthLeft;
        this.speed = speed;
        this.dodgeChance = dodgeChance;
    } 

    //POKEMON WORKS methods------------------------------------
    
    /**
     * This method sets up the information about moves in the arrays
     * @param name is the name of the move
     * @param damage is the damage of the move
     * @param effect is the effect that the move applies
     */
    public void addMove(String name, int damage, int effect){
        moveName.add(name);
        moveDamage.add(damage);
        moveEffect.add(effect);
    }


    /**
     * this method carries out the attack of the pokemon
     * @param other is the refrence to the other pokemon
     * @param move is the index of the move chosen by the user
     */
    public void attack(Pokemon other, int move){
        reduceCounter(); //calling the reduce counter method for effects
        //calling for the damage and effect values of the choosen move of the attacking pokemon
        if(this.getEffect().equals("sleep") == false){  //if not sleeping then applying move
            System.out.println(this.getName() + " uses " + getMoveName(move));
            other.applyDamage(getMoveDamage(move), getMoveEffect(move), this); //applying damage to other pokemon
        }
        else{ //if the pokemon is asleep, telling the user and not doing move
            System.out.println(this.getName() + " is sleeping! Cannot do move");
        }
    }
      

    /**
     * this method applies the damage to the pokemon
     * @param damage is the amount of damage of the move
     * @param effectnum is the integer relating to what effect comes with the move
     */
    public void applyDamage(int damage, int effectnum, Pokemon other){
        reduceCounter(); //calling the reduce counter method for effects
        int chance = (int)(Math.random() * dodgeChance);   //generating random chance    (int) (Math.Random() * range) + min
        //checking if the pokemon is sleeping
        if(this.getEffect().equals("sleep")){  //if sleeping then applying the damage without dodge chance 
            System.out.println(this.getName() + " is still sleeping"); //telling the user
            //applying damage
            healthLeft -= damage*(typeChecker(other)); //reducing the health
        }
        //if the pokemon is not sleeping
        else if(chance < (dodgeChance/2+2)){   //if the chance varible is less than 1/2 -1 of dodge chance then the damage is applied    
            //setting variable to the damage multiplier based on type
            double typeMultiplier = typeChecker(other);
            healthLeft -= damage*typeMultiplier; //reducing the health
            System.out.println(this.getName() + " has taken " + (damage*typeMultiplier) + " damage!"); //telling the user the outcome
            applyNewEffect(effectnum); //calling the method which applies the effects
        }
        else{ //if the pokemon has dodged the attack
            System.out.println(this.getName() + " has dodged the attack!");
        }
    }


    /**
     * This method apples the effect to the pokemon aswell as sets the timer
     * burn is short and high damage
     * sleep is short length and no damage
     * poison is longer in length with low damage
     * @param effectnum this is the number corresponding with the effect
     */
    public void applyNewEffect(int effectnum){  //0 for no effect //1 for burn //2 for sleep //3 poision 
        if(effectnum == 1){   //if the effect num is 1 then the pokemon is set to burn and sends a message
            effect = "burn";
            counter = 2; //setting the counter to 2, as this would run for 2 turns
            System.out.println(this.getName() + " is burning!");
        }
        else if(effectnum == 2){ //if 2 then sleeping
            effect = "sleep";
            counter = 3; // setting the counter to two 
            System.out.println(this.getName() + " is sleeping!");
        }
        else if (effectnum == 3){ //if 3 then poison
            effect = "poison";
            counter = 4; //setting the counter to 4
            System.out.println(this.getName() + " was poisoned!");
        }
    }


    /**
     * this method is for working out the effects, it increments the counter for the duration of the effects
     * the method also applies the effect aswell as removes the effect
     * the respective outputs are also done by this method
     */
    public void reduceCounter(){
        if(counter > 0){ //reducing counter only if the counter is above 0
            counter--;
            if(counter == 0){   //clearing effect if count is 0, then telling the user about the effect wearing off
                if(effect.equals("burn")){
                    System.out.println(this.getName() + " stopped burning!");
                }
                else if(effect.equals("sleep")){
                    System.out.println(this.getName() + " woke up!");
                }
                else if(effect.equals("poison")){
                    System.out.println(this.getName() + " stopped being poisoned!");
                }
                effect = "none";
            }
            else{ //if the effect is still in effect then the damages is applied
                if(effect.equals("burn")){
                    System.out.println(this.getName() + " Took burning damage!");
                    healthLeft -= 40; // reduces 40 damage with burning
                }
                else if(effect.equals("sleep")){
                    //no damage on this effect
                }
                else if(effect.equals("poison")){
                    System.out.println(this.getName() + " stopped being poisoned!");
                    healthLeft -= 20; //reduces 20 damage with poison
                }
            }
        }   
    }


    /**
     * This method prints out the pokemon with its type, move, and more
     */
    public String toString(){
        String pokemonDisplay = "| " + this.getName() + " |  Type: " + this.getType() + "\n";
        for(int i = 0; i < 4; i++){
            String effectThing = ""; //string for the effect 
            if(getMoveEffect(i) == 0){   //determining and setting the string for the effect
                effectThing = "none";
            }  
            else if(getMoveEffect(i) == 1){
                effectThing = "burn";
            }
            else if(getMoveEffect(i) == 2){
                effectThing = "sleep";
            }
            else if(getMoveEffect(i) == 3){
                effectThing = "poison";
            }
            pokemonDisplay += "Move " + (i+1) + ": " + getMoveName(i) + "\t\t Damage: " + getMoveDamage(i) + "\t Effect: " + effectThing + "\n"; //setting up the move info line
        }
        return pokemonDisplay; //returning the line
    }


    /**
     * This method prints out the health of the pokemon
     * @return the string of the pokemon health
     */
    public String printHealth(){
        return this.getName() + " has " + this.getHealthLeft() + "/" + this.getHealthTotal() + " health";
    }


    /**
     * This method calculates the damage multiplier
     * @param other pokemon in order to get the type
     * @return damageMultiplier is the damage multuplier
     */
    public double typeChecker(Pokemon other){
        double damageMultiplier = 1;//variable for damage multiplier set to 1 for regular damage
        //checking for poison type & strong against bug, grass and fighting weak against phychic
        //(This is in separate if as this is the only double type)
        if(this.getType().indexOf("Poison") > -1){
            if(other.getType().indexOf("Bug") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Grass") >  -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Fighting") >  -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Psychic") >  -1){
                damageMultiplier *= 2;
            }
        }


        //checking for electric type (Strong to electric)    
        if(this.getType().indexOf("Electric") > -1){
            if(other.getType().indexOf("Electric") > -1){
                damageMultiplier *= .5;
            }
        }


        //checking for bug type (Weak to fire and strong against grass and fighting)
        if(this.getType().indexOf("Bug") > -1){
            if(other.getType().indexOf("Fire") > -1){   
                damageMultiplier *= 2;
            }
            if(other.getType().indexOf("Grass") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Fighting") > -1){
                damageMultiplier *= .5;
            }
        }


        //checking for fire type (strong against bug fire and grass weak to water)
        if(this.getType().indexOf("Fire") > -1){
            if(other.getType().indexOf("Bug") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Fire") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Grass") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Water") > -1){
                damageMultiplier *= 2;
            }
        }


        //checking for water type(Strong against fire and water weak to electric and grass)
        if(this.getType().indexOf("Water") > -1){
            if(other.getType().indexOf("Fire") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Water") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Electric") > -1){
                damageMultiplier *= 2;
            }
            if(other.getType().indexOf("Grass") > -1){
                damageMultiplier *= 2;
            }
        }


        //checking for grass type(Strong against water and grass weak to bug poison and fire)
        if(this.getType().indexOf("Grass") > -1){
            if(other.getType().indexOf("Water") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Grass") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Bug") > -1){
                damageMultiplier *= 2;
            }
            if(other.getType().indexOf("Poison") > -1){
                damageMultiplier *= 2;
            }
            if(other.getType().indexOf("Fire") > -1){
                damageMultiplier *= 2;
            }
        }


        //checking for fighting type(Strong against bug, weak to psychic and resistant to ghost)
        if(this.getType().indexOf("Fighting") > -1){
            if(other.getType().indexOf("Bug") > -1){
                damageMultiplier *= 2;
            }
            if(other.getType().indexOf("Psychic") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Ghost") > -1){
                damageMultiplier *= 0;
            }
        }


        //checking for ghost type(Strong against bug poison, weak to ghost and resistant to fighting)
        if(this.getType().indexOf("Ghost") > -1){
            if(other.getType().indexOf("Bug") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Poison") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Ghost") > -1){
                damageMultiplier *= 2;
            }
            if(other.getType().indexOf("Psychic") > -1){
                damageMultiplier *= 0;
            }
        }


        //checking for psychic type(Strong against fighting and psychic weak to bug and ghost)        
        if(this.getType().indexOf("Psychic") > -1){
            if(other.getType().indexOf("Fighting") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Psychic") > -1){
                damageMultiplier *= .5;
            }
            if(other.getType().indexOf("Bug") > -1){
                damageMultiplier *= 2;
            }
            if(other.getType().indexOf("Ghost") > -1){
                damageMultiplier *= 2;
            }
        }

        //Printing then returning calculated multiplier
        System.out.println("Damage was multiplied " + damageMultiplier + " times");
        return damageMultiplier;
    }
    



    //getters for the list variables------------------------------------

    /**
     * This method returns the selected move name
     * @param index of the move
     * @return name of the move
     */
    public String getMoveName(int index){
        return moveName.get(index);
    }


    /**
     * this method returns the damage of the selected move
     * @param index of the move
     * @return damage of the move
     */
    public int getMoveDamage(int index){
        return moveDamage.get(index);
    }

    /**
     * this method returns the effect as an integer of the selected move
     * @param index of the move
     * @return int value of the effect
     */
    public int getMoveEffect(int index){
        return moveEffect.get(index);
    }


    // getters and setters for non list variables------------------------------------
    //-------getters

    /**
     * getter method for name
     * @return name of pokemon
     */
    public String getName(){
        return name;
    }

    /**
     * getter method for type
     * @return type of pokemon
     */
    public String getType(){
        return type;
    }

    /**
     * getter method for total health
     * @return total health of pokemon
     */
    public int getHealthTotal(){
        return healthTotal;
    }

    /**
     * getter method for health remaining
     * @return remaining health of pokemon
     */
    public int getHealthLeft(){
        return healthLeft;
    }

    /**
     * getter method for the speed of the pokemon
     * @return the speed of the pokemon
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * getter method for dodge chance
     * @return the dodge chance of the pokemon
     */
    public int getDodgeChance(){
        return dodgeChance;
    }

    /**
     * getter method for the effect on the pokemon
     * @return the effect of the pokemon                                     
     */
    public String getEffect(){
        return effect;
    }

    /**
     * getter method for the counter
     * @return the counter value
     */
    public int getCounter(){
        return counter;
    }

    //-------setters

    /**
     * setter method for name
     * @param name of pokemon
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * setter method for type
     * @param type of pokemon
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * setter method for total health
     * @param healthTotal health of pokemon
     */
    public void setHealthTotal(int healthTotal){
        this.healthTotal = healthTotal;
    }

    /**
     * setter method for health remaining
     * @param healthLeft health of pokemon
     */
    public void setHealthLeft(int healthLeft){
        this.healthLeft = healthLeft;
    }

    /**
     * setter method for the speed of the pokemon
     * @param speed of the pokemon
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }

    /**
     * setter method for dodge chance
     * @param dodgeChance the dodge chance of the pokemon
     */
    public void setDodgeChance(int dodgeChance){
        this.dodgeChance = dodgeChance;
    }

    /**
     * setter method for the effect on the pokemon
     * @param effect  the effect on the pokemon                                      
     */
    public void setEffect(String effect){
        this.effect = effect;
    }

    /**
     * setter method for the counter
     * @param counter the counter value
     */
    public void setCounter(int counter){
        this.counter = counter;
    }

    
    //method for printing out the ascii art------------------------------------

    /**
     * This method prints out the ascii art which is the image of the pokemon
     * @return is the ascii art of the pokemon as a string 
     */
    public String printThisAscii(){
        String out = "";
        if(this.getName().equals("Pikachu")){
            out += "       ,___          .-;'" + "\n";
            out += "       `\"-.`\\_...._/`.`" + "\n";
            out += "    ,      \\        /" + "\n";
            out += " .-' ',    / ()   ()\\" + "\n";
            out += "`'._   \\  /()    .  (|" + "\n";
            out += "    > .' ;,     -'-  /" + "\n";
            out += "   / <   |;,     __.;" + "\n";
            out += "   '-.'-.|  , \\    , \\" + "\n";
            out += "      `>.|;, \\_)    \\_)" + "\n";
            out += "       `-;     ,    /" + "\n";
            out += "          \\    /   <" + "\n";
            out += "           '. <`'-,_)" + "\n";
            out += "        jgs '._)" + "\n";

        }
        else if(this.getName().equals("Weedle")){
            out += "               ,`." + "\n";
            out += "               L  \\" + "\n";
            out += "              ,    \\" + "\n";
            out += "             j      \\" + "\n";
            out += "             ,       \\" + "\n";
            out += "            j         `" + "\n";
            out += "            ,          .__" + "\n";
            out += "         ,-'Y          `  `-." + "\n";
            out += "      .-'    `..___..-'      `-." + "\n";
            out += "     /__           ,-.          \\" + "\n";
            out += "    /(__)         `   '          `." + "\n";
            out += "   |               `\"'             L" + "\n";
            out += "   `.------._                      |" + "\n";
            out += " ,'          `                     |" + "\n";
            out += "F             |                    |" + "\n";
            out += "|             |                    |" + "\n";
            out += "`._         ,'                     j" + "\n";
            out += "   `+------'                      /" + "\n";
            out += "     \\                           /                           |`._" + "\n";
            out += "      `.                       ,'                          |   \\" + "\n";
            out += "        `._                _,-'                            |    \\" + "\n";
            out += "           `-,.________,.-'   `.                           |     L" + "\n";
            out += "            /                   '                          |     |" + "\n";
            out += "           /             _,._   |                          ,`---,'" + "\n";
            out += "         ,'|            /    .  j                        .'      `." + "\n";
            out += "         . L            '    | ,                      ,-'\"'`-..   |" + "\n";
            out += "          .,\\            `--' / `.               ___./       ,.' ,'" + "\n";
            out += "             \\              ,'    \\__         ,-'     \"-.    | |'" + "\n";
            out += "              `-._______,.-'  __   | `'-._.,- ._        _`   `\"Y" + "\n";
            out += "                |           .\"  \\  |     \\      `.    ,'  \\   ,'" + "\n";
            out += "                |           '    | ;      .       .   `._./.-'" + "\n";
            out += "                7.           `'\"' / `.--. |   _.. |      j" + "\n";
            out += "                `.__       `   _-'   |   |j  /   ||     .'" + "\n";
            out += "                    `-...,_..-'      `--'/   `._, ^----'" + "\n";
            out += "                         .\\            _'       ,'" + "\n";
            out += "                         `._-.______,.'`.___,.-'mh" + "\n";
        }
        else if(this.getName().equals("Charmander")){
            out += "             _.--\"\"`-.." + "\n";
            out += "            ,'          `." + "\n";
            out += "          ,'          __  `." + "\n";
            out += "         /|          \" __   \\" + "\n";
            out += "        , |           / |.   ." + "\n";
            out += "        |,'          !_.'|   |" + "\n";
            out += "      ,'             '   |   |" + "\n";
            out += "     /              |`--'|   |" + "\n";
            out += "    |                `---'   |" + "\n";
            out += "     .   ,                   |                       ,\"." + "\n";
            out += "      ._     '           _'  |                    , ' \\ `" + "\n";
            out += "  `.. `.`-...___,...---\"\"    |       __,.        ,`\"   L,|" + "\n";
            out += "  |, `- .`._        _,-,.'   .  __.-'-. /        .   ,    \\" + "\n";
            out += "-:..     `. `-..--_.,.<       `\"      / `.        `-/ |   ." + "\n";
            out += "  `,         \"\"\"\"'     `.              ,'         |   |  ',," + "\n";
            out += "    `.      '            '            /          '    |'. |/" + "\n";
            out += "      `.   |              \\       _,-'           |       ''" + "\n";
            out += "        `._'               \\   '\"\\                .      |" + "\n";
            out += "           |                '     \\                `._  ,'" + "\n";
            out += "           |                 '     \\                 .'|" + "\n";
            out += "           |                 .      \\                | |" + "\n";
            out += "           |                 |       L              ,' |" + "\n";
            out += "           `                 |       |             /   '" + "\n";
            out += "            \\                |       |           ,'   /" + "\n";
            out += "          ,' \\               |  _.._ ,-..___,..-'    ,'" + "\n";
            out += "         /     .             .      `!             ,j'" + "\n";
            out += "        /       `.          /        .           .'/" + "\n";
            out += "       .          `.       /         |        _.'.'" + "\n";
            out += "        `.          7`'---'          |------\"'_.'" + "\n";
            out += "       _,.`,_     _'                ,''-----\"'" + "\n";
            out += "   _,-_    '       `.     .'      ,\\" + "\n";
            out += "   -\" /`.         _,'     | _  _  _.|" + "\n";
            out += "    \"\"--'---\"\"\"\"\"'        `' '! |! /" + "\n";
            out += "-                            `\" \" -'" + "\n";
        }
        else if(this.getName().equals("Squirtle")){
            out += "               _,........__" + "\n";
            out += "            ,-'            \"`-." + "\n";
            out += "          ,'                   `-." + "\n";
            out += "        ,'                        \\" + "\n";
            out += "      ,'                           ." + "\n";
            out += "      .'\\               ,\"\".       `" + "\n";
            out += "     ._.'|             / |  `       \\" + "\n";
            out += "     |   |            `-.'  ||       `." + "\n";
            out += "     |   |            '-._,'||       | \\" + "\n";
            out += "     .`.,'             `..,'.'       , |`-." + "\n";
            out += "     l                       .'`.  _/  |   `." + "\n";
            out += "     `-.._'-   ,          _ _'   -\" \\  .     `" + "\n";
            out += "`.\"\"\"\"\"'-.`-...,---------','         `. `....__." + "\n";
            out += ".'        `\"-..___      __,'\\          \\  \\     \\" + "\n";
            out += "\\_ .          |   `\"\"\"\"'    `.           . \\     \\" + "\n";
            out += "  `.          |              `.          |  .     L" + "\n";
            out += "    `.        |`--...________.'.        j   |     |" + "\n";
            out += "      `._    .'      |          `.     .|   ,     |" + "\n";
            out += "         `--,\\       .            `7\"\"' |  ,      |" + "\n";
            out += "            ` `      `            /     |  |      |    _,-'\"\"\"`-." + "\n";
            out += "             \\ `.     .          /      |  '      |  ,'          `." + "\n";
            out += "              \\  v.__  .        '       .   \\    /| /              \\" + "\n";
            out += "               \\/    `\"\"\\\"\"\"\"\"\"\"`.       \\   \\  /.''                |" + "\n";
            out += "                `        .        `._ ___,j.  `/ .-       ,---.     |" + "\n";
            out += "                ,`-.      \\         .\"     `.  |/        j     `    |" + "\n";
            out += "               /    `.     \\       /         \\ /         |     /    j" + "\n";
            out += "              |       `-.   7-.._ .          |\"          '         /" + "\n";
            out += "              |          `./_    `|          |            .     _,'" + "\n";
            out += "              `.           / `----|          |-............`---'" + "\n";
            out += "                \\          \\      |          |" + "\n";
            out += "               ,'           )     `.         |" + "\n";
            out += "                7____,,..--'      /          |" + "\n";
            out += "                                  `---.__,--.'" + "\n";
        }
        else if(this.getName().equals("Bulbasaur")){
            out += "                                           /" + "\n";
            out += "                        _,.------....___,.' ',.-." + "\n";
            out += "                     ,-'          _,.--\"        |" + "\n";
            out += "                   ,'         _.-'              ." + "\n";
            out += "                  /   ,     ,'                   `" + "\n";
            out += "                 .   /     /                     ``." + "\n";
            out += "                 |  |     .                       \\.\\" + "\n";
            out += "       ____      |___._.  |       __               \\ `." + "\n";
            out += "     .'    `---\"\"       ``\"-.--\"'`  \\               .  \\" + "\n";
            out += "    .  ,            __               `              |   ." + "\n";
            out += "    `,'         ,-\"'  .               \\             |    L" + "\n";
            out += "   ,'          '    _.'                -._          /    |" + "\n";
            out += "  ,`-.    ,\".   `--'                      >.      ,'     |" + "\n";
            out += " . .'\\'   `-'       __    ,  ,-.         /  `.__.-      ,'" + "\n";
            out += " ||:, .           ,'  ;  /  / \\ `        `.    .      .'/" + "\n";
            out += " j|:D  \\          `--'  ' ,'_  . .         `.__, \\   , /" + "\n";
            out += "/ L:_  |                 .  \"' :_;                `.'.'" + "\n";
            out += ".    \"\"'                  \"\"\"\"\"'                    V" + "\n";
            out += " `.                                 .    `.   _,..  `" + "\n";
            out += "   `,_   .    .                _,-'/    .. `,'   __  `" + "\n";
            out += "    ) \\`._        ___....----\"'  ,'   .'  \\ |   '  \\  ." + "\n";
            out += "   /   `. \"`-.--\"'         _,' ,'     `---' |    `./  |" + "\n";
            out += "  .   _  `\"\"'--.._____..--\"   ,             '         |" + "\n";
            out += "  | .\" `. `-.                /-.           /          ," + "\n";
            out += "  | `._.'    `,_            ;  /         ,'          ." + "\n";
            out += " .'          /| `-.        . ,'         ,           ," + "\n";
            out += " '-.__ __ _,','    '`-..___;-...__   ,.'\\ ____.___.'" + "\n";
            out += " `\\\"^--'..'   '-`-^-'\"--    `-^-'`.''\"\"\"\"\"`.,^.`.--'" + "\n";
        }
        else if(this.getName().equals("Mewtwo")){
            out += "                                               `/:+`                      ```                        \n";
            out += "                                            :- .+`     `....``        -+-:/:                      \n";
            out += "                                            ./. `/--:::-....--:::::-./:   +.                      \n";
            out += "                                             `/.  .`              `.:`  `/.                       \n";
            out += "                                              `/                       `+`                        \n";
            out += "                                              `o                       /.                         \n";
            out += "                                              :-                       /.                         \n";
            out += "                                             `+                        ./                         \n";
            out += "                                             .:                        `+                         \n";
            out += "                                             `+    `.`           `     .+                         \n";
            out += "                                              :-`-:``.-`      `..` `-.`/.                         \n";
            out += "                                              `+``o/o.` `- `- ```:+-/ ./                          \n";
            out += "                                               :- //Nh/. .:-/ .:hNh--.+`                          \n";
            out += "                      `-:::::-`                `+```:+ss::.`:/yoo/.``+`                           \n";
            out += "                     `+-`   `.+.              `:+-     ``     `     -:                            \n";
            out += "                     /-       .o              +.`./-.            `-:-`                            \n";
            out += "                     :/       :+:`           ::  --.-::.` ``. `.::.                               \n";
            out += "                      :/-...-/:`./:--..`     o   o    s.::-..::-`                                 \n";
            out += "                       `..-+-`    -+:..-:-.` + `.+:---o````.+-                                    \n";
            out += "                `...`      :+      `:/-..-//:+:/:-.` `.--:``-:-.-.                                \n";
            out += "              -/:-.-:/-``.:/. -      .s:...-/-````.-:`  `..-:-  `:-                               \n";
            out += "             .o`     `o/--.  `o      o`     `o`     `/.      -``  -:                              \n";
            out += "      ```    -/       /-      +`     s       o`       +`     ` .:` :-                             \n";
            out += "  `-//:::://:-s:`   `:o.``    `.   ``++.`  `:/-`      -/     .- -/  +`                            \n";
            out += "`//-`       `-:os+:::----:::.````-/:-.-:::::. ./`     `+      /  +` :/                            \n";
            out += " .o.   ``...:.    `:+.       `-::::-`            `+.    `+      `  :` :s                            \n";
            out += "`o.  `::----:+/`    `+:                           `/:`   /.      `..``/o                            \n";
            out += ":+  `+.       -+`     //                            ./-` `::-..-::-` :./`                           \n";
            out += "o.  :/         -o`     /+                             -/.    `    `  /..:                           \n";
            out += "y   +.          :+      //                             `::        /. :-`/                           \n";
            out += "y   +.           //      o-                              +.       `o``+`/`                          \n";
            out += "s`  /:            +/     .o                              -/        .+ .+./`                         \n";
            out += "+-  .o             /+`    s.                             -:         -- .+.+`                        \n";
            out += "-+   +-             -o:   s`                        ./::-:` .`       `  `/:o`                       \n";
            out += " o`  `o.              -/" + "/" + "/-                      `:/-        ``       ````-/o:                      \n";
            out += " .o   `o`                                      `//`              .:-::::::/++s+                     \n";
            out += ":/   `+-                                    -+`                 -:        :+:+-                   \n";
            out += " //    :+`                                 :/`                   +         `o-:+`                 \n";
            out += "  :+`   `//`                              :/                     o          `s`-o`                \n";
            out += "   -+`    .//-                           -+                     .o           o. -+                \n";
            out += "    `+-     `-/:-.                     `-s                      +-           o.  /:               \n";
            out += "     `//`      `.-:::---.....-------::::s.                     .o           `s   `s               \n";
            out += "       .+:`         ````......``````   :/                     `o.           //    s`              \n";
            out += "         -/:`                         `o`                     +-           .o`    s`              \n";
            out += "           ./:`                       -/                    `+-           `o.     s`              \n";
            out += "             .:/-`                    +.                   `+-           `o-      s               \n";
            out += "               `-/:.`                 o`                  .+-           -+.      `o               \n";
            out += "                  `-/:-.`             o`                `//`          ./:`       :-               \n";
            out += "                     `.-:::-.``       :`              `:/.          .//.        ./                \n";
            out += "                          `.--::::-.-:+`            .:/.         `-/:.        .::`                \n";
            out += "                                `.://:`          `.//.        `-:/-`       .-/-`                  \n";
            out += "                                -/-`          `-/+:.````...-:/s:.      `.-/-.                     \n";
            out += "                               `o         .::::-:---------..` o       `+-`                        \n";
            out += "                                o`      `-:-`                 ::      .+                          \n";
            out += "                                o`     ./.                     +`     +.                          \n";
            out += "                               `o     .o`                      +.     +                           \n";
            out += "                               ::     o.                      `o`     /`                          \n";
            out += "                              `o`    :+                      `+-      `+-                         \n";
            out += "                              :/     y`                    ./o/-`      `:/.                       \n";
            out += "                             `o`     h/-                  -+. `-s`       `//.`                    \n";
            out += "                            `o.      y`o`                 o`    o.         `:/:`                  \n";
            out += "                            /:       s.o.                 -o.``:s.`           .:/:.`       ````   \n";
            out += "                           :/        :o-                   `///-`-:/:.         ...:///::::/::://` \n";
            out += "                          :/         `o.`..``                       .:+.        .-:/::/++-`    .+:\n";
            out += "                         -+  `://`    `/:..::/-                       `+:`               -+-    `s\n";
            out += "                        -o      -+            //                        -//:.```         `/o-:--:.\n";
            out += "                          /:     `/o-:::::::`   `s                           `-:::::::::--:-`       \n";
        }
        else if(this.getName().equals("Machop")){
            out += "                        ,.\"--.\n";
            out += "                   _.../     _\\\"\"-.\n";
            out += "                 //  ,'    ,\"      :\n";
            out += "                .'  /   .:'      __|\n";
            out += "                || ||  /,    _.\"   '.\n";
            out += "                || ||  ||  ,'        `.\n";
            out += "               /|| ||  ||,'            .\n";
            out += "              /.`| /` /`,'  __          '\n";
            out += "             j /. \" `\"  ' ,' /`.        |\n";
            out += "             ||.|        .  | . .      _|,--._\n";
            out += "             ||#|        |  | #'|   ,-\"       `-.\n";
            out += "            /'.||        |  \\.\" |  /             `\n";
            out += "           /    '        `.----\"   |`.|           |\n";
            out += "           \\  `.    ,'             `  \\           |\n";
            out += "            `._____           _,-'  `._,..        |\n";
            out += "              `\".  `'-..__..-'   _,.--'.  .       |\n";
            out += "               ,-^-._      _,..-'       `.|       '\n";
            out += "           _,-'     |'\"\"'\"\"              `|  `\\    \\\n";
            out += "       _.-'         |            `.,--    |    \\    \\\n";
            out += "  _,.\"\"'\"\"'-._      '      `.     .      j      '    \\\n";
            out += " /            `.___/.-\"    ._`-._  \\.    |      |     L\n";
            out += "/  ____           /,.-'    . `._ '\"\"|`.  `      |     |\n";
            out += " `.    `\"-.      / _,-\"     `._ `\"'\".  `. \\     '     '\n";
            out += "   \\       `-   .\"'            \"`---'\\   ` `-._/     /\n";
            out += "    `-------.   |                     \\   `-._      /\n";
            out += "             \\ j                      .       `...,'\n";
            out += "              `|                       \\\n";
            out += "               '                        \\\n";
            out += "                .                      / \\\n";
            out += "                |`.                   /   `._\n";
            out += "                |    `.._____        /|      `-._\n";
            out += "                |        |   Y.       |.         `.\n";
            out += "                |       j     \\       '.`\"--....-'\n";
            out += "             _,-'       |      |        \\\n";
            out += "          .-'           |     ,'         `.\n";
            out += "         '              |     |            `.\n";
            out += "         `.        __,..'     '.             \\\n";
            out += "           `-.---\"'             `-..__      _/\n";
            out += "                                      `'\"\"\"' mh\n";
        }
        else if(this.getName().equals("Ghastly")){
            out += "" + "\n";
            out += "                             _\n";
            out += "                          .\"' `..._\n";
            out += "                         '         `.\n";
            out += "                       .'      ___..'\n";
            out += "                 _   .\"       '   .__,-.,\"\", ,----.\n";
            out += "      ,.-\"\"''-..\" :  :        `--'        ' :      :\n";
            out += "    .'            :_,'                    `._`\"--. ;\n";
            out += "    :              _,.--'\"'\"\"`--._           `.  `\"\n";
            out += "   j             ,'               `-.      ,._.'  ,\"\".\n";
            out += "   :           ,'                   ,-.   .   __  `..'\n";
            out += "   `--.    .'.'                   ,'   `. :_,\"  `.\n";
            out += " ,.   ;   .   \\                 ,'      |         `.\n";
            out += "' :  :    |    `.             ,'        |\\         `.  _\n";
            out += " `.   ._  |      \\         _.'          | .      ___ `\" :\n";
            out += "        : '     . \\      ,'  .          ' |     :   `...'\n";
            out += "       ,'  \\       `.   .             ,'  |     '  __\n";
            out += "      .    `.       |    \\          .'    '    .  (  `.\n";
            out += "    .'      \\`.___,'      `-.____.-'     '     :   `-.'\n";
            out += "     .   ,\". \\ ..___              _     /      :    .\n";
            out += "     :   . :  \\|/\\  `\"'--------+\"|,'  ,'       `-..' :\n";
            out += "      `-\" .'   `: `\"-.._______,.\\|  .'               '\n";
            out += "          `--. _ `._             _,'        ,\"\"-.__,'\n";
            out += "              \" :   `\"--.....--\"'     __   .\n";
            out += "              ,-'                 ,.-\"  `-'\n";
            out += "             :   ,..             .    ,\"\".\n";
            out += "            .'   .  :   __..._   `\"-. :   :\n";
            out += "            `.._  : ' ,'      `\"--..' `--\"\n";
            out += "                `-' `\" mh\n";
        }                                                           
        return out;
    }



} //end of Pokemon class
