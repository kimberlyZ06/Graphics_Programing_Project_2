import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};

        for (String s : suits) {
            for (String v : values) {
                deck.add(new Card(s, v));
            }
        }
    }

    public Card getRandomCard() {
        int random = (int)(Math.random() * deck.size());
        return deck.remove(random);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public boolean winning (Deck d) {
        if (d.getDeck().size() == 0){
            return true;
        }
        return false;
    }

    public boolean checkForMoves(Card[][] cards) {
//        losing
        ArrayList<Card> leftOnScreen = new ArrayList<>();
        int counter = 0;
        for (int row = 0; row < cards.length; row++) {
            for (int col = 0; col < cards.length; col++) {
                leftOnScreen.add(cards[row][col]);
            }
        }
        ArrayList<Integer> letterCardsLeft = new ArrayList<Integer>();
        for (int i = 0; i < leftOnScreen.size(); i++) {
            for (int j = i + 1; j < leftOnScreen.size(); j++) {
                //checks for 11
                if (Integer.parseInt(leftOnScreen.get(i).getValue()) + Integer.parseInt(leftOnScreen.get(j).getValue()) == 11){
                    counter++;
                }
            }
            //Checks Jack, Queen, King
            if (Integer.parseInt(leftOnScreen.get(i).getValue()) == 11 ||
                    Integer.parseInt(leftOnScreen.get(i).getValue()) == 12 ||
                    Integer.parseInt(leftOnScreen.get(i).getValue()) == 13){
                letterCardsLeft.add(Integer.parseInt(leftOnScreen.get(i).getValue()));
            }
            if (letterCardsLeft.contains(11) && letterCardsLeft.contains(12) &&
                    letterCardsLeft.contains(13)){
                counter++;
            }
        }
        if (counter == 0){
            return true;
        }
        return false;
    }

    public ArrayList<Card> hint (Card[][] cards){
        ArrayList<Card> leftOnScreen = new ArrayList<>();
        ArrayList<Card> hint = new ArrayList<>();
        for (int row = 0; row < cards.length; row++) {
            for (int col = 0; col < cards.length; col++) {
                leftOnScreen.add(cards[row][col]);
            }
        }
        ArrayList<Integer> letterCardsLeft = new ArrayList<Integer>();
        ArrayList<Card> letters = new ArrayList<>();
        for (int i = 0; i < leftOnScreen.size(); i++) {
            for (int j = i + 1; j < leftOnScreen.size(); j++) {
                //checks for 11
                if (Integer.parseInt(leftOnScreen.get(i).getValue()) + Integer.parseInt(leftOnScreen.get(j).getValue()) == 11){
                    hint.add(leftOnScreen.get(i));
                    hint.add(leftOnScreen.get(j));
                    return hint;
                }
            }
            //Checks Jack, Queen, King
            if (Integer.parseInt(leftOnScreen.get(i).getValue()) == 11 ||
                    Integer.parseInt(leftOnScreen.get(i).getValue()) == 12 ||
                    Integer.parseInt(leftOnScreen.get(i).getValue()) == 13){
                letterCardsLeft.add(Integer.parseInt(leftOnScreen.get(i).getValue()));
                letters.add(leftOnScreen.get(i));
            }
            if (letterCardsLeft.contains(11) && letterCardsLeft.contains(12) &&
                    letterCardsLeft.contains(13)){
                hint.add(letters.get(letterCardsLeft.indexOf(11)));
                hint.add(letters.get(letterCardsLeft.indexOf(12)));
                hint.add(letters.get(letterCardsLeft.indexOf(13)));
                return hint;
            }
        }
        return hint;
    }
}