import java.util.*;
import java.lang.Math;
public class Counting{
    private static boolean gameEnd = false;
    private static Field field = new Field();
    
    public static void counting(ArrayList<Card> dealerHandForCounting, ArrayList<Card> nonDealerHandForCounting, int player){
        field.clearField();
        Scanner s = new Scanner(System.in);
        int dealerChoice = 0;
        int nonDealerChoice = 0;
        ArrayList<Card> dealerHand = dealerHandForCounting;
        ArrayList<Card> nonDealerHand = nonDealerHandForCounting;
        //player is dealer
        if(player == 0){
            boolean doesPlayerSayGo = false;
            while(!isCountingDone(dealerHand, nonDealerHand)){
                //prompt non dealer to play a card
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
                // non dealer opponent does not have a play available (value would go above 31 or hand is empty)
                if(nonDealerChoice >= nonDealerHand.size() && nonDealerHand.size() != 0 && dealerHand.size() != 0 && field.size() > 0){
                    System.out.println("Non dealer says go");
                    // prompt dealer player for a card
                    int dealerPlayDecider = GameDisplay.promptNumberReadLine(s, "Do you have a card to play? Enter Yes or No.", 0);
                    // player does not have a card and gets the point from the go
                    // adds their point, checks for game end and clears the field
                    if(dealerPlayDecider == -1){
                        System.out.println("You scored 1 point for a go from your opponent");
                        GameDisplay.setPlayerTotalScore(1);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
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
                    dealerChoice = GameDisplay.promptNumberReadLine(s, "Play a card or go" + "\n" + "Dealer Hand: " + "\n" + Deck.toString(dealerHand), dealerHand.size());
                    // opponent receives  go, check for game end and clear if opponent cant play a card.
                    if(dealerChoice == -1){
                        System.out.println("Your opponent scored 1 point from a go from you.");
                        GameDisplay.setOpponentTotalScore(1);
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        doesPlayerSayGo = true;
                        if(nonDealerChoice > nonDealerHand.size() - 1){
                            nonDealerChoice = 0;
                        }
                        continue;
                    }
                    //add card to field
                    field.add(dealerHand.get(dealerChoice - 1));
                    //check if valid to play
                    if(fieldSum(fieldValues) > 31){
                        overThirtyOne(dealerHand);
                    }
                    //check points and game end
                    pointsCheck(field, "You");
                    // prevent display of played card
                    dealerHand.remove(dealerChoice - 1);
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

    public static Card opponentCardChoice(ArrayList<Card> opponentHand){
        return null;
    }

    public static int pointsCheck(Field field, String player){
        int scored = 0;
        int pairsScore = pairsCounting(field);
        int runsScore = runsCounting(field);
        int fifteenScore = fieldSum(field);
        boolean hitThirtyOne = thirtyOne(field);
        if(pairsScore > 0){
            System.out.println(player +" scored " + pairsScore + " points in pairs");
        }
        if(runsScore > 0){
            System.out.println(player +" scored " + runsScore + " points in runs");
        }
        if(fifteenScore == 15){
            System.out.println(player + " scored " + "2 points in fifteens");
        }
        if(hitThirtyOne){
            System.out.println(player + " scored " + "2 points by hitting 31");
            field.clearField();
        } 
        
    }
    
    public static boolean isGameEnd(int score){
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
        if(fieldSize < 3){
            return 0;
        }
        //convert field elements to an int array
        ArrayList<Integer> runList = new ArrayList<Integer>();
        for(int i = 0; i < fieldSize; i++){
            runList.add(f.getCard(i).getValue());
        }

        int runScore = 1;
        for(int i = fieldSize - 1; i > 0; i--){
            //if a pair is found a run is not possible
            if(runList.get(fieldSize - 1) == runList.get(i) && i != fieldSize - 1){
                return 0;
            }
            int adjDiff = Math.abs(runList.get(i) - runList.get(i - 1));
            //if the difference between 2 adjacent cards is greater
            //than the number of cards played the run is broken.
            if(adjDiff > runList.size() - 1){
                break;
            }
            runScore++;
        }
        //run score only viable if at least 3
        if(runScore >= 3) return runScore;
        return 0;
    }
    
    public static boolean thirtyOne(ArrayList<Integer> a){
        if(fieldSum(a) == 31){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static void overThirtyOne(ArrayList<Card> c){
        Scanner s = new Scanner(System.in);
        boolean over = true;
        while(over == true){
            System.out.println("Your card goes over 31.");
            field.remove(field.size() - 1); //remove card from the field
            int choice = GameDisplay.promptNumberReadLine(s, "Play a different card or go.", c.size()); // prompt for a new choice
            if(choice == -1){ // if player says go
                GameDisplay.setOpponentTotalScore(1);
                if(GameDisplay.getOpponentTotalScore() > 120){
                    gameEnd = true;
                    break;
                }
                field.clear(); // clear the field
                over = false;
                break;
            }
            field.add(c.get(choice - 1)); // otherwise add new choice to the field\
            if(fieldSum(fieldValues) > 31){ // if still over 31 repeat loop
                continue;
            }
            else if(thirtyOne(fieldValues) == true){ // if exact 31 score points and clear the field
                GameDisplay.setPlayerTotalScore(2);
                if(GameDisplay.getPlayerTotalScore() > 120){
                    gameEnd = true;
                    break;
                }
                field.clear();
                over = false;
            }
            else{
                over = false;
            }
        }
    }
    
    public static int fieldSum(ArrayList<Integer> a){
        int sum = 0;
        for(int i = 0; i < a.size(); i++){
            sum = sum + a.get(i);
        }
        return sum;
    }
    
    public static boolean getGameEnd(){
        return gameEnd;
    }
}
