import java.util.*;


import java.util.*;


public class Deck{
    private static Deque<Card> cards;
    
    Integer[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};
    String[] suit = {"Club", "Spade", "Diamond", "Heart"};

    public Deck(){
        cards = new LinkedList<Card>();
        for (int i = 0; i < suit.length; i++) {
            for(int j = 0; j < values.length; j++){
                cards.add(new Card(suit[i],values[j]));
            }
        }
        ArrayList<Card> deckList = new ArrayList<Card>(cards);
        Collections.shuffle(deckList);
        Deque<Card> shuffledDeck = new LinkedList<Card>(deckList);
        cards = shuffledDeck;
    }
    
    public ArrayList<Card> getHand(){
        ArrayList<Card> hand = new ArrayList<Card>();
        for(int i = 0; i < 6; i++){
            hand.add(cards.pop());
        }
        return hand;
    }

    public Card getTopCard(){
        return cards.peek();
    } 
    
}
