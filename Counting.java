import java.util.*;
import java.util.Random;
import java.lang.Math;
public class Counting{
    private static boolean gameEnd = false;
    private static Field field = new Field();
    
    public static void counting(ArrayList<Card> playerHandCounting, ArrayList<Card> opponentHandCounting, int player){
        Scanner s = new Scanner(System.in);
        ArrayList<Card> playerHand = playerHandCounting;
        ArrayList<Card> opponentHand = opponentHandCounting;
        boolean oppGoesFirst = true;
        //player is dealer
        if(player == 0){
            boolean doesPlayerSayGo = false;
            boolean doesOppSayGo = false;
            while(!isCountingDone(playerHand, opponentHand)){
                //opp turn
                System.out.println("Opponent's turn.")
                doesOppSayGo = opponentTurn(field, opponentHand, doesPlayerSayGo, oppGoesFirst);
                oppGoesFirst = false;
                if(isGameEnd()) break;
                
                //player turn
                doesPlayerSayGo = playerTurn(f, playerHand);
                if(isGameEnd()) break;
            }
        }
        //player is non-dealer
        if(player == 1){
            boolean doesPlayerSayGo = false;
            oppGoesFirst = false;
            while(!isCountingDone(playerHand, opponentHand)){
                //player turn
                doesPlayerSayGo = playerTurn(f, playerHand);
                if(isGameEnd()) break;
                //opp turn
                opponentTurn(field, opponentHand, doesPlayerSayGo, oppGoesFirst);
                if(isGameEnd()) break;
            }
        }
    }
    
