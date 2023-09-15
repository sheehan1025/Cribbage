import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.*;


public class Tests {
    
    @Test
    public void pairsTest(){

        Scoring score = new Scoring();

        ArrayList<Card> onePair = new ArrayList<Card>();
        onePair.add(new Card("Spade", 1));
        onePair.add(new Card("Diamond", 1));
        assertEquals(2, score.pairs(onePair));

        ArrayList<Card> twoPair = onePair;
        twoPair.add(new Card("Heart", 1));
        assertEquals(6, score.pairs(twoPair));

        ArrayList<Card> threePair = twoPair;
        threePair.add(new Card("Club", 1));
        assertEquals(12, score.pairs(threePair));

        ArrayList<Card> maxPair = threePair;
        maxPair.add(new Card("Club", 2));
        maxPair.add(new Card("Heart", 2));
        assertEquals(14, score.pairs(maxPair));
    }
}
