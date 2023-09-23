import java.util.Random;


public class Counting{
    private String playerName;
    private CribbageGame cribGame;
    
    public Counting(String playerName, CribbageGame cribGame){
        this.playerName = playerName;
        this.cribGame = cribGame;
    }
    
    public void countingRound(ArrayList<Card> playerHandCounting, ArrayList<Card> opponentHandCounting, boolean playerIsDealer){
        Field field = new Field();
        ArrayList<Card> playerHand = playerHandCounting;
        ArrayList<Card> opponentHand = opponentHandCounting;
        
        boolean doesPlayerSayGo = false;
        boolean doesOppSayGo = false;
        boolean playerLastCard = false;
        boolean oppGoesFirst = true;
       
        //player is dealer
        if(playerIsDealer){
            while(!isCountingDone(playerHand, opponentHand)){
                //opp turn
                doesOppSayGo = opponentTurn(field, opponentHand, doesPlayerSayGo, oppGoesFirst);
                oppGoesFirst = false;
                if(!isCardAvailable(opponentHand) && isCardAvailable(playerHand)) playerLastCard = true;
                if(this.cribGame.statusCheck()) break;
                //player turn
                field.printField();
                doesPlayerSayGo = playerTurn(field, playerHand, doesOppSayGo);
                if(this.cribGame.statusCheck()) break;
            }
        }
        //player is non-dealer
        else{
            oppGoesFirst = false;
            while(!isCountingDone(playerHand, opponentHand)){
                //player turn
                field.printField();
                doesPlayerSayGo = playerTurn(field, playerHand, doesOppSayGo);
                if(this.cribGame.statusCheck()) break;
                //opp turn
                doesOppSayGo = opponentTurn(field, opponentHand, doesPlayerSayGo, oppGoesFirst);
                if(!isCardAvailable(opponentHand) && isCardAvailable(playerHand)) playerLastCard = true;
                if(this.cribGame.statusCheck()) break;
            }
        }
        if(this.cribGame.statusCheck()) return;
        if(playerLastCard){
            System.out.println("You scored 1 point for last card.");
            this.cribGame.increasePlayerTotalScore(1);
        }
        else{
            System.out.println("Opponent scored 1 point for last card.");
            this.cribGame.increaseOpponentTotalScore(1);
        }
    }
    
    public boolean opponentTurn(Field f, ArrayList<Card> opponentHand, boolean doesPlayerSayGo, boolean oppGoesFirst){
        //check if opp can play a card
        System.out.println("Opponent's turn.");
        boolean oppGo = saysGo(f, opponentHand);
        if(!oppGo || (doesPlayerSayGo && !oppGo)){
            //has a playable card, determine optimal card unless first play
            Card oppChoice = opponentCardChoice(f, opponentHand, oppGoesFirst);
            f.addCard(oppChoice);
            System.out.println("Opponent played " + oppChoice);
            this.cribGame.increaseOpponentTotalScore(pointsCheck(f, "Opponent"));
            opponentHand.remove(oppChoice);
            return false;
        }
        //Opponent gets a go from player but cant play a card
        else if(doesPlayerSayGo && oppGo){
            System.out.println("Opponent Scores 1 point for a go.");
            this.cribGame.increaseOpponentTotalScore(1);
            f.clearField();
            return false;
        }
        //opponent says go
        else if(oppGo){
            System.out.println("Opponent says Go.");
            return true;
        }
        return false;
    }
    
    public boolean playerTurn(Field f, ArrayList<Card> playerHand, boolean oppSayGo){
        //player makes choice
        Scanner s = new Scanner(System.in);
        System.out.println("Player Turn");
        while(true){
            int handIndex = 0;
            String playerChoice = playerInput(s, playerHand);
            //format choice to an int if possible
            try {
                handIndex = Integer.parseInt(playerChoice) - 1;
                if(playerHand.size() - 1 < handIndex || handIndex < -1){
                    System.out.println("Invalid entry");
                    continue;
                }
            } catch(NumberFormatException e) {
                System.out.println("Invalid entry.");
                continue;
            }
            //player says go
            if(handIndex == -1){
                if(!saysGo(f, playerHand)){
                    System.out.println("Cards available to play.");
                    continue;
                }
                if(oppSayGo){
                    System.out.println("Player Scores 1 point for a go.");
                    this.cribGame.increasePlayerTotalScore(1);
                    f.clearField();
                    return false;
                }
                return true;
            }
            //no go, player plays a card, check validity
            Card playerCard = playerHand.get(handIndex);
            f.addCard(playerCard);
            if(overThirtyOne(f)){
                f.deleteLastCard();
                System.out.println("Invalid play.");
                continue;
            }
            playerHand.remove(playerCard);
            this.cribGame.increasePlayerTotalScore(pointsCheck(f, this.playerName));
            return false;
        }
    }
    
    
    public boolean isCardAvailable(ArrayList<Card> hand){
        return hand.size() > 0;
    }

