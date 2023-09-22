import java.util.Scanner;
import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class CribbageGame{
    private static ArrayList<Card> playerHand = new ArrayList<Card>();
    private static ArrayList<Card> opponentHand = new ArrayList<Card>();
    private static ArrayList<Card> crib = new ArrayList<Card>();
    private static int playerTotalScore = 0;
    private static int opponentTotalScore = 0;
    private static Random r = new Random();
    private static boolean playerStarts = true;

    
    public static void runGame(){
        Deck deck = new Deck();
        boolean isPlayerDealer = playerStarts;
        while(true){
            int playerRoundScore = 0;
            int opponentRoundScore = 0;
            int cribRoundScore = 0;
            
            startRound();
            //crib placement
            cribPlacement(playerHand, opponentHand, isPlayerDealer);
            
            
            Card topCard = deck.getTopCard();
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
            startCountingRound(playerHand, opponentHand, isPlayerDealer);
            
            //TODO end game if a player scored enough points to end the game
            
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
    
    public static void cribPlacement(ArrayList<Card> pHand, ArrayList<Cards> oHand, boolean isPlayerDealer){
        if(isPlayerDealer){
            System.out.println("You are the dealer, choose cards for your crib.");
        }
        else{
            System.out.println("You are not the dealer, choose cards for your opponent's crib.");
        }
        //player chooses cards for crib
        ArrayList<Card> cribChoicePlayer = playerCribChoice(pHand)
        //opponent chooses cards for crib
        ArrayList<Card> cribChoiceOpponent = opponentCribChoice(oHand);
    }
    
    
    public ArrayList<Card> playerCribChoice(ArrayList<Card> hand){
        Scanner s = new Scanner(System.in);
        ArrayList<Card> cribCards = new ArrayList<Card>();
        while(cribCards.size() < 3){
            System.out.println("Select a card for the crib." + "\n" + "Player Hand: " + "\n" + printHand(hand));
            try{
                int playerSelection = s.nextInt() - 1;
                if(playerSelection < 0 || playerSelection > hand.size()){
                    System.out.println("Invalid choice");
                    continue;
                }
                cribCards.add(hand.get(playerSelection));
                hand.remove(hand.get(playerSelection));
            }
            catch(Exception e){
                System.out.println("Invalid entry");
            }
        }
        s.close();
        return cribCards;
    }
    
    public ArrayList<Card> opponentCribChoice(ArrayList<Card> hand){
        return null;
    }
    
    public void startCountingRound(ArrayList<Card> pHand, ArrayList<Card> oHand, boolean isPlayerDealer){
        ArrayList<Card> playerHandForCounting = new ArrayList<Card>();
        ArrayList<Card> opponentHandForCounting = new ArrayList<Card>();
        for(int i = 0; i < playerHand.size(); i++){
            playerHandForCounting.add(pHand.get(i));
            opponentHandForCounting.add(oHand.get(i));
        }
        System.out.println("Entering Counting Round.");
        wait(3000);
        if(isPlayerDealer){
            System.out.println("You are the dealer, the opponent will play the first card" + "\n");
        }
        else{
            System.out.println("You are not the dealer, you will play your card first" + "\n");
        }
        Counting.counting(playerHandForCounting, opponentHandForCounting, isPlayerDealer);
    }
    
    
    public static void startRound(){
        System.out.println("Player overall score: " + playerTotalScore);
        System.out.println("Opponent overall score: " + opponentTotalScore + "\n");
        playerHand = deck.getHand();
        opposingHand = deck.getHand();
    }
	
	public static void resetRound(){
	    playerHand.clear();
        opposingHand.clear();
        crib.clear();
	}
	
	public static ArrayList<Card> getPlayerHand(){
	    return playerHand;
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
	    playerTotalScore += score;
	}
	public static void setOpponentTotalScore(int score){
	    opponentTotalScore += score;
	}
	
	public static void wait(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    
    public static String printHand(ArrayList<Card> a){
        String hand = "";
        for(int i = 0; i < a.size(); i++){
            hand += (i + 1) + ". " + a.get(i) + "\n";
        }
        return hand;
    }
}
