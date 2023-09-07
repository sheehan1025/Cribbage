import java.util.Scanner;
import java.util.*;
import java.lang.Math;
public class Counting{
    private static boolean gameEnd = false;
    private static ArrayList<Card> field = new ArrayList<Card>();
    private static ArrayList<Integer> fieldValues = new ArrayList<Integer>();
    private static ArrayList<Integer> fieldValuesForRuns = new ArrayList<Integer>();
    
    public static void counting(ArrayList<Card> dealerHandForCounting, ArrayList<Card> nonDealerHandForCounting, int player){
        field.clear();
        updateFieldValues(fieldValues, field);
        updateFieldValuesForRuns(fieldValuesForRuns, field);
        Scanner s = new Scanner(System.in);
        int dealerChoice = 0;
        int nonDealerChoice = 0;
        ArrayList<Card> dealerHand = dealerHandForCounting;
        ArrayList<Card> nonDealerHand = nonDealerHandForCounting;
        if(player == 0){
            boolean doesPlayerSayGo = false;
            while(dealerHand.size() + nonDealerHand.size() > 0){
                //prompt non dealer to play a card
                //played card gets added to arraylist to represent cards on field
                if(nonDealerChoice < nonDealerHand.size() && doesPlayerSayGo == false){
                    if(nonDealerHand.size() > 0 && dealerHand.size() == 0){
                        if(fieldSum(fieldValues) < 31 && field.size() > 4){
                            field.clear();
                            updateFieldValues(fieldValues, field);
                            updateFieldValuesForRuns(fieldValuesForRuns, field);
                        }
                        if(nonDealerHand.size() == 1){
                            System.out.println("Opponent scored 1 point for last card");
                            GameDisplay.setOpponentTotalScore(1);
                            if(GameDisplay.getOpponentTotalScore() > 120){
                                gameEnd = true;
                                break;
                            }
                        }
                    }
                    field.add(nonDealerHand.get(nonDealerChoice));
                    updateFieldValues(fieldValues, field);
                    updateFieldValuesForRuns(fieldValuesForRuns, field);
                    if(fieldSum(fieldValues) > 31){
                        field.remove(field.size() - 1);
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                        nonDealerChoice = nonDealerChoice + 1;
                        continue;
                    }
                    System.out.println("Opponent played " + nonDealerHand.get(nonDealerChoice).toString());
                    if(pairsCounting(field) > 0){
                        System.out.println("Opponent scored " + pairsCounting(field) + " points in pairs");
                        GameDisplay.setOpponentTotalScore(pairsCounting(field));
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(runsCounting(fieldValuesForRuns) > 0){
                        System.out.println("Opponent scored " + runsCounting(fieldValuesForRuns) + " points in runs");
                        GameDisplay.setOpponentTotalScore(runsCounting(fieldValuesForRuns));
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(fieldSum(fieldValues) == 15){
                        System.out.println("Opponent scored 2 points in fifteens");
                        GameDisplay.setOpponentTotalScore(2);
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(thirtyOne(fieldValues) == true){
                        System.out.println("Opponent scored 2 points by hitting 31");
                        GameDisplay.setOpponentTotalScore(2);
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                    }   
                    nonDealerHand.remove(nonDealerChoice);
                    nonDealerChoice = 0;
                }
                if(nonDealerChoice >= nonDealerHand.size() && nonDealerHand.size() != 0 && dealerHand.size() != 0 && field.size() > 0){
                    System.out.println("Non dealer says go");
                    int dealerPlayDecider = GameDisplay.promptNumberReadLine(s, "Do you have a card to play? Enter Yes or No.", 0);
                    if(dealerPlayDecider == -1){
                        System.out.println("You scored 1 point for a go from your opponent");
                        GameDisplay.setPlayerTotalScore(1);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
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
                    if(dealerChoice == -1){
                        System.out.println("Your opponent scored 1 point from a go from you.");
                        GameDisplay.setOpponentTotalScore(1);
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                        doesPlayerSayGo = true;
                        if(nonDealerChoice > nonDealerHand.size() - 1){
                            nonDealerChoice = 0;
                        }
                        continue;
                    }
                    field.add(dealerHand.get(dealerChoice - 1));
                    updateFieldValues(fieldValues, field);
                    updateFieldValuesForRuns(fieldValuesForRuns, field);
                    //check if pair, 15, run, 31, or over
                    if(fieldSum(fieldValues) > 31){
                        overThirtyOne(dealerHand);
                    }
                    if(pairsCounting(field) > 0){
                        System.out.println("You scored " + pairsCounting(field)+ " points in pairs");
                        GameDisplay.setPlayerTotalScore(pairsCounting(field));
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(runsCounting(fieldValuesForRuns) > 0){
                        System.out.println("You scored " + runsCounting(fieldValuesForRuns)+ " points in runs");
                        GameDisplay.setPlayerTotalScore(runsCounting(fieldValuesForRuns));
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(fieldSum(fieldValues) == 15){
                        System.out.println("You scored 2 points in fifteens");
                        GameDisplay.setPlayerTotalScore(2);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(thirtyOne(fieldValues) == true){
                        System.out.println("You scored 2 points for hitting 31");
                        GameDisplay.setPlayerTotalScore(2);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                    }
                    if(nonDealerHand.size() == 0 && dealerHand.size() > 0){
                        System.out.println("You scored 1 point for last card");
                        GameDisplay.setPlayerTotalScore(1);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    dealerHand.remove(dealerChoice - 1);
                    System.out.println("Field: " + "Field total = " + fieldSum(fieldValues) + "\n" + Deck.toString(field));
                }
                if(nonDealerChoice > nonDealerHand.size() - 1){
                    nonDealerChoice = 0;
                }
            }
        }
        if(player == 1){
            boolean doesOpponentSayGo = false;
            boolean opponentSearching = false;
            while(dealerHand.size() + nonDealerHand.size() > 0){
                if(nonDealerHand.size() > 0 && dealerChoice == 0 && doesOpponentSayGo == false && opponentSearching == false){
                    nonDealerChoice = GameDisplay.promptNumberReadLine(s, "Play a card or go" + "\n" + "Non Dealer Hand: " + "\n" + Deck.toString(nonDealerHand), nonDealerHand.size());
                    if(nonDealerChoice == -1){
                        System.out.println("Your opponent scored 1 point from a go from you.");
                        GameDisplay.setOpponentTotalScore(1);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                        if(dealerChoice > dealerHand.size() - 1){
                            nonDealerChoice = 0;
                        }
                        continue;
                    }
                    field.add(nonDealerHand.get(nonDealerChoice - 1));
                    updateFieldValues(fieldValues, field);
                    updateFieldValuesForRuns(fieldValuesForRuns, field);
                    if(fieldSum(fieldValues) > 31){
                        overThirtyOne(nonDealerHand);
                    }
                    if(pairsCounting(field) > 0){
                        System.out.println("You scored " + pairsCounting(field)+ " points in pairs");
                        GameDisplay.setPlayerTotalScore(pairsCounting(field));
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(runsCounting(fieldValuesForRuns) > 0){
                        System.out.println("You scored " + runsCounting(fieldValuesForRuns)+ " points in runs");
                        GameDisplay.setPlayerTotalScore(runsCounting(fieldValuesForRuns));
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(fieldSum(fieldValues) == 15){
                        System.out.println("You scored 2 points in fifteens");
                        GameDisplay.setPlayerTotalScore(2);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(thirtyOne(fieldValues) == true){
                        System.out.println("You scored 2 points for hitting 31");
                        GameDisplay.setPlayerTotalScore(2);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                    }
                    if(dealerHand.size() == 0 && nonDealerHand.size() > 0){
                        System.out.println("You scored 1 point for last card");
                        GameDisplay.setPlayerTotalScore(1);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    nonDealerHand.remove(nonDealerChoice - 1);
                    System.out.println("Field: " + "Field total = " + fieldSum(fieldValues) + "\n" + Deck.toString(field));
                }
                if(dealerChoice >= dealerHand.size() && dealerHand.size() != 0 && nonDealerHand.size() != 0 && field.size() > 0 && opponentSearching == true){
                    System.out.println("Dealer says go");
                    int nonDealerPlayDecider = GameDisplay.promptNumberReadLine(s, "Do you have a card to play? Enter Yes or No.", 0);
                    if(nonDealerPlayDecider == -1){
                        System.out.println("You scored 1 point for a go from your opponent");
                        GameDisplay.setPlayerTotalScore(1);
                        if(GameDisplay.getPlayerTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                        dealerChoice = 0;
                        doesOpponentSayGo = true;
                        continue;
                    }
                    else{
                        opponentSearching = false;
                        continue;
                    }
                }
                else if(dealerChoice >= dealerHand.size() && dealerHand.size() != 0 && nonDealerHand.size() == 0 && field.size() > 0 && opponentSearching == true){
                    System.out.println("Dealer says go and you have no cards to play.");
                    System.out.println("You scored 1 point for a go from your opponent.");
                    GameDisplay.setPlayerTotalScore(1);
                    if(GameDisplay.getPlayerTotalScore() > 120){
                        gameEnd = true;
                        break;
                    }
                    field.clear();
                    updateFieldValues(fieldValues, field);
                    updateFieldValuesForRuns(fieldValuesForRuns, field);
                    dealerChoice = 0;
                    doesOpponentSayGo = true;
                    continue;
                }
                if(dealerChoice < dealerHand.size()){
                    doesOpponentSayGo = false;
                    if(dealerHand.size() > 0 && nonDealerHand.size() == 0){
                        if(fieldSum(fieldValues) < 31 && field.size() > 4){
                            field.clear();
                            updateFieldValues(fieldValues, field);
                            updateFieldValuesForRuns(fieldValuesForRuns, field);
                        }
                    }
                    field.add(dealerHand.get(dealerChoice));
                    updateFieldValues(fieldValues, field);
                    updateFieldValuesForRuns(fieldValuesForRuns, field);
                    opponentSearching = false;
                    if(fieldSum(fieldValues) > 31){
                        field.remove(field.size() - 1);
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                        dealerChoice = dealerChoice + 1;
                        opponentSearching = true;
                        continue;
                    }
                    System.out.println("Opponent played " + dealerHand.get(dealerChoice).toString());
                    if(pairsCounting(field) > 0){
                        System.out.println("Opponent scored " + pairsCounting(field) + " points in pairs");
                        GameDisplay.setOpponentTotalScore(pairsCounting(field));
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(runsCounting(fieldValuesForRuns) > 0){
                        System.out.println("Opponent scored " + runsCounting(fieldValuesForRuns) + " points in runs");
                        GameDisplay.setOpponentTotalScore(runsCounting(fieldValuesForRuns));
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(fieldSum(fieldValues) == 15){
                        System.out.println("Opponent scored 2 points in fifteens");
                        GameDisplay.setOpponentTotalScore(2);
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    if(thirtyOne(fieldValues) == true){
                        System.out.println("Opponent scored 2 points by hitting 31");
                        GameDisplay.setOpponentTotalScore(2);
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                        field.clear();
                        updateFieldValues(fieldValues, field);
                        updateFieldValuesForRuns(fieldValuesForRuns, field);
                    }
                    if(dealerHand.size() == 1 && nonDealerHand.size() == 0){
                        System.out.println("Opponent scored 1 point for last card");
                        GameDisplay.setOpponentTotalScore(1);
                        if(GameDisplay.getOpponentTotalScore() > 120){
                            gameEnd = true;
                            break;
                        }
                    }
                    dealerHand.remove(dealerChoice);
                    System.out.println("Field: " + "Field total = " + fieldSum(fieldValues) + "\n" + Deck.toString(field));
                    dealerChoice = 0;
                }
            }
        }
    }
    
    public static void updateFieldValues(ArrayList<Integer> fieldOutput, ArrayList<Card> fieldInput){
        fieldOutput.clear();
        Deck.handValues(fieldOutput, fieldInput);
    }
    
    public static void updateFieldValuesForRuns(ArrayList<Integer> fieldOutput, ArrayList<Card> fieldInput){
        fieldOutput.clear();
        Deck.handValuesForRuns(fieldOutput, fieldInput);
    }
    
    public static int pairsCounting(ArrayList<Card> a){
        int score = 0;
        int round = a.size() - 1;
        if(a.size() < 2){
            return 0;
        }
        for(int i = round; i > 0; i--){
            Card c = a.get(i);
            String value = c.getValue();
            Card c2 = a.get(i - 1);
            String value2 = c2.getValue();
            if(value == value2){
                score = score + 2;
            }
            else{
                break;
            }
        }
        if(score == 8){
            score = 12;
        }
        if(score == 4){
            score = 6;
        }
        return score;
    }
    
    public static int runsCounting(ArrayList<Integer> num){
        if(num.size() < 3){
            return 0;
        }
        int round = num.size() - 1;
        int runScore = 1;
        int diff = Math.abs(num.get(round) - num.get(round - 1));
        int outerDiff = Math.abs(num.get(0) - num.get(round));
        if(diff == 0){
            return 0;
        }
        else if(diff > round){
            return 0;
        }
        else if(outerDiff > round){
            return 0;
        }
        for(int i = 2; i > 0; i--){
            if(num.get(round) == num.get(i) && i != round){
                break;
            }
            diff = Math.abs(num.get(i) - num.get(i - 1));
            if(diff > num.size() - 1 || diff == 0){
                break;
            }
            else if(diff == 1 || Math.abs(num.get(i - 1) - num.get(round)) == 1){
                runScore++;
            }
            else if(diff > 1 && Math.abs(num.get(round) - num.get(round - diff)) == 1){
                runScore++;
            }
        }
        if(num.size() > 3){
            int[] a = new int[2];
            int largest = 0;
            int smallest = 99;
            for(int i = 0; i < round; i++){
                if(num.get(i) > largest){
                    largest = num.get(i);
                    a[1] = largest;
                }
                if(num.get(i) < smallest){
                    smallest = num.get(i);
                    a[0] = smallest;
                }
            }
            if(num.get(round) - 1 == a[0] || num.get(round) + 1 == a[0] || num.get(round) - 1 == a[1] || num.get(round) + 1 == a[1]){
                runScore = runScore + (num.size() - 3);
            }
            else{
                return 0;
            }
        }
        if(runScore >= 3){
            return runScore;
        }
        else{
            return 0;
        }
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
            updateFieldValues(fieldValues, field);
            updateFieldValuesForRuns(fieldValuesForRuns, field);
            int choice = GameDisplay.promptNumberReadLine(s, "Play a different card or go.", c.size()); // prompt for a new choice
            if(choice == -1){ // if player says go
                GameDisplay.setOpponentTotalScore(1);
                if(GameDisplay.getOpponentTotalScore() > 120){
                    gameEnd = true;
                    break;
                }
                field.clear(); // clear the field
                updateFieldValues(fieldValues, field);
                updateFieldValuesForRuns(fieldValuesForRuns, field);
                over = false;
                break;
            }
            field.add(c.get(choice - 1)); // otherwise add new choice to the field\
            updateFieldValues(fieldValues, field);
            updateFieldValuesForRuns(fieldValuesForRuns, field);
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
                updateFieldValues(fieldValues, field);
                updateFieldValuesForRuns(fieldValuesForRuns, field);
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