    public boolean isCountingDone(ArrayList<Card> dealerHand, ArrayList<Card> nonDealerHand){
        if(isCardAvailable(nonDealerHand) || isCardAvailable(dealerHand)){
            return false;
        }
        return true;
    }
    
    public Card opponentCardChoice(Field f, ArrayList<Card> o, boolean playFirst){
        //chose a random card if they play first
        Random r = new Random();
        if(playFirst){
            int choice = 3;//r.nextInt(4);
            return o.get(choice);
        }
        //deteremine what card will score the most points
        int maxScore = 0;
        int scoreInd = 0;
        ArrayList<Card> validCards = new ArrayList<Card>();
        for(int i = scoreInd; i < o.size(); i++){
            int currScore = 0;
            Card oppChoice = o.get(i);
            f.addCard(oppChoice);
            if(overThirtyOne(f)){
                f.deleteLastCard();
                continue;
            }
            currScore = opponentPointCountCheck(f);
            if(currScore > maxScore){
                maxScore = currScore;
                scoreInd = i;
            }
            validCards.add(oppChoice);
            f.deleteLastCard();
        }
        //chose a random card that can be played if no points possible
        if(maxScore == 0 && validCards.size() > 0){
            int n = r.nextInt(validCards.size());
            return o.get(n);
        }
        return o.get(scoreInd);
    }
    
    private int opponentPointCountCheck(Field f){
        int points = 0;
        int pair = pairsCounting(f);
        int run = runsCounting(f);
        boolean fifteenCheck = fifteen(f);
        boolean thirtyOneCheck = thirtyOne(f);
        
        if(fifteenCheck) points += 2;
        if(thirtyOneCheck) points += 2;
        points += pair;
        points += run;
        return points;
    }
    
    public boolean saysGo(Field f, ArrayList<Card> o){
        for(int i = 0; i < o.size(); i++){
            Card oppChoice = o.get(i);
            f.addCard(oppChoice);
            if(!overThirtyOne(f)){
                f.deleteLastCard();
                return false;
            }
            f.deleteLastCard();
        }
        return true;
    }
    
    public int pointsCheck(Field field, String player){
        int score = 0;
        int pairsScore = pairsCounting(field);
        int runsScore = runsCounting(field);
        boolean hitFifteen = fifteen(field);
        boolean hitThirtyOne = thirtyOne(field);
        if(pairsScore > 0){
            System.out.println(player +" scored " + pairsScore + " points in pairs");
        }
        if(runsScore > 0){
            System.out.println(player +" scored " + runsScore + " points in runs");
        }
        if(hitFifteen){
            System.out.println(player + " scored " + "2 points in fifteens");
            score += 2;
        }
        if(hitThirtyOne){
            System.out.println(player + " scored " + "2 points by hitting 31");
            score += 2;
            field.clearField();
        } 
        return score + pairsScore + runsScore;
    }

    public int pairsCounting(Field f){
        int score = 0;
        int count = 1;
        int fieldSize = f.size() - 1;
        while(fieldSize > 0){
            if(f.getTail().card.getValue() == f.getFieldNode(fieldSize - 1).card.getValue()){
                count++;
                fieldSize--;    
            }
            else{
                break;
            }
        }
        return score + count * (count - 1);
    }
    
    public int runsCounting(Field f){
        int fieldSize = f.size();
        if(fieldSize < 3) return 0;
        
        ArrayList<Integer> runList = new ArrayList<Integer>();
        for(int i = 0; i < fieldSize; i++) runList.add(f.getCard(i).getValue());
        
        int maxRunLength = 1;
        ArrayList<Integer> sortRunList = new ArrayList<Integer>();
        for(int i = fieldSize - 1; i >= fieldSize - 3; i--) sortRunList.add(runList.get(i));
        
        Collections.sort(sortRunList);
        int currRunLength = scanRun(sortRunList);
        if(currRunLength > maxRunLength) maxRunLength = currRunLength;
        
        while(sortRunList.size() < fieldSize){
            sortRunList.add(runList.get(runList.size() - sortRunList.size() - 1));
            Collections.sort(sortRunList);
            currRunLength = scanRun(sortRunList);
            if(currRunLength > maxRunLength) maxRunLength = currRunLength;
        }
        
        if(maxRunLength >= 3) return maxRunLength;
        return 0;
    }
    
    public String playerInput(Scanner s, ArrayList<Card> h){
        System.out.println("Play a card or press 0 for a go." + "\n" + "Player Hand: " + "\n" + CribbageGame.printHand(h));
        String playerSelection = s.next();
        return playerSelection;
    }
    
    private int scanRun(ArrayList<Integer> run){
        int runLength = 1;
        for(int i = 0; i < run.size() - 1; i++){
            if(run.get(i) + 1 == run.get(i + 1)) runLength++;
            else return 0;
        }
        return runLength;
    }
    
    public boolean thirtyOne(Field f){
        return f.getSum() == 31;
    }
    
    public boolean fifteen(Field f){
        return f.getSum() == 15;
    }
    
    public boolean overThirtyOne(Field f){
        return f.getSum() > 31;
    }
    
}
