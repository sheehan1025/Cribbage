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
        assertEquals(4, score.flush(fourFlush));

        ArrayList<Card> fiveFlush = fourFlush;
        fiveFlush.set(4, new Card("Spade", 6));
        assertEquals(5, score.flush(fiveFlush));

        ArrayList<Card> noFlush = new ArrayList<Card>();
        noFlush.add(new Card("Spade", 1));
        noFlush.add(new Card("Diamond", 1));
        noFlush.add(new Card("Heart", 1));
        noFlush.add(new Card("Club", 1));
        noFlush.add(new Card("Club", 2));
        assertEquals(0, score.flush(noFlush));

    }

    @Test
    public void fifteensTest(){
        ArrayList<Card> maxFifteens = new ArrayList<Card>();
        maxFifteens.add(new Card("Spade", 5));
        maxFifteens.add(new Card("Diamond", 5));
        maxFifteens.add(new Card("Heart", 5));
        maxFifteens.add(new Card("Club", 5));
        maxFifteens.add(new Card("Club", 10));
        assertEquals(16, score.fifteens(maxFifteens));

        ArrayList<Card> noFifteens = new ArrayList<Card>();
        noFifteens.add(new Card("Spade", 1));
        noFifteens.add(new Card("Diamond", 3));
        noFifteens.add(new Card("Heart", 10));
        noFifteens.add(new Card("Club", 10));
        noFifteens.add(new Card("Club", 8));
        assertEquals(0, score.fifteens(noFifteens));

        ArrayList<Card> midFifteens = new ArrayList<Card>();
        midFifteens.add(new Card("Spade", 6));
        midFifteens.add(new Card("Diamond", 6));
        midFifteens.add(new Card("Heart", 6));
        midFifteens.add(new Card("Club", 5));
        midFifteens.add(new Card("Club", 9));
        assertEquals(6, score.fifteens(midFifteens));
    }

    @Test
    public void runsTest(){
        ArrayList<Card> singleRun = new ArrayList<Card>();
        singleRun.add(new Card("Spade", 1));
        singleRun.add(new Card("Diamond", 2));
        singleRun.add(new Card("Heart", 3));
        singleRun.add(new Card("Club", 7));
        singleRun.add(new Card("Club", 8));
        assertEquals(3, score.runs(singleRun));

        ArrayList<Card> doubleRun = new ArrayList<Card>();
        doubleRun.add(new Card("Spade", 1));
        doubleRun.add(new Card("Diamond", 3));
        doubleRun.add(new Card("Heart", 4));
        doubleRun.add(new Card("Club", 5));
        doubleRun.add(new Card("Club", 5));
        assertEquals(6, score.runs(doubleRun));

        ArrayList<Card> tripleRun = new ArrayList<Card>();
        tripleRun.add(new Card("Spade", 1));
        tripleRun.add(new Card("Diamond", 2));
        tripleRun.add(new Card("Heart", 2));
        tripleRun.add(new Card("Club", 2));
        tripleRun.add(new Card("Club", 3));
        assertEquals(9, score.runs(tripleRun));

        ArrayList<Card> noRun = new ArrayList<Card>();
        noRun.add(new Card("Spade", 1));
        noRun.add(new Card("Diamond", 2));
        noRun.add(new Card("Heart", 4));
        noRun.add(new Card("Club", 5));
        noRun.add(new Card("Club", 7));
        assertEquals(0, score.runs(noRun));

    }

}
