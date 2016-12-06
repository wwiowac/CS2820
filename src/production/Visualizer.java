package production;

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
    private int time = 0;

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
        frame.setSize(new Dimension(1200, 700));
        repaint();
        frame.setVisible(true);

        try {
            // Let the JApplet fully initialize before continuing
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void repaint(int time) {
        this.time = time;
        super.repaint();
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
        g2.drawString("Light Grey: Picker", 10, 660);
        g2.drawString("Blue: Robot", 10, 675);

        g2.drawString("Yellow: Shelf", 150, 615);
        g2.drawString("Orange: Robot Lowered Shelf", 150, 630);
        g2.drawString("Red: Robot Raised Shelf", 150, 645);
        g2.drawString("Pink: Home", 150, 660);
        g2.drawString("Magenta: Home with Robot", 150, 675);

        g2.drawString("Dark Grey: Bin on Belt", 400, 615);

        g2.clearRect(1000, 610, 100, 20);
        g2.drawString("Time: "+ this.time, 1010, 625);
        g2.setPaint(Color.gray);

        // Print the grid
        Cell[][] grid = floor.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j].type) {
                    case BELT:
                        g2.setColor(Color.BLACK);
                        break;
                    case BINONBELT:
                        g2.setColor(Color.DARK_GRAY);
                    case CHARGER:
                        g2.setColor(Color.CYAN);
                        break;
                    case PACKAGE:
                        g2.setColor(Color.PINK);
                        break;
                    case PICKER:
                        g2.setColor(Color.lightGray);
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
                    case HOME:
                        g2.setColor(Color.PINK);
                        break;
                    case ROBOTHOME:
                        g2.setColor(Color.MAGENTA);
                        break;
                }

                Rectangle r = new Rectangle(i * 20, j * 20, 20, 20);
                g2.fill(r);
                g2.setColor(Color.GRAY);
                g2.draw(r);
            }
        }
    }
}
