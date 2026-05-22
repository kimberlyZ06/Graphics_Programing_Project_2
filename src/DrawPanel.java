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

    }

    public void mousePressed(MouseEvent e) {

        Point p = e.getPoint();
        int button = e.getButton();

        Rectangle playAgain = new Rectangle(50, 300, 200, 50);

        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards.length; c++) {
                if (d.getDeck().size() != 0 && button == 1) {
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

                if (highlighted.size()== 2){
                    int firstVal = Integer.parseInt(highlighted.get(0).getValue());
                    int secVal = Integer.parseInt(highlighted.get(1).getValue());

                    if (firstVal + secVal == 11){
                        for (int row = 0; row < cards.length; row++) {
                            for (int col = 0; col < cards.length; col++) {
                                if (cards[row][col].getHighlight()){
                                    cards[row][col] = d.getRandomCard();
                                    cards[row][col].flipHighlight();
                                }
                            }
                        }
                    }
                }


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



    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}