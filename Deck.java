import java.util.*;
import java.util.Arrays;
import java.util.Random;

public class Deck{
    private static ArrayList<Card> cards = new ArrayList<Card>();
    private static Random r = new Random();
    
    String[] values = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
    String[] suit = {"Club", "Spade", "Diamond", "Heart"};

    public Deck(){
        cards.clear();
        for (int i = 0; i < suit.length; i++) {
            for(int j = 0; j < values.length; j++){
                cards.add(new Card(suit[i],values[j]));
            }
        }
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getDeck(){
        return cards;
    }
    
    public static ArrayList<Card> reset(){
        Collections.shuffle(cards);
        return cards;
    }
    
    public ArrayList<Card> getDealerHand(){
        ArrayList<Card> hand = new ArrayList<Card>();
        for(int i = 1; i < 12; i += 2){
            hand.add(cards.get(i));
        }
        return hand;
    }
    
    public ArrayList<Card> getNonDealerHand(){
        ArrayList<Card> hand = new ArrayList<Card>();
        for(int i = 0; i < 11; i += 2){
            hand.add(cards.get(i));
        }
        return hand;
    }
    
    public static ArrayList<Card> removeCards(ArrayList<Card> a){
        int check = 0;
        while(check < 12){
            a.remove(0);
            check++;
        }
        return a;
    }
    
    public static Card getTopCard(ArrayList<Card> a){
        return a.get(r.nextInt(40));
    } 
    
    public static void handValues(ArrayList<Integer> valueOutput, ArrayList<Card> handInput){
        for(Card c : handInput){
            String cardType = c.getValue() + c.getSuit();
            if(cardType.contains("10") || cardType.contains("Jack") || cardType.contains("Queen") || cardType.contains("King")){
                valueOutput.add(10);
            }
            else if(cardType.contains("Ace")){
                valueOutput.add(1);
            }
            else{
                int cardValue = Integer.parseInt(cardType.substring(0, 1));
                valueOutput.add(cardValue);
            }
        }
    }
    
    public static void handValuesForRuns(ArrayList<Integer> valueOutput, ArrayList<Card> handInput){
        for(Card c : handInput){
            String cardType = c.getValue() + c.getSuit();
            if(cardType.contains("10")){
                valueOutput.add(10);
            }
            else if(cardType.contains("Jack")){
                valueOutput.add(11);
            }
            else if(cardType.contains("Queen")){
                valueOutput.add(12);
            }
            else if(cardType.contains("King")){
                valueOutput.add(13);
            }
            else if(cardType.contains("Ace")){
                valueOutput.add(1);
            }
            else{
                int cardValue = Integer.parseInt(cardType.substring(0, 1));
                valueOutput.add(cardValue);
            }
        }
    }

    public static String toString(ArrayList<Card> a){
        String hand = "";
        for(int i = 0; i < a.size(); i++){
            hand += (i + 1) + ". " + a.get(i) + "\n";
        }
        return hand;
    }
    
    public static String toStringWithTopCard(ArrayList<Card> a){
        String hand = "";
        for(int i = 0; i < a.size() - 1; i++){
            hand += (i + 1) + ". " + a.get(i) + "\n";
        }
        hand += "Top Card: " + a.get(4) + "\n";
        return hand;
    }
}