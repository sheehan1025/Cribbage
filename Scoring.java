import java.util.*;


public class Scoring{

    private ArrayList<Card> sortedHand = new ArrayList<Card> ();

    public Scoring(){

    }

    public Scoring(ArrayList<Card> hand){
        this.sortedHand = hand;
        sortHand(this.sortedHand);
    }

    void sortHand(ArrayList<Card> hand){
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
    
    public static int fifteens(ArrayList<Integer> num){
        int score = 0;
        Collections.sort(num);
        score = score + fifteens(num, 0, 0, 1);
        return score;
    }
    
    private static int fifteens(ArrayList<Integer> a, int sum, int first, int second){
        if(first == a.size() - 1 && second == a.size()){
            return fifteens(a, 0, 0, 1, 2);
        }
        if(second > a.size() - 1){
            return fifteens(a, 0, first + 1, first + 2);
        }
        else{
            ArrayList<Integer> sub = new ArrayList<Integer>(2);
            sub.add(a.get(first));
            sub.add(a.get(second));
            for(int i = 0; i < sub.size(); i++){
                sum = sum + sub.get(i);
            }
            if(sum == 15){
                return 2 + fifteens(a, 0, first, second + 1);
            }
            else{
                return fifteens(a, 0, first, second + 1);
            }
        }
    }
    private static int fifteens(ArrayList<Integer> a, int sum, int first, int second, int third){
        if(first == a.size() - 3 && second == a.size() - 2 && third == a.size()){
            return fifteens(a, 0, 0, 1, 2, 3);
        }
        if(third > a.size() - 1 && first == 1 && second == 3){
            return fifteens(a, 0, 2, 3, 4);
        }
        else if(third > a.size() - 1 && first == 1 && second == 2){
            return fifteens(a, 0, first, second + 1, second + 2);
        }
        else if(third > a.size() - 1 && first == 0 && second == 3){
            return fifteens(a, 0, first + 1, first + 2, first + 3);
        }
        else if(third > a.size() - 1 && first == 0){
            return fifteens(a, 0, first, second + 1, second + 2);
        }
        else{
            ArrayList<Integer> sub = new ArrayList<Integer>(3);
            sub.add(a.get(first));
            sub.add(a.get(second));
            sub.add(a.get(third));
            for(int i = 0; i < sub.size(); i++){
                sum = sum + sub.get(i);
            }
            if(sum == 15){
                return 2 + fifteens(a, 0, first, second, third + 1);
            }
            else{
                return fifteens(a, 0, first, second, third + 1);
            }
        }
    }
    private static int fifteens(ArrayList<Integer> a, int sum, int first, int second, int third, int fourth){
        if(first == a.size() - 4 && second == a.size() - 3 && third == a.size() - 2 && fourth == a.size()){
            return fifteens(a, 0);
        }
        if(fourth > a.size() - 1 && first == 0 && second == 2){
            return fifteens(a, 0, 1, 2, 3, 4);
        }
        else if(fourth > a.size() - 1 && first == 0 && third == 3){
            return fifteens(a, 0, first, second + 1, second + 2, second + 3);
        }
        else if(fourth > a.size() - 1 && first == 0){
            return fifteens(a, 0, first, second, third + 1, third + 2);
        }
        else{
            ArrayList<Integer> sub = new ArrayList<Integer>(4);
            sub.add(a.get(first));
            sub.add(a.get(second));
            sub.add(a.get(third));
            sub.add(a.get(fourth));
            for(int i = 0; i < sub.size(); i++){
                sum = sum + sub.get(i);
            }
            if(sum == 15){
                return 2 + fifteens(a, 0, first, second, third, fourth + 1);
            }
            else{
                return fifteens(a, 0, first, second, third, fourth + 1);
            }
        }
    }
    private static int fifteens(ArrayList<Integer> a, int sum){
        ArrayList<Integer> sub = new ArrayList<Integer>(5);
        for(int i = 0; i < sub.size(); i++){
            sub.add(a.get(i));
        }
        for(int i = 0; i < sub.size(); i++){
            sum = sum + sub.get(i);
        }
        if(sum == 15){
            return 2;
        }
        else{
            return 0;
        }
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
    
    public static int runs(ArrayList<Integer> num){
        int tempScore = 0;
        int duplicatesCount = 0;
        boolean duplicates = false;
        Collections.sort(num);
        for(int i = 0; i < num.size() - 1; i++){
            if(num.get(i) == num.get(i + 1)){
                duplicates = true;
                duplicatesCount++;
            }
        }
        if(duplicates == false){
            //run of 3
            for(int i = 0; i < 3; i++){
                if(num.get(i) + 1 == num.get(i + 1) && num.get(i) + 2 == num.get(i + 2)){
                    tempScore = 3;
                }
            }
            //run of 4
            for(int i = 0; i < 2; i++){
                if(num.get(i) + 1 == num.get(i + 1) && num.get(i) + 2 == num.get(i + 2) && num.get(i) + 3 == num.get(i + 3)){
                    tempScore = 4;
                }
            }
            //run of 5
            if(num.get(0) + 1 == num.get(1) && num.get(1) + 1 == num.get(2) && num.get(2) + 1 == num.get(3) && num.get(3) + 1 == num.get(4)){
                tempScore = 5;
            }
        }
        else if(duplicatesCount < 2 && duplicates == true){
            //double triple runs
            for(int i = 0; i < 2; i++){
                if((num.get(i) + 1 == num.get(i + 1) && num.get(i + 1) + 1 == num.get(i + 2) && num.get(i + 2) == num.get(i + 3)) ||
                    (num.get(i) + 1 == num.get(i + 1) && num.get(i + 1) == num.get(i + 2) && num.get(i + 2) + 1 == num.get(i + 3)) ||
                    (num.get(i) == num.get(i + 1) && num.get(i + 1) + 1 == num.get(i + 2) && num.get(i + 2) + 1 == num.get(i + 3))){
                    tempScore = tempScore + 6;
                    //double quad runs
                    if(i == 0 && num.get(3) + 1 == num.get(4)){
                        tempScore = tempScore - 4 ;
                    }
                    else if(i == 0 && num.get(3) == num.get(4)){
                        tempScore = tempScore + 2;
                    }
                }
            }
        }
        else{
            //triple triple runs
            //quad triple runs
            if((num.get(0) == num.get(1) && num.get(0) == num.get(2) && num.get(0) + 1 == num.get(3) && num.get(0) + 2 == num.get(4)) ||
               (num.get(0) + 1 == num.get(1) && num.get(1) == num.get(2) && num.get(2) == num.get(3) && num.get(0) + 2 == num.get(4)) ||
               (num.get(0) + 1 == num.get(1) && num.get(1) + 1 == num.get(2) && num.get(2) == num.get(3) && num.get(3) == num.get(4))){
                   tempScore = 9;
            }
            else if((num.get(0) == num.get(1) && num.get(1) + 1 == num.get(2) && num.get(2) == num.get(3) && num.get(3) + 1 == num.get(4)) ||
                    (num.get(0) == num.get(1) && num.get(1) + 1 == num.get(2) && num.get(2) + 1 == num.get(3) && num.get(3) == num.get(4)) ||
                    (num.get(0) + 1 == num.get(1) && num.get(1) == num.get(2) && num.get(2) + 1 == num.get(3) && num.get(3) == num.get(4))){
                        tempScore = 12;
            }
        }
        if(tempScore > 0){
            return tempScore;
        }
        else{
            return 0;
        }
    }
    
    public static int nibs(Card c){
        int jackCheck = c.getValue();
        if(jackCheck.contains("Jack")){
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