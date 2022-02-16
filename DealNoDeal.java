/*
Chase Balmer
2/8/2022
This is my own work, cjb
Project Deal or No Deal
This program runs a game of deal or no deal
*/
import java.util.*;
public class DealNoDeal
{
	public static void main(String[]args)
	{
		//Variables
		Scanner reader = new Scanner(System.in);
		boolean[] isOpen = new boolean[26];
		Integer[] values = {0,1,5,10,25,50,75,100,200,300,400,500,750,1000,5000,10000,25000,50000,75000,
							100000,200000,300000,400000,500000,750000,1000000};	//0 value represents .01 (This value is so small that it is not worth inlcuding in calculations.
																					//In the event the value is won, i will typecast


		//Set cases to open
		for(int i=0; i<26; i++)
		{
			isOpen[i]=true;
		}

		//Randomize order of values
		List<Integer> list = Arrays.asList(values);
		Collections.shuffle(list);
		list.toArray(values);


		//User select first box
		System.out.println("Welcome to deal or no deal. In this game, you will start with one box(1-26), and over time slowly remove boxes from the game.");
		System.out.println("You will be offered payouts to stop playing, but may continue to play and open your own case\n\n");
		System.out.println("What box would you like to start with:");
		int startingBox = reader.nextInt();

		while(startingBox<1 || startingBox>26)
		{
			System.out.println("What box would you like to start with: (previous input was not inbounds)");
			startingBox = reader.nextInt();
		}
		isOpen[startingBox-1] = false;

		//Use method to play rounds
		for(int round = 1; round < 10; round++)
		{
			//Methods will play a round and then gernerate an offer from the banker
			playRound(round, values, isOpen, reader);
			generateOffer(round, values, isOpen, reader, startingBox);
		}
		//If the user makes it to the end, a seperate method will run that round and complete the game
		finalRound(values, isOpen, startingBox, reader);



	}//End Main
	//Method to play rounds
	static void playRound(int round, Integer[]values, boolean[]isOpen, Scanner reader)
	{
		//Set number of cases to be opened
		int toOpen = 7-round;
		if(toOpen < 1){toOpen=1;}	//continue opening 1 after values have worked down



		//Prompt user to remove case and tell them the value
		int currentCase;
		for(int i=0; i<toOpen; i++)
		{
			System.out.println("What case would you like to remove:");
			currentCase = reader.nextInt();
			if(!isOpen[currentCase-1])
			{
				System.out.println("What case would you like to remove: (previous already open)");
				currentCase = reader.nextInt();
			}

			System.out.print("Open the case...");

			 try {Thread.sleep(2000);}		//Delay for suspense
			 catch (InterruptedException ex){}

			if(values[currentCase-1] == 0)
				System.out.println("$0.01 has been removed from your list!");	//Accounting for 0.01 in array
			else
			{
				System.out.println("$" + values[currentCase-1] + " has been removed from your list!");
				isOpen[currentCase-1] = false;
			}
		}
		System.out.println("\n\nWhat an end to round " + round);
	}//End Playround

	static void finalRound(Integer[]values, boolean[]isOpen, int startingBox, Scanner reader)
	{
		//User decides between final offer which will be an average vs keeping their own case
			//Calculate avg of values left
			int sum = 0, count = 0, finalCase = 0;
			for(int i = 0; i<26; i++)
			{
				if(isOpen[i])
				{
					sum += values[i];
					count++;
					finalCase = i;
				}
			}
				sum += values[startingBox-1];
				int avg = sum/count;


		//Output situation, if statements to account for the 0 in the array
		if(values[finalCase] == 0)
			System.out.println("Final round, you went all the way. There are two cases left: one containing $" + values[startingBox-1] + " and one containing $0.01");
		else if(values[startingBox-1] == 0)
			System.out.println("Final round, you went all the way. There are two cases left: one containing $0.01 and one containing $" + values[finalCase]);
		else
			System.out.println("Final round, you went all the way. There are two cases left: one containing $" + values[startingBox-1] + " and one containing $" + values[finalCase]);

		System.out.println("Would you like to take the final offer from the banker, an offer at $" + avg + " dollars, or keep your original box and gamble for more money (input 1 for offer, 2 for og box)");
		int choice = reader.nextInt();

		while(!(choice == 1 || choice == 2))
		{
			System.out.println("1 or 2");
			choice = reader.nextInt();
		}

		if(choice == 1)
		{
			System.out.println("\n\nSmart choice. You are walking away with $" + avg + " dollars! \n\nCongradulations");
		}
		else
		{
			if(values[startingBox-1] == 0)
				System.out.println("\n\nRisky choice. The value of your case was $0.01 dollars!");
			else
				System.out.println("\n\nRisky choice. The value of your case was $" + values[startingBox-1] + " dollars!");
			if(values[startingBox-1] > avg)
				System.out.println("CONGRATS, YOU BEAT THE ODDS");
			else
				System.out.println("Unfortunately, you chose the lesser half of the average. Thank you for playing");
		}

	}//End final round


	//Banker Offer Method
	static void generateOffer(int round, Integer[]values, boolean[]isOpen, Scanner reader, int startingBox)
	{
		//Explain and output
		if(round!=9)	//stop running if user goes to the end
		{
			System.out.println("As you know, the banker will now give you an offer to buy back your case and take you out of the game.");
			System.out.println("The Banker is calling...here is his offer:");
		}

		//Generate offer
			//Calculate avg of values left
			int sum = 0, count = 0;
			for(int i = 0; i<26; i++)
			{
				if(isOpen[i])
				{
					sum += values[i];
					count++;
				}
			}
			sum += values[startingBox-1];
			int avg = sum/count;


			//Change avg percentage based on how far into game
			if(round == 1)
				avg *= .3;
			if(round == 2)
				avg *= .4;
			if(round == 3)
				avg *= .5;
			if(round == 4)
				avg *= .6;
			if(round == 5)
				avg *= .7;
			if(round == 6)
				avg *= .7;
			if(round == 7)
				avg *= .8;
			if(round == 8)
				avg *= .8;
			if(round == 9)
				avg *= .9;


		//Output final offer and choice
		if(round!=9)
		{
			System.out.print("\t\t $" + avg + "\n\n");
			System.out.print("Will you take the offer, or continue playing(1 to take, 2 to play on)");
			int choice = reader.nextInt();

			while(!(choice == 1 || choice == 2))
			{
				System.out.println("1 or 2");
				choice = reader.nextInt();
			}

			if(choice == 1)
			{
				System.out.println("\n\nFair play. You are walking away with $" + avg + " dollars! \n\nCongrats and thanks for playing");
				System.exit(0);
			}
			else
			{
				System.out.println("\n\nIn that case, we shall move on!");
			}
		}
	}//End generateOffer


}//End Program