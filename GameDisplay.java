import java.util.Scanner;
import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class GameDisplay{
    private static Scanner s = new Scanner(System.in);
    private static ArrayList<Card> playerHand = new ArrayList<Card>();
    private static ArrayList<Card> opposingHand = new ArrayList<Card>();
    private static ArrayList<Card> crib = new ArrayList<Card>();
    private static ArrayList<Integer> playerValues = new ArrayList<Integer>();
    private static ArrayList<Integer> playerValuesForRuns = new ArrayList<Integer>();
    private static ArrayList<Integer> cribValues = new ArrayList<Integer>();
    private static ArrayList<Integer> cribValuesForRuns = new ArrayList<Integer>();
    private static ArrayList<Integer> opposingValues = new ArrayList<Integer>();
    private static ArrayList<Integer> opposingValuesForRuns = new ArrayList<Integer>();
    private static int playerTotalScore = 0;
    private static int opponentTotalScore = 0;
    private static Random r = new Random();
    private static int player = r.nextInt(2);
    
    public static void main(String[] args){
        while(true){
            int playerScore = 0;
            int opposingScore = 0;
            int cribScore = 0;
            System.out.println("Player overall score: " + playerTotalScore);
            System.out.println("Opponent overall score: " + opponentTotalScore + "\n");
            Deck deck = new Deck();
            if(player == 0){
                playerHand = deck.getDealerHand();
                opposingHand = deck.getNonDealerHand();
            }
            else{
                playerHand = deck.getNonDealerHand();
                opposingHand = deck.getDealerHand();
            }
            //crib placement
            if(player == 0){
                System.out.println("You are the dealer, choose cards for your crib.");
            }
            else{
                System.out.println("You are not the dealer, choose cards for your opponent's crib.");
            }
            System.out.println("Player Hand: " + "\n" + Deck.toString(playerHand));
            int cribChoice = promptNumberReadLine(s, "First card for crib", playerHand.size());
            crib.add(playerHand.get(cribChoice - 1));
            playerHand.remove(cribChoice - 1);
            System.out.println("Player Hand: " + "\n" + Deck.toString(playerHand));
            int cribChoice2 = promptNumberReadLine(s, "Second card for crib", playerHand.size());
            crib.add(playerHand.get(cribChoice2 - 1));
            playerHand.remove(cribChoice2 - 1);
            int opponentCribChoice = r.nextInt(6);
            crib.add(opposingHand.get(opponentCribChoice));
            opposingHand.remove(opponentCribChoice);
            opponentCribChoice = r.nextInt(5);
            crib.add(opposingHand.get(opponentCribChoice));
            opposingHand.remove(opponentCribChoice);
            Deck.removeCards(deck.getDeck());
            Card topCard = Deck.getTopCard(deck.getDeck());
            System.out.println("====================================");
            System.out.print("Top card: " + topCard.toString() + "\n");
            System.out.println("====================================");
            if(Scoring.nibs(topCard) > 0 && player == 0){
                System.out.println("You scored 2 points for Jack top card");
                playerTotalScore = playerTotalScore + 2;
                if(playerTotalScore > 120){
                    break;
                }
            }
            else if((Scoring.nibs(topCard) > 0 && player == 1)){
                System.out.println("Opponent scored 2 points for Jack top card");
                opponentTotalScore = opponentTotalScore + 2;
                if(opponentTotalScore > 120){
                    break;
                }
            }
            
            //counting round
            ArrayList<Card> dealerHandForCounting = new ArrayList<Card>();
            ArrayList<Card> nonDealerHandForCounting = new ArrayList<Card>();
            for(int i = 0; i < playerHand.size(); i++){
                if(player == 0){
                    dealerHandForCounting.add(playerHand.get(i));
                    nonDealerHandForCounting.add(opposingHand.get(i));
                }
                else{
                    dealerHandForCounting.add(opposingHand.get(i));
                    nonDealerHandForCounting.add(playerHand.get(i));
                }
            }
            System.out.println("Entering Counting Round.");
            wait(3000);
            if(player == 0){
                System.out.println("You are the dealer, the opponent will play the first card" + "\n");
            }
            else{
                System.out.println("You are not the dealer, you will play your card first" + "\n");
            }
            Counting.counting(dealerHandForCounting, nonDealerHandForCounting, player);
            if(Counting.getGameEnd() == true){
                break;
            }
            
            //Scoring round
            playerHand.add(topCard);
            opposingHand.add(topCard);
            crib.add(topCard);
            System.out.println("Now scoring hand and crib points." + "\n");
            wait(3000);
            if(player == 0){
                System.out.println("Your opponent scores first." + "\n");
                Deck.handValues(opposingValues, opposingHand);
                Deck.handValuesForRuns(opposingValuesForRuns, opposingHand);
                System.out.println("Opponent Hand: " + "\n" + Deck.toStringWithTopCard(opposingHand));
                opposingScore = opposingScore + Scoring.pairs(opposingHand) + Scoring.fifteens(opposingValues) + Scoring.flush(opposingHand) + Scoring.runs(opposingValuesForRuns) + Scoring.nobs(opposingHand, topCard);
                System.out.println("Pairs score = " + Scoring.pairs(opposingHand) + " Fifteens Score = " + Scoring.fifteens(opposingValues) + 
                " Runs Score = " + Scoring.runs(opposingValuesForRuns) + " Flush Score = " + Scoring.flush(opposingHand) + " Nobs = " + Scoring.nobs(opposingHand, topCard));
                System.out.println("Opponent Hand Score = " + opposingScore + "\n");
                
                opponentTotalScore = opponentTotalScore + opposingScore;
                
                if(opponentTotalScore > 120){
                    break;
                }
                wait(5000);
                System.out.println("Scoring player hand.");
                Deck.handValues(playerValues, playerHand);
                Deck.handValuesForRuns(playerValuesForRuns, playerHand);
                System.out.println("Player Hand: " + "\n" + Deck.toStringWithTopCard(playerHand));
                playerScore = playerScore + Scoring.pairs(playerHand) + Scoring.fifteens(playerValues) + Scoring.runs(playerValuesForRuns) + Scoring.flush(playerHand) + Scoring.nobs(playerHand, topCard);
                System.out.println("Pairs Score = " + Scoring.pairs(playerHand) + " Fifteens Score = " + Scoring.fifteens(playerValues) + 
                " Runs Score = " + Scoring.runs(playerValuesForRuns) + " Flush Score = " + Scoring.flush(playerHand) + " Nobs = " + Scoring.nobs(playerHand, topCard));
                System.out.println("Player Hand Score = " + playerScore + "\n");
            
                playerTotalScore = playerTotalScore + playerScore;
            
                if(playerTotalScore > 120){
                    break;
                }
                wait(5000);
                System.out.println("Scoring player crib.");
                Deck.handValues(cribValues, crib);
                Deck.handValuesForRuns(cribValuesForRuns, crib);
                System.out.println("Crib: " + "\n" + Deck.toStringWithTopCard(crib));
                cribScore = cribScore + Scoring.pairs(crib) + Scoring.fifteens(cribValues) + Scoring.runs(cribValuesForRuns) + Scoring.nobs(crib, topCard);
                if(Scoring.flush(crib) == 5){
                    cribScore = cribScore + 5;
                    System.out.println("Pairs Score = " + Scoring.pairs(crib) + " Fifteens Score = " + Scoring.fifteens(cribValues) + 
                    " Runs Score = " + Scoring.runs(cribValuesForRuns) + " Flush Score = " + Scoring.flush(crib) + " Nobs = " + Scoring.nobs(crib, topCard));
                }
                else{
                    System.out.println("Pairs Score = " + Scoring.pairs(crib) + " Fifteens Score = " + Scoring.fifteens(cribValues) + 
                    " Runs Score = " + Scoring.runs(cribValuesForRuns) + " Flush Score = " + 0 + " Nobs = " + Scoring.nobs(crib, topCard));
                    System.out.println("Total Crib Score = " + cribScore);
                }
                
                playerTotalScore = playerTotalScore + cribScore;
                
                if(playerTotalScore > 120){
                    break;
                }
            }
            if(player == 1){
                System.out.println("Player scores first.");
                Deck.handValues(playerValues, playerHand);
                Deck.handValuesForRuns(playerValuesForRuns, playerHand);
                System.out.println("Player Hand: " + "\n" + Deck.toStringWithTopCard(playerHand));
                playerScore = playerScore + Scoring.pairs(playerHand) + Scoring.fifteens(playerValues) + Scoring.runs(playerValuesForRuns) + Scoring.flush(playerHand) + Scoring.nobs(playerHand, topCard);
                System.out.println("Pairs Score = " + Scoring.pairs(playerHand) + " Fifteens Score = " + Scoring.fifteens(playerValues) + 
                " Runs Score = " + Scoring.runs(playerValuesForRuns) + " Flush Score = " + Scoring.flush(playerHand) + " Nobs = " + Scoring.nobs(playerHand, topCard));
                System.out.println("Player Hand Score = " + playerScore + "\n");
            
                playerTotalScore = playerTotalScore + playerScore;
            
                if(playerTotalScore > 120){
                    break;
                }
                wait(5000);
                System.out.println("Scoring opponenet's hand.");
                Deck.handValues(opposingValues, opposingHand);
                Deck.handValuesForRuns(opposingValuesForRuns, opposingHand);
                System.out.println("Opponent Hand: " + "\n" + Deck.toStringWithTopCard(opposingHand));
                opposingScore = opposingScore + Scoring.pairs(opposingHand) + Scoring.fifteens(opposingValues) + Scoring.flush(opposingHand) + Scoring.runs(opposingValuesForRuns) + Scoring.nobs(opposingHand, topCard);
                System.out.println("Pairs score = " + Scoring.pairs(opposingHand) + " Fifteens Score = " + Scoring.fifteens(opposingValues) + 
                " Runs Score = " + Scoring.runs(opposingValuesForRuns) + " Flush Score = " + Scoring.flush(opposingHand) + " Nobs = " + Scoring.nobs(opposingHand, topCard));
                System.out.println("Opponent Hand Score = " + opposingScore + "\n");
                
                opponentTotalScore = opponentTotalScore + opposingScore;
                
                if(opponentTotalScore > 120){
                    break;
                }
                wait(5000);
                System.out.println("Scoring opponent's crib.");
                Deck.handValues(cribValues, crib);
                Deck.handValuesForRuns(cribValuesForRuns, crib);
                System.out.println("Crib: " + "\n" + Deck.toString(crib));
                cribScore = cribScore + Scoring.pairs(crib) + Scoring.fifteens(cribValues) + Scoring.runs(cribValuesForRuns) + Scoring.nobs(crib, topCard);
                if(Scoring.flush(crib) == 5){
                    cribScore = cribScore + 5;
                    System.out.println("Pairs Score = " + Scoring.pairs(crib) + " Fifteens Score = " + Scoring.fifteens(cribValues) + 
                    " Runs Score = " + Scoring.runs(cribValuesForRuns) + " Flush Score = " + Scoring.flush(crib) + " Nobs = " + Scoring.nobs(crib, topCard));
                }
                else{
                    System.out.println("Pairs Score = " + Scoring.pairs(crib) + " Fifteens Score = " + Scoring.fifteens(cribValues) + 
                    " Runs Score = " + Scoring.runs(cribValuesForRuns) + " Flush Score = " + 0 + " Nobs = " + Scoring.nobs(crib, topCard));
                    System.out.println("Total Crib Score = " + cribScore);
                }
                
                opponentTotalScore = opponentTotalScore + cribScore;
        
                if(playerTotalScore > 120){
                    break;
                }
            }
            resetRound();
            System.out.println("Starting new round.");
            System.out.println("===============================================");
            if(player == 0){
                player = 1;
            }
            else{
                player = 0;
            }
        }
        if(playerTotalScore > 120){
            if(playerTotalScore - opponentTotalScore >= 30){
                System.out.println("You win!!! You also skunked your opponent!");
            }
            else{
                System.out.println("You win!!!");
            }
            System.out.println("Player overall score: " + playerTotalScore);
            System.out.println("Opponent overall score: " + opponentTotalScore);
        }
        else{
            if(opponentTotalScore - playerTotalScore >= 30){
                System.out.println("You lose and you got skunked by your opponent :(");
            }
            else{
                System.out.println("You lose :(");
            }
            System.out.println("Player overall score: " + playerTotalScore);
            System.out.println("Opponent overall score: " + opponentTotalScore);
        }
    }
    
    public static int promptNumberReadLine(Scanner s, String prompt, int max) {
		int value = 0;
		while (true) {
			System.out.print(prompt);
			if(s.hasNext("go") || s.hasNext("Go")){
			    s.next();
			    return -1;
			}
			if(s.hasNext("yes") || s.hasNext("Yes")){
			    s.next();
			    return 0;
			}
			if(s.hasNext("no") || s.hasNext("No")){
			    s.next();
			    return -1;
			}
			else if (!s.hasNextInt()) {
				System.out.println("That was not a valid number! Please try again.");
				s.next();
				continue;
			}
			value = s.nextInt();
			if (value < 0 || value > max) {
				System.out.println("That was not a valid number! Please try again.");
				continue;
			}
			break;
		}
		return value;
	}
	
	public static void resetRound(){
	    playerHand.clear();
        opposingHand.clear();
        crib.clear();
        playerValues.clear();
        playerValuesForRuns.clear();
        cribValues.clear();
        cribValuesForRuns.clear();
        opposingValues.clear();
        opposingValuesForRuns.clear();
	}
	
	public static ArrayList<Card> getPlayerHand(){
	    return playerHand;
	}
	public static ArrayList<Integer> getHandValues(ArrayList<Integer> a){
	    return a;
	}
	public static ArrayList<Card> getOpposingHand(){
	    return opposingHand;
	}
	
	public static int getPlayerTotalScore(){
	    return playerTotalScore;
	}
	
	public static int getOpponentTotalScore(){
	    return opponentTotalScore;
	}
	
	public static void setPlayerTotalScore(int score){
	    playerTotalScore = playerTotalScore + score;
	}
	public static void setOpponentTotalScore(int score){
	    opponentTotalScore = opponentTotalScore + score;
	}
	
	public static void wait(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
}