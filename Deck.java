import java.util.*;
import java.util.Random;

public class Deck{
    private static ArrayList<Card> cards = new ArrayList<Card>();
    private static Random r = new Random();
    
    Integer[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};
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
