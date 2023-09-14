public class Card {
    private String suit;
    private int value;
    
    public Card(String suit, int value){
        this.suit = suit;
        this.value = value;
    }
    
    public String getSuit(){
        return suit;
    }
    public void setSuit(String suit){
        this.suit = suit;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }

    public String toString(){
        String newVal = "";
        if(value == 1){
            newVal = "Ace";
        }
        else if(value == 11){
            newVal = "Jack";
        }
        else if(value == 12){
            newVal = "Queen";
        }
        else if(value == 13){
            newVal = "King";
        }
        if(newVal == ""){
            return value + " of "+ suit + "s";
        }
        return newVal + " of "+ suit + "s";
    }
}


