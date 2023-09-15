import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.*;


public class Tests {

    Scoring score = new Scoring();

    @Test
    public void pairsTest(){
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

        ArrayList<Card> noPair = new ArrayList<Card>();
        noPair.add(new Card("Spade", 1));
        noPair.add(new Card("Club", 2));
        assertEquals(0, score.pairs(noPair));
    }

    @Test
    public void flushTest(){
        ArrayList<Card> fourFlush = new ArrayList<Card>();
        for(int i = 1; i < 5; i++){
            fourFlush.add(new Card("Spade", i));
        }
        fourFlush.add(new Card("Club", 2));
        fourFlush.add(new Card("Heart", 3));
        assertEquals(4, score.flush(fourFlush));

        ArrayList<Card> fiveFlush = fourFlush;
        fiveFlush.add(new Card("Spade", 6));
        fiveFlush.remove(5);
        assertEquals(5, score.flush(fiveFlush));
    }
}