    public static boolean opponentTurn(Field f, ArrayList<Card> opponentHand, boolean doesPlayerSayGo, boolean oppGoesFirst){
        //check if opp can play a card
        boolean oppGo = opponentSaysGo(f, opponentHand);
        if(!oppGo){
            //has a playable card, determine optimal card unless first play
            Card oppChoice = opponentCardChoice(field, opponentHand, oppGoesFirst);
            oppGoesFirst = false;
            //score points and check game status
            opponentHand.remove(oppChoice);
            System.out.println("Opponent played" + oppChoice);
            GameDisplay.setOpponentTotalScore(pointsCheck(f,"Opponent"));
        }
        //Opponent gets a go from player and can play a card
        else if(doesPlayerSayGo && !oppGo){
            Card oppChoice = opponentCardChoice(field, opponentHand, oppGoesFirst);
            opponentHand.remove(oppChoice);
            System.out.println("Opponent played" + oppChoice);
            GameDisplay.setOpponentTotalScore(pointsCheck(f,"Opponent"));
        }
        //Opponent gets a go from player but cant play a card
        else if(doesPlayerSayGo && oppGo){
            System.out.println("Opponent Scores 1 point for a go.");
            GameDisplay.setOpponentTotalScore(1));
        }
        //opponent says go
        else if(oppGo)){
            System.out.println("Opponent says Go.");
            return true;
        }
        return false;
    }
    
    public static boolean playerTurn(Field f, ArrayList<Card> playerHand, boolean oppSayGo, Scanner s){
        //player makes choice
        while(true){
            //ToDo make method to handle player choice in counting
            playerChoice = GameDisplay.promptNumberReadLine(s, "Play a card or go" + "\n" + "Dealer Hand: " + "\n" + Deck.toString(playerHand), playerHand.size()) - 1;
            //player says go return to switch to opp turn
            if(playerChoice == -1){
                return true;
            }
            //no go, player plays a card, check validity
            playerCard = playerHand.get(playerChoice)
            field.addCard(playerCard);
            if(overThirtyOne(f)){
                field.deleteLastCard();
                System.out.println("Invalid play.");
                continue
            }
            //add card to field, score points, update game status
            playerHand.remove(playerCard);
            GameDisplay.setPlayerTotalScore(pointsCheck(field, "You"));
            return false;
        }
    }
    
    public static boolean isCardAvailable(ArrayList<Card> hand){
        return hand.size() > 0;
    }

    public static boolean isCountingDone(ArrayList<Card> dealerHand, ArrayList<Card> nonDealerHand){
        if(isCardAvailable(nonDealerHand) || isCardAvailable(dealerHand)){
            return false;
        }
        return true;
    }

    public static Card opponentCardChoice(Field f, ArrayList<Card> o, boolean playFirst){
        //chose a random card if they play first
        if(o.size() == 4 && playFirst){
            Random r = new Random();
            int choice = r.nextInt(4);
            return o.get(choice);
        }
        int maxScore = 0;
        int scoreInd = 0;
        for(int i = scoreInd; i < o.size() - 1; i++){
            int currScore = 0;
            Card oppChoice = o.get(i);
            f.addCard(oppChoice);
            if(overThirtyOne(f)){
                f.deleteLastCard();
                continue;
            }
            currScore = opponentPointCountCheck(f);
            if(currScore > maxScore){
                maxScore = currScore;
                scoreInd = i;
            }
        }
        return o.get(scoreInd);
    }
    
    private static int opponentPointCountCheck(Field f){
        int points = 0;
        int pair = pairsCounting(f);
        int run = runsCounting(f);
        boolean fifteenCheck = fifteen(f);
        boolean thirtyOneCheck = thirtyOneCheck(f);
        
        if(fifteenCheck) points += 2;
        if(thirtyOneCheck) points += 2;
        points += pair;
        points += run;
        return points;
    }
    
    public static boolean opponentSaysGo(Field f, ArrayList<Card> o){
        for(int i = 0; i < o.size(); i++){
            Card oppChoice = o.get(i);
            f.addCard(oppChoice);
            if(!overThirtyOne(f)){
                f.deleteLastCard();
                return false;
            }
            f.deleteLastCard();
        }
        return true;
    }
    
    public static int pointsCheck(Field field, String player){
        int score = 0;
        int pairsScore = pairsCounting(field);
        int runsScore = runsCounting(field);
        boolean hitFifteen = fifteen(field);
        boolean hitThirtyOne = thirtyOne(field);
        if(pairsScore > 0){
            System.out.println(player +" scored " + pairsScore + " points in pairs");
        }
        if(runsScore > 0){
            System.out.println(player +" scored " + runsScore + " points in runs");
        }
        if(hitFifteen){
            System.out.println(player + " scored " + "2 points in fifteens");
            score += 2;
        }
        if(hitThirtyOne){
            System.out.println(player + " scored " + "2 points by hitting 31");
            score += 2;
            field.clearField();
        } 
        return score + pairsScore + runsScore;
    }
    
    public static boolean isGameEnd(){
        if(GameDisplay.getOpponentTotalScore() > 120){
            gameEnd = true;
            return gameEnd;
        }
        if(GameDisplay.getPlayerTotalScore() > 120){
            gameEnd = true;
            return gameEnd;
        }
        return gameEnd;
    }

    public static int pairsCounting(Field f){
        int score = 0;
        int count = 1;
        int fieldSize = f.size() - 1;
        while(fieldSize > 0){
            if(f.getTail().card.getValue() == f.getFieldNode(fieldSize - 1).card.getValue()){
                count++;
                fieldSize--;    
            }
            else{
                break;
            }
        }
        return score + count * (count - 1);
    }
    
    public static int runsCounting(Field f){
        int fieldSize = f.size();
        if(fieldSize < 3) return 0;
        
        ArrayList<Integer> runList = new ArrayList<Integer>();
        for(int i = 0; i < fieldSize; i++) runList.add(f.getCard(i).getValue());
        
        int maxRunLength = 1;
        ArrayList<Integer> sortRunList = new ArrayList<Integer>();
        for(int i = fieldSize - 1; i >= fieldSize - 3; i--) sortRunList.add(runList.get(i));
        
        Collections.sort(sortRunList);
        int currRunLength = scanRun(sortRunList);
        if(currRunLength > maxRunLength) maxRunLength = currRunLength;
        
        while(sortRunList.size() < fieldSize){
            sortRunList.add(runList.get(runList.size() - sortRunList.size() - 1));
            Collections.sort(sortRunList);
            currRunLength = scanRun(sortRunList);
            if(currRunLength > maxRunLength) maxRunLength = currRunLength;
        }
        
        if(maxRunLength >= 3) return maxRunLength;
        return 0;
    }
    
    private static int scanRun(ArrayList<Integer> run){
        int runLength = 1;
        for(int i = 0; i < run.size() - 1; i++){
            if(run.get(i) + 1 == run.get(i + 1)) runLength++;
            else return 0;
        }
        return runLength;
    }
    
    public static boolean thirtyOne(Field f){
        return f.getSum() == 31;
    }
    
    public static boolean fifteen(Field f){
        return f.getSum() == 15;
    }
    
    public static boolean overThirtyOne(Field f){
        return f.getSum() > 31;
    }
    
    public static boolean getGameEnd(){
        return gameEnd;
    }
}
