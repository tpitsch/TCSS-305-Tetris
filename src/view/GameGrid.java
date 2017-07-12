/*
 * TCSS 305 - Assignment 6: Tetris
 * Spring 2017
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Block;
import model.Board;

/**
 * Panel that all the pieces get drawn to.
 * @author Tyler Pitsch
 * @version 5/26/17
 *
 */
public class GameGrid extends JPanel implements Observer {
    /**
     * The dimensions of the window.
     */
    public static final Dimension PANEL_SIZE = new Dimension(300, 600);
    /**
     * The size that each rectangle that represents a box will be.
     */
    public static final int SHAPE_SIZE = 30;
    /**
     * The total number of rows.
     */
    public static final int ROWS = 20;
    /**
     * The total number of Columns.
     */
    private static final int COLUMNS = 10;
    /**
     * Generated UID.
     */
    private static final long serialVersionUID = 5674640605162957199L;
    /**
     * List of all our pieces to be drawn.
     */
    private List<Block[]> myBlocks;
    
    
    /**
     * Constructor.
     * @param theBoard that has all the pieces.
     */
    public GameGrid(final Board theBoard) {
        super();
        theBoard.addObserver(this);
        myBlocks = new ArrayList<Block[]>();
        setSize(PANEL_SIZE);
        this.setLayout(new BorderLayout());
    }
    /**
     * Paints all the shapes to the window.
     * @param theGraphics that draws the objects.
     */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                             RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                g2d.setColor(checkColor(myBlocks.get(ROWS - 1 - j)[i]));
                g2d.fillRect(SHAPE_SIZE * i, SHAPE_SIZE * j, 
                                     SHAPE_SIZE, SHAPE_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(SHAPE_SIZE * i, SHAPE_SIZE * j, 
                                     SHAPE_SIZE, SHAPE_SIZE);
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof List<?>) {
            myBlocks = (List<Block[]>) theObject;
            repaint();
        }
    }
    /**
     * Checks which color the given type of Block should be.
     * @param theBlock that we are checking to determine what color it wants to be.
     * @return the color the block should now be.
     */
    private Color checkColor(final Block theBlock) {
        Color colorChange = Color.BLACK;
        if (theBlock == Block.O) {
            colorChange = Color.YELLOW;
        } else if (theBlock == Block.I) {
            colorChange = Color.CYAN;
        } else if (theBlock == Block.J) {
            colorChange = Color.BLUE;
        } else if (theBlock == Block.L) {
            colorChange = Color.ORANGE;
        } else if (theBlock == Block.S) {
            colorChange = Color.GREEN;
        } else if (theBlock == Block.Z) {
            colorChange = Color.RED;
        } else if (theBlock == Block.T) {
            colorChange = Color.MAGENTA.darker().darker();
        } else if (theBlock == null) {
            colorChange = Color.WHITE;
        }
        return colorChange;
    }
}
