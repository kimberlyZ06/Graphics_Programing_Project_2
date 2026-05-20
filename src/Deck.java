import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Deck(){
        String[] pics = {"diamonds", "clubs", "hearts", "spades"};
        String[] nums = {"A", "02", "03", "04", "05", "06", "07", "08", "09", "10", "J", "Q", "K"};
        for (int i = 0; i < pics.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                String cardNum = nums[j];
                Card card = new Card(pics[i], cardNum);
                cards.add(card);
            }
        }
    }


    public Card getRandomCard(){
        int cardRemoved = (int) (Math.random()*cards.size() - 1);
        cards.remove(cardRemoved);
        return cards.get(cardRemoved);
    }

}
