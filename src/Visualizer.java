import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Thread.sleep;

/**
 * @author Jacob Roschen
 */

public class Visualizer extends JApplet {
    private Floor floor;

    /**
     * Sets up the visualizer to startPoint drawing
     *
     * @param f The floor that the visualizer should draw
     * @author Jacob Roschen
     */
    Visualizer(Floor f) {
        this.floor = f;

        JFrame frame = new JFrame("Visualizer");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.getContentPane().add("Center", this);
        frame.pack();
        frame.setSize(new Dimension(1200, 750));
        repaint();
        frame.setVisible(true);

        try {
            // Let the JApplet fully initialize before continuing
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the floor
     *
     * @param g
     * @author Jacob Roschen
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Output the labels
        g2.setColor(Color.BLACK);
        g2.drawString("Black: Belt", 10, 615);
        g2.drawString("Cyan: Charger", 10, 630);
        g2.drawString("Pink: Package", 10, 645);
        g2.drawString("Dark Grey: Picker", 10, 660);
        g2.drawString("Blue: Robot", 10, 675);
        g2.drawString("Yellow: Shelf", 10, 690);
        g2.drawString("Orange: Robot Lowered Shelf", 10, 705);
        g2.drawString("Red: Robot Raised Shelf", 10, 720);
        g2.setPaint(Color.gray);

        // Print the grid
        Floor.Item[][] grid = floor.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j]) {
                    case BELT:
                        g2.setColor(Color.BLACK);
                        break;
                    case CHARGER:
                        g2.setColor(Color.CYAN);
                        break;
                    case PACKAGE:
                        g2.setColor(Color.PINK);
                        break;
                    case PICKER:
                        g2.setColor(Color.DARK_GRAY);
                        break;
                    case ROBOT:
                        g2.setColor(Color.BLUE);
                        break;
                    case SHELF:
                        g2.setColor(Color.YELLOW);
                        break;
                    case ROBOTRAISEDSHELF:
                        g2.setColor(Color.RED);
                        break;
                    case ROBOTLOWEREDSHELF:
                        g2.setColor(Color.ORANGE);
                        break;
                    case EMPTY:
                        g2.setColor(Color.WHITE);
                        break;
                }

                Rectangle r = new Rectangle(i * 6, j * 6, 6, 6);
                g2.fill(r);
                g2.setColor(Color.GRAY);
                g2.draw(r);
            }
        }
    }
}
