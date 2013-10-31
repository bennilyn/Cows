import java.util.*;
/*
 * Cows and Bulls
 * 
 * What I need to do:
 * Make a Secret Code - check
 * Ask the person to guess said secret code - check
 * Depending on if they got the right code at the right place, give them a bull - check
 * Depending on if they got the right code in the WRONG place, give them a cow - check
 * If they get neither... give them neither.
 * 
 */
public class Cows {
	public static ArrayList<Character> secretCode;
	public static ArrayList<Character> playerCode;
	
	public static void main(String[] args) {
		
		//Game introduction
		System.out.println("Welcome to the game of Cows and Bulls.");
		System.out.println("In order to play this game, you must guess secret code comprised of");
		System.out.println("numbers 0-9 and letters A-F.");
		
		Scanner keyboard = new Scanner(System.in);
		String num;
		Integer numOfCodes = 0;
		boolean okay = false;
		//A loop to get the number of numbers/letters
		while(!okay){
			System.out.print("\nPlease enter the number of letters and numbers you want to guess (3-6): ");
			num = keyboard.nextLine();
			
			//Checking if they gave me an int or not
			try{
				numOfCodes = Integer.parseInt(num);
			}
			catch(Exception e){
				numOfCodes = 0;
				System.out.println("You did not enter an integer.");
				
			}
			
			//making sure they gave an int 3-6
			//This should probably be shorter for the MIPS version 2-4 or something like that
			if(numOfCodes < 3 || numOfCodes > 6)
			{
				System.out.println("Next time, please enter a number 3-6.");
			}
			else{
				okay = true;
			}
		}
		
		makeCode(numOfCodes);
		//System.out.println(secretCode);
		//Uncomment the above line when actually playing the game
		
		int numBulls = 0;
		
		//Plays the game
		do{
			getGuess(numOfCodes);
			System.out.println("Your guess was: " + playerCode);
			System.out.println("The number of bulls was: " + getBulls());
			System.out.println("The number of cows was: " + getCows());
			numBulls = getBulls();
		}while(numBulls != numOfCodes);
		
		System.out.println("Congratulations! You won! The secret code was: " + secretCode);
	}
	
	/*
	 * Makes the code by using an ArrayList of choices and taking away a choice
	 * each time it is picked (so there are no duplicates)
	 */
	public static void makeCode(int numOfCodes) {
		secretCode = new ArrayList<Character>();
		Random rand = new Random();
		ArrayList<Integer> choices = new ArrayList<Integer>();
		
		for(int i = 0; i < 16; i++){
			choices.add(i);
		}
		
		//Picks a choice then removes it from the choices array
		for(int i = 0; i < numOfCodes; i++){
			int randomNum = rand.nextInt(choices.size());
			int num = choices.get(randomNum);
			secretCode.add(switchToChar(num));
			choices.remove(randomNum);
		}
	}
	
	//This is terrible and wouldn't be needed if we just asked the user to input one
	//Character at a time, so I think we should probably do that
	private static Character switchToChar(int num) {
		switch(num){
			case 0: return '0';
			case 1: return '1';
			case 2: return '2';
			case 3: return '3';
			case 4: return '4';
			case 5: return '5';
			case 6: return '6';
			case 7: return '7';
			case 8: return '8';
			case 9: return '9';
			case 10: return 'A';
			case 11: return 'B';
			case 12: return 'C';
			case 13: return 'D';
			case 14: return 'E';
			case 15: return 'F';
			default: return 'X';
		}
	}
	
	/*
	 * This gets the user's guess
	 */
	public static void getGuess(int numOfCodes) {
		System.out.println("\nMake a guess, numbers 0-9 and letters A-F.");
		System.out.println("Separate your guesses using spaces in the form: 1 2 A B");
		
		boolean okay = true;
		
		do{
			System.out.print("What is your guess? ");
			Scanner keyboard = new Scanner(System.in);
			String playerGuess = keyboard.nextLine();
		
			String[] guesses = playerGuess.split(" ");
			playerCode = new ArrayList<Character>();
		
			for(int i = 0; i < guesses.length; i++)
			{
				Character guess = 'X';
				//Selects the player's first character if they put in more than one
				//For example if the person puts in 12 3 4, it will treat the 12 as a 1
				guess = guesses[i].charAt(0);
				if(Character.isLetter(guess))
				{
					guess = Character.toUpperCase(guess);
				}
				playerCode.add(guess);
			}
			
			okay = check(numOfCodes); //this returns true if everything is okay with their guess
			
		}while(!okay);
	}
	
	/*
	 * Way too long
	 */
	private static boolean check(int numOfCodes){
		boolean okay = true;
		
		int guessLen = playerCode.size();
		
		//checks if they enter the correct number of codes
		if(guessLen != numOfCodes)
		{
			okay = false;
			System.out.println("\nYou did not enter the correct number of codes!");
		}
		
		//if okay keep checking
		if(okay){
			//checks if they entered the 0-9 and A-F
			for(int i = 0; i < guessLen; i++)
			{
				char guess = playerCode.get(i);
				int numGuess = (int)guess;
				if(numGuess < 48 || numGuess > 71)
				{
					System.out.println("\nOne of the codes you entered was not 0-9 or A-F.");
					okay = false;
					break;
				}
			}
		}
		
		if(okay) {
			//checks if they entered a duplicate
			//to make this more efficient, and to solve a lot of headaches, it would
			//be easier to ask for one code at a time, instead of the entirety of
			//the code at once!
			ArrayList<Character> dupCheck = new ArrayList<Character>();
			for(int i = 0; i < playerCode.size(); i++){
				Character guess = playerCode.get(i);
				if(dupCheck.contains(guess)){
					okay = false;
					System.out.println("\nYour guess contained more than one of the same code.");
					break;
				}
				else{
					dupCheck.add(guess);
				}
			}
		}
		
		if(!okay)
		{
			System.out.println("Please, try again.");
		}
		
		
		return okay;
	}
	
	/*
	 * Returns the # of bulls the characters guessed
	 */
	public static int getBulls() {
		int result = 0;
		
		for(int i = 0; i < playerCode.size(); i++)
		{
			Character player = playerCode.get(i);
			Character secret = secretCode.get(i);
			if(player.equals(secret)) {
				result += 1;
			}
		}
		
		return result;
	}
	
	/*
	 * returns the number of cows the player guessed
	 */
	public static int getCows() {
		int result = 0;
		
		for(int i = 0; i < playerCode.size(); i++) {
			Character player = playerCode.get(i);
			Character secret = secretCode.get(i);
			if(!player.equals(secret))
			{
				if(secretCode.contains(player))
				{
					result++;
				}
			}
		}
		
		return result;
	}
}