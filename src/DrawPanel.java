import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;

class DrawPanel extends JPanel implements MouseListener {

    private Deck d;
    private Card[][] cards;
    private boolean noMovesLeft = false;
    private boolean win = false;

    public DrawPanel() {
        cards = new Card[3][3];
        d = new Deck();
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards.length; c++) {
                cards[r][c] = d.getRandomCard();
            }
        }
        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards.length; c++) {
                g.drawImage(cards[r][c].getImage(), x, y, null);
                Rectangle cardHitBox = new Rectangle(x, y, cards[r][c].getImage().getWidth(), cards[r][c].getImage().getHeight());
                cards[r][c].setHitbox(cardHitBox);
                if (cards[r][c].getHighlight()) {
                    g.drawRect(x, y, (int)cardHitBox.getWidth(), (int)cardHitBox.getHeight());
                }
                x += 80;
            }
            y += 100;
            x = 50;
        }

        g.drawString("Number of cards left: " + d.getDeck().size(), x, y + 100);

        //play again
        g.drawRect(50, 300, 200, 50);
        g.drawString("Play Again", 120, 330);

        //replace cards
        g.drawRect(300, 50, 100, 50);
        g.drawString("Replace Cards", 310, 80);

        //shuffle
        g.drawRect(300, 130, 100, 50);
        g.drawString("Shuffle", 330, 160);

        //Hint
        g.drawRect(300, 210, 100, 50);
        g.drawString("Hint", 338, 240);

        if (win){
            g.drawString("There are " + d.getDeck().size() + " cards left!", 300, 300);
        }
        if (noMovesLeft){
            g.drawString("There are no valid moves! :(", 300, 300);
        }
    }

    public void mousePressed(MouseEvent e) {

        Point p = e.getPoint();
        int button = e.getButton();

        Rectangle playAgain = new Rectangle(50, 300, 200, 50);
        Rectangle replaceCard = new Rectangle(300,50,100,50);
        Rectangle shuffle = new Rectangle(300, 130, 100, 50);
        Rectangle hint = new Rectangle(300, 210, 100, 50);

        //highlight cards
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards.length; c++) {
                if (!d.getDeck().isEmpty() && button == 1) {
                    if (cards[r][c].getHitbox().contains(p)) {
                        cards[r][c].flipHighlight();
                    }
                }
                //check
                ArrayList<Card> highlighted = new ArrayList<>();
                for (int row = 0; row < cards.length; row++) {
                    for (int col = 0; col < cards.length; col++) {
                        if (cards[row][col].getHighlight()){
                            highlighted.add(cards[row][col]);
                        }
                    }
                }

                if (highlighted.size() == 2){
                    int firstVal = Integer.parseInt(highlighted.get(0).getValue());
                    int secVal = Integer.parseInt(highlighted.get(1).getValue());

                    if (firstVal + secVal == 11 && replaceCard.contains(p)){
                        for (int row = 0; row < cards.length; row++) {
                            for (int col = 0; col < cards.length; col++) {
                                if (cards[row][col].getHighlight()){
                                    cards[row][col] = d.getRandomCard();
                                    noMovesLeft = d.checkForMoves(cards);
                                    win = d.winning(d);
                                }
                            }
                        }
                    }
                } else if (highlighted.size() == 3) {
                    int firstVal = Integer.parseInt(highlighted.get(0).getValue());
                    int secVal = Integer.parseInt(highlighted.get(1).getValue());
                    int thirdVal = Integer.parseInt(highlighted.get(2).getValue());

                    if (firstVal + secVal + thirdVal == 36 && replaceCard.contains(p)){
                        for (int row = 0; row < cards.length; row++) {
                            for (int col = 0; col < cards.length; col++) {
                                if (cards[row][col].getHighlight()){
                                    cards[row][col] = d.getRandomCard();
                                    noMovesLeft = d.checkForMoves(cards);
                                    win = d.winning(d);
                                }
                            }
                        }
                    }
                }

                //play again
                if (button == 1 && playAgain.contains(p)) {
                    d = new Deck();
                    for (int row = 0; row < cards.length; row++) {
                        for (int col = 0; col < cards.length; col++) {
                            cards[row][col] = d.getRandomCard();
                            noMovesLeft = d.checkForMoves(cards);
                            win = d.winning(d);
                        }
                    }
                }

                //shuffle
                if (button == 1 && shuffle.contains(p)){
                    ArrayList<Card> current = new ArrayList<>();
                    for (int row = 0; row < cards.length; row++) {
                        for (int col = 0; col < cards[0].length; col++) {
                            current.add(cards[row][col]);
                        }
                    }

                    for (int row = 0; row < cards.length; row++) {
                        for (int col = 0; col < cards[0].length; col++) {
                            int cardNum = (int) (Math.random() * current.size());
                                    cards[row][col] = current.remove(cardNum);
                        }
                    }
                }

                //Hint
                if (button == 1 && hint.contains(p)){
                    noMovesLeft = d.checkForMoves(cards);
                    if (noMovesLeft == false) {
                        for (int row = 0; row < cards.length; row++) {
                            for (int col = 0; col < cards[0].length; col++) {
                                for (int i = 0; i < d.hint(cards).size(); i++) {
                                    if (cards[row][col].equals(d.hint(cards).get(i))){
                                        cards[row][col].flipHighlight();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}