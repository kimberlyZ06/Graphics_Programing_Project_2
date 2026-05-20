import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private Deck deck;
    private Card currCard;
    private Card[][] onScreen = new Card[3][3];

    public DrawPanel() {
        deck = new Deck();
        for (int row = 0; row < onScreen.length; row++) {
            for (int col = 0; col < onScreen[0].length; col++) {
                currCard = deck.getRandomCard();
                onScreen[row][col] = currCard;
            }
        }
        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;

        for (int row = 0; row < onScreen.length; row++) {
            for (int col = 0; col < onScreen[0].length; col++) {
                g.drawImage(onScreen[row][col].getImage(), x + (100*col), y + (100*row), null);
            }
        }
        g.drawString("There are " + deck.getCards().size() + " cards left", 50, 300);
    }

    public void mousePressed(MouseEvent e) {
        if (deck.getCards().size() >= 9){
        for (int row = 0; row < onScreen.length; row++) {
            for (int col = 0; col < onScreen[0].length; col++) {
                currCard = deck.getRandomCard();
                onScreen[row][col] = currCard;
            }
        }
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}