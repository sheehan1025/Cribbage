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
        //player is dealer
        if(player == 0){
            boolean doesPlayerSayGo = false;
            while(!isCountingDone(dealerHand, nonDealerHand)){
                //prompt opponent to play a card
                //played card gets added to field
                if(nonDealerChoice < nonDealerHand.size() && doesPlayerSayGo == false){
                    //condition if nondealer has a card to play and player does not
                    if(nonDealerHand.size() > 0 && dealerHand.size() == 0){
                        //clear the field as the nondealer will play out there cards
                        if(field.getSum() < 31 && field.size() > 4){
                            field.clearField();
                        }
                        //non dealer played last card
                        if(nonDealerHand.size() == 1){
                            System.out.println("Opponent scored 1 point for last card");
                            GameDisplay.setOpponentTotalScore(1);
                            if(GameDisplay.getOpponentTotalScore() > 120){
                                gameEnd = true;
                                break;
                            }
                        }
                    }
                    // non dealer adds card to the field
                    field.addCard(nonDealerHand.get(nonDealerChoice));
                    // card went over 31 nondealer increase choice to find new card
                    if(field.getSum() > 31){
                        field.remove(field.size() - 1);
                        nonDealerChoice = nonDealerChoice + 1;
                        continue;
                    }
                    // show nondealer opponent played card and check for points and game end.
                    System.out.println("Opponent played " + nonDealerHand.get(nonDealerChoice).toString());
                    //
                    pointsCheck(field, "Opponent");
                    //check to not allow card to be replayed
                    nonDealerHand.remove(nonDealerChoice);
                    nonDealerChoice = 0;
                }
                // opponent does not have a play available (value would go above 31 or hand is empty)
                if(nonDealerChoice >= nonDealerHand.size() && nonDealerHand.size() != 0 && dealerHand.size() != 0 && field.size() > 0){
                    System.out.println("Non dealer says go");
                    // prompt dealer player for a card
                    int dealerPlayDecider = GameDisplay.promptNumberReadLine(s, "Do you have a card to play? Enter Yes or No.", 0);
                    // player does not have a card and gets the point from the go
                    // adds their point, checks for game end and clears the field
                    if(dealerPlayDecider == -1){
                        System.out.println("You scored 1 point for a go from your opponent");
                        GameDisplay.setPlayerTotalScore(1);
                        isGameEnd
                        field.clear();
                        nonDealerChoice = 0;
                        continue;
                    }
                }
                System.out.println("Field: " + "Field total = " + fieldSum(fieldValues) + "\n" + Deck.toString(field));
                //prompt dealer to play card; gets added to field arraylist
                if(dealerHand.size() > 0){
                    doesPlayerSayGo = false;
                    //System.out.println("Dealer Hand: " + "\n" + Deck.toString(dealerHand));
                    playerChoice = GameDisplay.promptNumberReadLine(s, "Play a card or go" + "\n" + "Dealer Hand: " + "\n" + Deck.toString(playerHand), playerHand.size());
                    // opponent receives  go, check for game end, switch to opponent's turn
                    if(playerChoice == -1){
                        System.out.println("Your opponent scored 1 point for a go from you.");
                        GameDisplay.setOpponentTotalScore(1);
                        isGameEnd();
                        doesPlayerSayGo = true;
                        continue;
                    }
                    field.add(playerHand.get(playerChoice - 1));
                    //check if valid to play, back to start of turn if over 31
                    if(overThirtyOne(field)){
                        System.out.println("Invalid card. Count goes over 31.");
                        field.deleteLastCard();
                        continue;
                    }
                    //score points
                    GameDisplay.setPlayerTotalScore(pointsCheck(field, "You"));
                    //check if game is over
                    isGameEnd();
                    playerHand.remove(dealerChoice - 1);
                    System.out.println("Field: " + "Field total = " + fieldSum(fieldValues) + "\n" + Deck.toString(field));
                }
            }
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
        int highScore = 0;
        int highScoreInd = 0;
        for(int i = highScoreInd; i < o.size(); i++){
            Card oppChoice = o.get(i);
            f.addCard(oppChoice);
            if(overThirtyOne(f)){
                f.deleteLastCard();
                continue;
            }
            else if(pairsCounting(f) + runsCounting(f) > 0 || fifteen(f) || thirtyOne(f)){
                //
            }
        }
    }
    
    public static boolean opponentSaysGo(Field f, ArrayList<Card> o){
        for(int i = highScoreInd; i < o.size(); i++){
            Card oppChoice = o.get(i);
            f.addCard(oppChoice);
            if(overThirtyOne(f)){
                f.deleteLastCard();
                return true;
            }
            f.deleteLastCard();
            return false;
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
