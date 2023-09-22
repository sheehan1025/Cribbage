import java.util.Scanner;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class CribbageGame{
    private static ArrayList<Card> playerHand = new ArrayList<Card>();
    private static ArrayList<Card> opponentHand = new ArrayList<Card>();
    private static ArrayList<Card> crib = new ArrayList<Card>();
    private static int playerTotalScore = 0;
    private static int opponentTotalScore = 0;
    private static boolean playerStarts = true;

    
    public static void runGame(){
        Deck deck = new Deck();
        boolean isPlayerDealer = playerStarts;
        Scoring nibsScoring = new Scoring();
        while(true){
            startRound(deck);

            //crib placement
            cribPlacement(playerHand, opponentHand, isPlayerDealer);
            
            //reveal top card after crib placements
            Card topCard = deck.getTopCard();
            System.out.println("====================================");
            System.out.print("Top card: " + topCard.toString() + "\n");
            System.out.println("====================================");
            
            //dealer scores nibs points
            int nibsScore = nibsScoring.nibs(topCard);
            if(isPlayerDealer) increasePlayerTotalScore(nibsScore);
            else increaseOpponentTotalScore(nibsScore);
            if(statusCheck()) break;
            
            //counting round
            startCountingRound(playerHand, opponentHand, isPlayerDealer);
            
            //TODO end game if a player scored enough points to end the game
            
            //Scoring round
            playerHand.add(topCard);
            opponentHand.add(topCard);
            crib.add(topCard);
            System.out.println("Now scoring hand and crib points." + "\n");
            wait(3000);
            
            scoringRound(playerHand, opponentHand, crib, topCard, isPlayerDealer);
            
            resetRound();
            System.out.println("Starting new round.");
            System.out.println("===============================================");
            //switch roles
            if(isPlayerDealer) isPlayerDealer = false;
            else isPlayerDealer = true;
        }
        //end game messages
        gameEnd();
    }
    
    public static void scoringRound(ArrayList<Card> pHand, ArrayList<Card> oHand, ArrayList<Card> cHand, Card top,boolean isPlayerDealer){
        //non dealer score points first
        if(isPlayerDealer){

        }
    }

    public static int handScore(ArrayList<Card> hand, Card top){
        Scoring score = new Scoring(hand);
        return score.totalScore(top);
    }

    public static void cribPlacement(ArrayList<Card> pHand, ArrayList<Card> oHand, boolean isPlayerDealer){
        if(isPlayerDealer){
            System.out.println("You are the dealer, choose cards for your crib.");
        }
        else{
            System.out.println("You are not the dealer, choose cards for your opponent's crib.");
        }
        //player chooses cards for crib
        ArrayList<Card> cribChoicePlayer = playerCribChoice(pHand);
        //opponent chooses cards for crib
        ArrayList<Card> cribChoiceOpponent = opponentCribChoice(oHand);

        for(Card c : cribChoicePlayer) crib.add(c);
        for(Card c : cribChoiceOpponent) crib.add(c);
        
    }
    
    public static ArrayList<Card> playerCribChoice(ArrayList<Card> hand){
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
    
    public static ArrayList<Card> opponentCribChoice(ArrayList<Card> hand){
        return null;
    }
    
    public static void startCountingRound(ArrayList<Card> pHand, ArrayList<Card> oHand, boolean isPlayerDealer){
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
    
    
    public static void startRound(Deck d){
        System.out.println("Player overall score: " + playerTotalScore);
        System.out.println("Opponent overall score: " + opponentTotalScore + "\n");
        playerHand = d.getHand();
        opponentHand = d.getHand();
    }
	
	public static void resetRound(){
	    playerHand.clear();
        opponentHand.clear();
        crib.clear();
	}
	

    public static void gameEnd(){
        if(playerWin()){
            //check for skunk, double skunk, victory message
        }
    }

    public static boolean statusCheck(){
        return playerWin() || opponentWin();
    }

    public static boolean playerWin(){
        return getPlayerTotalScore() > 120;
    }

    public static boolean opponentWin(){
        return getOpponentTotalScore() > 120;
    }

	public static ArrayList<Card> getPlayerHand(){
	    return playerHand;
	}
	
	public static ArrayList<Card> getOpponentHand(){
	    return opponentHand;
	}
	
	public static int getPlayerTotalScore(){
	    return playerTotalScore;
	}
	
	public static int getOpponentTotalScore(){
	    return opponentTotalScore;
	}
	
	public static void increasePlayerTotalScore(int score){
	    playerTotalScore += score;
	}
	public static void increaseOpponentTotalScore(int score){
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
