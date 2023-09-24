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
        System.out.println("=================================================");
        System.out.println("Field Total: " + getSum());
        System.out.println("-------------------------------------------------");
        while (node != null) {
            System.out.print(node.card + "\n");
            node = node.next;
        }
        System.out.println("=================================================");
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
            if(current.card.getValue() > 10) sum += 10; 
            else sum += current.card.getValue();
            current = current.next;
        }
    
        return sum;
    }
    
    public void deleteLastCard() {
        if (head == null) return;

        if (head == tail) {
            head = null;
            tail = null;
            return;
        }
        tail.prev.next = null;
        tail = tail.prev;
    }
}
