import java.util.Scanner;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class CribbageGame{
    private ArrayList<Card> playerHand = new ArrayList<Card>();
    private ArrayList<Card> opponentHand = new ArrayList<Card>();
    private ArrayList<Card> crib = new ArrayList<Card>();
    private int playerTotalScore = 0;
    private int opponentTotalScore = 0;
    private boolean playerStarts = true;
    private String playerName;
    private String opponentName = "Opponent";
    
    public CribbageGame(){
        setPlayerName();
        runGame();
    }
    
    public void setPlayerName(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        playerName = s.next();
    }
    
    public void runGame(){
        Deck deck = new Deck();
        boolean isPlayerDealer = playerStarts;
        Scoring nibsScoring = new Scoring();
        while(true){
            startRound(deck);

            //crib placement
            cribPlacement(getPlayerHand(), getOpponentHand(), isPlayerDealer);
            
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
            startCountingRound(getPlayerHand(), getOpponentHand(), isPlayerDealer);
            if(statusCheck()) break;
            
            //Scoring round
            System.out.println("Now scoring hand and crib points." + "\n");
            wait(3000);
            
            scoringRound(getPlayerHand(), getOpponentHand(), getCrib(), topCard, isPlayerDealer);
            if(statusCheck()) break;
            
            resetRound(); //ToDo fix reset round to move cards from hands back to deck and shuffle
            System.out.println("Starting new round.");
            System.out.println("===============================================");
            
            //switch roles
            if(isPlayerDealer) isPlayerDealer = false;
            else isPlayerDealer = true;
        }
        //end game messages
        gameEnd();
    }
    
    public void scoringRound(ArrayList<Card> pHand, ArrayList<Card> oHand, ArrayList<Card> cHand, Card top, boolean isPlayerDealer){
        //add top card to hands and crib for scoring
        getPlayerHand().add(top);
        getOpponentHand().add(top);
        getCrib().add(top);
        //non dealer score points first

        //after scoring points check if over 120 return before scoring any additional points
        if(isPlayerDealer){
            System.out.println("Scoring Opponent's hand:");
            increaseOpponentTotalScore(handScore(oHand, top, opponentName));
            System.out.println(printHand(oHand));
            if(statusCheck()) return;
            
            System.out.println("Scoring Player's hand:");
            increasePlayerTotalScore(handScore(pHand, top, playerName));
            if(statusCheck()) return;
            
            System.out.println("Scoring Player's crib:");
            increasePlayerTotalScore(handScore(cHand, top, playerName));
            System.out.println(printHand(cHand));
        }
        else{
            System.out.println("Scoring Player's hand:");
            increasePlayerTotalScore(handScore(pHand, top, playerName));
            if(statusCheck()) return;
            
            System.out.println("Scoring Opponent's hand:");
            increaseOpponentTotalScore(handScore(oHand, top, opponentName));
            printHand(oHand);
            if(statusCheck()) return;
            
            System.out.println("Scoring Opponent's crib:");
            increaseOpponentTotalScore(handScore(cHand, top, opponentName));
            printHand(cHand);
        }
    }

    public int handScore(ArrayList<Card> hand, Card top, String name){
        Scoring score = new Scoring(hand, name);
        return score.totalScore(top);
    }

    public void cribPlacement(ArrayList<Card> pHand, ArrayList<Card> oHand, boolean isPlayerDealer){
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
    
    public ArrayList<Card> playerCribChoice(ArrayList<Card> hand){
        Scanner s = new Scanner(System.in);
        ArrayList<Card> cribCards = new ArrayList<Card>();
        while(cribCards.size() < 2){
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
        return cribCards;
    }
    
    public ArrayList<Card> opponentCribChoice(ArrayList<Card> hand){
        ArrayList<Card> cribCards = new ArrayList<Card>();
        //place holder for opponent crib choice
        cribCards.add(hand.get(0));
        cribCards.remove(hand.get(0));
        cribCards.add(hand.get(0));
        cribCards.remove(hand.get(0));
        return cribCards;
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
        Counting counting =  new Counting(playerName, this);
        counting.countingRound(playerHandForCounting, opponentHandForCounting, isPlayerDealer);
    }
    
    
    public void startRound(Deck d){
        System.out.println("Player overall score: " + playerTotalScore);
        System.out.println("Opponent overall score: " + opponentTotalScore + "\n");
        playerHand = d.getHand();
        opponentHand = d.getHand();
    }
	
	public void resetRound(){
	    playerHand.clear();
        opponentHand.clear();
        crib.clear();
	}
	

    public void gameEnd(){
        if(playerWin()){
            //check for skunk, double skunk, victory message
        }
    }

    public boolean statusCheck(){
        return playerWin() || opponentWin();
    }

    public boolean playerWin(){
        return getPlayerTotalScore() > 120;
    }

    public boolean opponentWin(){
        return getOpponentTotalScore() > 120;
    }

    public ArrayList<Card> getPlayerHand(){
	return playerHand;
    }
	
    public ArrayList<Card> getOpponentHand(){
	return opponentHand;
    }
	
    public ArrayList<Card> getCrib(){
	return crib;
    }
	
    public int getPlayerTotalScore(){
	return this.playerTotalScore;
    }
	
    public int getOpponentTotalScore(){
	return this.opponentTotalScore;
    }
	
    public void increasePlayerTotalScore(int score){
	this.playerTotalScore += score;
    }

    public void increaseOpponentTotalScore(int score){
	this.opponentTotalScore += score;
    }
	
    public void wait(int ms){
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
