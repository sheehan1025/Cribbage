import java.util.*;


public class Scoring{

    private ArrayList<Card> sortedHand = new ArrayList<Card> ();

    public Scoring(){

    }

    public Scoring(ArrayList<Card> hand){
        this.sortedHand = hand;
        sortHand(this.sortedHand);
    }

    public int totalScore(Card topCard){
        int totScore = pairs(this.sortedHand);
        totScore += fifteens(this.sortedHand);
        totScore += flush(this.sortedHand);
        totScore += runs(this.sortedHand);
        totScore += nobs(this.sortedHand, topCard);
        totScore += nibs(topCard);
        return totScore;
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
        int[] adjustedHand = faceValueAdjust(arr);
        List<List<Integer>> fifteenList = fifteens(adjustedHand, 15);
        return fifteenList.size() * 2;
    }

    private int[] faceValueAdjust(ArrayList<Card> arr){
        int[] ret_lst = new int[arr.size()];
        for(int i = 0; i < ret_lst.length; i++){
            if(arr.get(i).getValue() > 10){
                ret_lst[i] = 10;
            }
            else{
                ret_lst[i] = arr.get(i).getValue();
            }
        }
        return ret_lst;
    }

    private List<List<Integer>> fifteens(int [] arr, int sum) {
        List<List<Integer>>[][] dp = new ArrayList[arr.length + 1][sum + 1];
        for (int i = 0; i <= arr.length; i++) {
            for (int j = 0; j <= sum; j++) {
                dp[i][j] = new ArrayList<>();
                if (j == 0) {
                    dp[i][j].add(new ArrayList<>());
                } else if (i != 0) {
                    dp[i][j].addAll(dp[i - 1][j]);
                    if (arr[i - 1] <= j) {
                        for (List<Integer> subset : dp[i - 1][j - arr[i - 1]]) {
                            List<Integer> newSubset = new ArrayList<>(subset);
                            newSubset.add(arr[i - 1]);
                            dp[i][j].add(newSubset);
                        }
                    }
                }
            }
        }
        return dp[arr.length][sum];
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
        return 0;
    }
    
    public int nobs(ArrayList<Card> a, Card c){
        String topSuit = c.getSuit();
        for(Card x : a){
            if(x.getValue() == 11 && x.getSuit() == topSuit){
                return 1;
            }
        }
        return 0;
    }
}