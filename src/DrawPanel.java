import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;

class DrawPanel extends JPanel implements MouseListener {

    private Deck d;
    private Card[][] cards;

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
    }

    public void mousePressed(MouseEvent e) {

        Point p = e.getPoint();
        int button = e.getButton();

        Rectangle playAgain = new Rectangle(50, 300, 200, 50);
        Rectangle replaceCard = new Rectangle(300,50,100,50);

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
                        }
                    }
                }
            }
        }

        //winning
        if (cards.length == 0){
            getGraphics().drawString("There are " + cards.length + " cards left!", 300, 250);
        }

        //losing
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
            getGraphics().drawString("There are no valid moves! :(", 300, 250);
        }



    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}