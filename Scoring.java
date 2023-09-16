import java.util.*;


public class Scoring{

    private ArrayList<Card> sortedHand = new ArrayList<Card> ();

    public Scoring(){

    }

    public Scoring(ArrayList<Card> hand){
        this.sortedHand = hand;
        sortHand(this.sortedHand);
    }

    private void sortHand(ArrayList<Card> hand){
        int n = hand.size();
        for (int i = 1; i < n; ++i) {
            int key = hand.get(i).getValue();
            Card cardKey = hand.get(i);
            int j = i - 1;
            while (j >= 0 && hand.get(j).getValue() > key) {
                hand.set(j + 1, hand.get(j));
                j = j - 1;
            }
            hand.set(j + 1, cardKey);
        }
    }

    public int pairs(ArrayList<Card> a){
        int score = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Card c : a){
            map.put(c.getValue(), map.getOrDefault(c.getValue(), 0) + 1);
        }
        int count = 0;
        for (int freq : map.values()) {
            count += freq * (freq - 1) / 2;
        }
        score = score +  2 * count;
        return score;
    }
    
    public int fifteens(ArrayList<Card> arr){
        List<List<Integer>> fifteenList = fifteens(arr, 15);
        System.out.println(fifteenList);
        return fifteenList.size() * 2;
    }

    private List<List<Integer>> fifteens(ArrayList<Card> arr, int sum) {
        List<List<Integer>>[][] dp = new ArrayList[sum + 1][arr.size() + 1];
        for (int i = 0; i <= arr.size(); i++) {
            dp[0][i] = new ArrayList<>();
            dp[0][i].add(new ArrayList<>());
        }

        for (int i = 1; i <= sum; i++) {
            for (int j = 0; j <= arr.size(); j++) {
                dp[i][j] = new ArrayList<>();
                if (j > 0) {
                    dp[i][j].addAll(dp[i][j - 1]);
                    if (i >= arr.get(j - 1).getValue()) {
                        for (List<Integer> subset : dp[i - arr.get(j - 1).getValue()][j - 1]) {
                            List<Integer> newSubset = new ArrayList<>(subset);
                            newSubset.add(arr.get(j - 1).getValue());
                            dp[i][j].add(newSubset);
                        }
                    }
                }
            }
        }
        return dp[sum][arr.size()];
    }

    
    public int flush(ArrayList<Card> a){
        HashMap<String, Integer> map = new HashMap<>();
        int flushScore = 0;
        for(Card c : a){
            map.put(c.getSuit(), map.getOrDefault(c.getSuit(), 0) + 1);
        }
        for (int freq : map.values()) {
            if(freq >= 4){
                flushScore = freq;
            }
        }
        return flushScore;
    }
    
    public int runs(ArrayList<Card> a){
        int runCount = 0;
        int currRunCount = 1;
        boolean isDoubleRun = false;
        boolean isTripleRun = false;
        for(int i = 1; i < a.size(); i++){
            if(a.get(i).getValue() == a.get(i - 1).getValue() + 1){
                currRunCount++;
            }
            else if(a.get(i).getValue() == a.get(i - 1).getValue() && isDoubleRun){
                isTripleRun = true;
            }
            else if(a.get(i).getValue() == a.get(i - 1).getValue()){
                isDoubleRun = true;
            }
            else{
                if (currRunCount >= 3) {
                    if (isDoubleRun && !isTripleRun) {
                        runCount += currRunCount * 2;
                    }
                    else if (isTripleRun) {
                        runCount += currRunCount * 3;
                    }
                    else {
                        runCount += currRunCount;
                    }
                }
                currRunCount = 1;
                isDoubleRun = false;
                isTripleRun = false;
            }
        }
        if (currRunCount >= 3) {
            if (isDoubleRun && !isTripleRun) {
                runCount += currRunCount * 2;
            }
            else if (isTripleRun) {
                runCount += currRunCount * 3;
            }
            else {
                runCount += currRunCount;
            }
        }
        return runCount;
    }


    public int nibs(Card c){
        int jackCheck = c.getValue();
        if(jackCheck == 11){
            return 2;
        }
        else{
            return 0;
        }
    }
    
    public static int nobs(ArrayList<Card> a, Card c){
        String topSuit = c.getSuit();
        Card store = a.remove(a.size() - 1);
        for(Card x : a){
            String cardSearch = x.getValue() + " " + x.getSuit();
            if(cardSearch.contains("Jack") && cardSearch.contains(topSuit)){
                a.add(store);
                return 1;
            }
        }
        a.add(store);
        return 0;
    }
}