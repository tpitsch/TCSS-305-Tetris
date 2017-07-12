/*
 * TCSS 305 - Assignment 6: Tetris
 * Spring 2017
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Block;
import model.Board;
import model.Point;
import model.TetrisPiece;



/**
 * Window for the next piece to be displayed.
 * @author Tyler Pitsch
 * @version 5/26/17
 *
 */
public class NextPieceWindow extends JPanel implements Observer {
    /**
     * The position that this window originates from.
     */
    private static final Point WINDOW_LOC = new Point(325, 25);
    /**
     * The white window location.
     */
    private static final Point NEXT_PIECE_WINDOW = new Point(365, 20);
    /**
     * The Size of the next piece window.
     */
    private static final int WINDOW_SIZE = 150;
    /**
     * Size of each square.
     */
    private static final int SIZE = 30;
    /**
     * Generated UID for the JPanel.
     */
    private static final long serialVersionUID = -127278658451196049L;
    /**
     * Starting point for each next piece.
     */
    private static final Point CENTER_POINT = new Point(395, 100);
    /**
     * Starting lines to next level.
     */
    private static final int STATING_LINES = 5;
    /**
     * Change in the y direction that the score should be drawn.
     */
    private static final int Y_CHANGE = 16;
    /**
     * Score section stating point.
     */
    private static final Point SCORE_START = new Point(340, 250);
    /**
     * differential in y-axis and location of the remaining lines.
     */
    private static final int Y_DIFF = 3;
    
    /**
     * The next piece to be added to the board.
     */
    private TetrisPiece myPiece;
    /**
     * The list of info.
     */
    private int[] myScoreInfo;
    
    
    
    /**
     * Constructor.
     * @param theBoard that we are playing on.
     * @param theScore that the user currently has.
     */
    public NextPieceWindow(final Board theBoard, final Score theScore) {
        super();
        theBoard.addObserver(this);
        theScore.addObserver(this);
        //setBackground(Color.WHITE);
        myPiece = TetrisPiece.I;
        this.setLayout(new BorderLayout());
        myScoreInfo  = new int[] {0, 0, 1, STATING_LINES};
        
    }
    @Override
    public void update(final Observable theObservable, final Object theObj) {
        if (theObj instanceof TetrisPiece) {
            myPiece = (TetrisPiece) theObj;
            repaint();
        } else if (theObj instanceof int[]) {
            myScoreInfo = (int[]) theObj;
            repaint();
        } else {
            repaint();
        }
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                             RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.drawString("NEXT PIECE", NEXT_PIECE_WINDOW.x(), NEXT_PIECE_WINDOW.y());
        paintPieceFrame(g2d);
        if (myPiece.getBlock() == Block.O) {
            paintOPiece(g2d);
        } else if (myPiece.getBlock() == Block.I) {
            paintIPiece(g2d);
        } else if (myPiece.getBlock() == Block.J) {
            paintJPiece(g2d);
        } else if (myPiece.getBlock() == Block.L) {
            paintLPiece(g2d);
        } else if (myPiece.getBlock() == Block.Z) {
            paintZPiece(g2d);
        } else if (myPiece.getBlock() == Block.S) {
            paintSPiece(g2d);
        } else if (myPiece.getBlock() == Block.T) {
            paintTPiece(g2d);
        }
        final Integer points = (Integer) myScoreInfo[0];
        final String stringPoints = "Total Points: " + points.toString();
        theGraphics.drawString(stringPoints, SCORE_START.x(), SCORE_START.y());
        final Integer lines = (Integer) myScoreInfo[1];
        final String stringLines = "Total Lines: " + lines.toString();
        theGraphics.drawString(stringLines, SCORE_START.x(), SCORE_START.y() + Y_CHANGE);
        final Integer level = (Integer) myScoreInfo[2];
        final String stringLevel = "Level: " + level.toString();
        theGraphics.drawString(stringLevel, SCORE_START.x(), SCORE_START.y() + Y_CHANGE * 2);
        final Integer nextLevel = (Integer) myScoreInfo[Y_DIFF];
        final String stringNextLevel = "Lines to next level: " + nextLevel.toString();
        theGraphics.drawString(stringNextLevel, SCORE_START.x(), SCORE_START.y() 
                               + Y_CHANGE * Y_DIFF);
        
        
        
    }
    
    /**
     * Sets the background of the next piece window.
     * @param theGraphics to draw to.
     */
    private void paintPieceFrame(final Graphics theGraphics) {
        theGraphics.setColor(Color.WHITE);
        theGraphics.fillRect(WINDOW_LOC.x(), WINDOW_LOC.y(), WINDOW_SIZE, WINDOW_SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(WINDOW_LOC.x(), WINDOW_LOC.y(), WINDOW_SIZE, WINDOW_SIZE);
    }
    /**
     * Draws the O shape and its color.
     * @param theGraphics to draw to.
     */
    private void paintOPiece(final Graphics theGraphics) {
        theGraphics.setColor(Color.YELLOW);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        
    }
    /**
     * Draws the I shape and its color.
     * @param theGraphics to draw on.
     */
    private void paintIPiece(final Graphics theGraphics) {
        theGraphics.setColor(Color.CYAN);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE * 2, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() , CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE * 2, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() , CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
    }
    /**
     * Draws the J shape and its color.
     * @param theGraphics to draw on.
     */
    private void paintJPiece(final Graphics theGraphics) {
        theGraphics.setColor(Color.BLUE);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
        
        
    }
    /**
     * Draws the L shape and its color.
     * @param theGraphics that we are drawing on.
     */
    private void paintLPiece(final Graphics theGraphics) {
        theGraphics.setColor(Color.ORANGE);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
    }
    /**
     * Draw the Z shape and its color and outline.
     * @param theGraphics that we are drawing on.
     */
    private void paintZPiece(final Graphics theGraphics) {
        theGraphics.setColor(Color.RED);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
    }
    /**
     * Draws the s shape with its color and outline.
     * @param theGraphics that we are drawing on.
     */
    private void paintSPiece(final Graphics theGraphics) {
        theGraphics.setColor(Color.GREEN);
        theGraphics.fillRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
    }
    /**
     * Draws the T shape with its color and outline.
     * @param theGraphics that we are drawing on.
     */
    private void paintTPiece(final Graphics theGraphics) {
        theGraphics.setColor(Color.MAGENTA.darker().darker());
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.fillRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.setColor(Color.BLACK);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y() - SIZE, SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() - SIZE, CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x(), CENTER_POINT.y(), SIZE, SIZE);
        theGraphics.drawRect(CENTER_POINT.x() + SIZE, CENTER_POINT.y(), SIZE, SIZE);
    }

}
