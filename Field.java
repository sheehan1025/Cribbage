import java.util.NoSuchElementException;

public class Field {

    FieldNode head;
    FieldNode tail;

    public class FieldNode {
        Card card;
        FieldNode next;
        FieldNode prev;
    
        public FieldNode(Card c) {
            card = c;
            next = null;
            prev = null;
        }

        public FieldNode getPreviousNode() {
            return prev;
        }
    }
    
    public FieldNode getTail(){
        return tail;
    }

    public void addCard(Card card) {
        FieldNode newNode = new FieldNode(card);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }
    
    public void printField() {
        FieldNode node = head;
        while (node != null) {
            System.out.print(node.card + " ");
            node = node.next;
        }
    }

    public void clearField() {
        head = null;
    }

    public Card getCard(int index) {
        FieldNode current = head;
        int count = 0;
        
        while (current != null) {
            if (count == index) {
                return current.card;
            }
            count++;
            current = current.next;
        }
        
        throw new IndexOutOfBoundsException();
    }

    public FieldNode getFieldNode(int index) {
        FieldNode current = head;
        int count = 0;
        
        while (current != null) {
            if (count == index) {
                return current;
            }
            count++;
            current = current.next;
        }
        
        throw new IndexOutOfBoundsException();
    }


    public int size() {
        FieldNode current = head;
        int count = 0;
    
        while (current != null) {
            count++;
            current = current.next;
        }
    
        return count;
    }

    public int getSum() {
        FieldNode current = head;
        int sum = 0;
    
        while (current != null) {
            sum += current.card.getValue();
            current = current.next;
        }
    
        return sum;
    }

    public Card getPreviousCard(FieldNode node) {
        if (node != null && node.prev != null) {
            return node.prev.card;
        } else {
            throw new NoSuchElementException();
        }
    }
}

