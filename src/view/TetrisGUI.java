/*
 * TCSS 305 - Assignment 6: Tetris
 * Spring 2017
 */
package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;

/**
 * The Tetris GUI.
 * @author Tyler Pitsch
 * @version 5/19/17
 *
 */
public class TetrisGUI extends JFrame implements Observer {
    /**
     * Dimension of the frame.
     */
    public static final Dimension FRAME_SIZE = new Dimension(500, 660);
    /**
     * Generated UID for the frame.
     */
    private static final long serialVersionUID = -8812298156338771138L;
    /**
     * Delay on the timer fires.
     */
    private static final int TIMER_DELAY = 1_000;
    /**
     * Increase the speed by this amount each level. Never drops below this amount. 
     */
    private static final int SPEED_INCREASE = 100;
    /**
     * player for the sounds.
     */
    private static final SoundPlayer SOUND_PLAYER = new SoundPlayer();
    /**
     * Main song file.
     */
    private static final String MAIN_SONG = ".\\Sounds\\PitschTetris2.0.wav";
    /**
     * The icon for the frame and the JOption panes.
     */
    private final ImageIcon myIcon;
    /**
     * The game board.
     */
    private final Timer myTimer;
    /**
     * The game board that has all the pieces.
     */
    private final Board myBoard;
    /**
     * The listener to move pieces.
     */
    private PieceMovemenet myMoveListener;
    /**
     * The Score.
     */
    private final Score myScore;
    /**
     * Controls what to do if the game is already paused or not if the button 
     * to pause is hit again.
     */
    private boolean myPaused;
    /**
     * Controls if the game is over or not.
     */
    private boolean myGameOver;
    
    /**
     * Constructs the GUI elements.
     */
    public TetrisGUI() {
        super("TCSS 305 Tetris");
        myIcon = new ImageIcon(".\\Images\\tetris-icon.png");
        frameOps();
        myMoveListener = new PieceMovemenet();
        myPaused = false;
        myGameOver = true;
        myMoveListener = new PieceMovemenet();
        
        myTimer = new Timer(TIMER_DELAY, new PieceListener());
        myBoard = new Board();
        myScore = new Score(myBoard);
        
        
    }
    /**
     * Starts the construction of the GUI.
     */
    public void start() {
        myMoveListener = new PieceMovemenet();
        setJMenuBar(createMenuBar());
        final JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.add(new GameGrid(myBoard));
        framePanel.add(new NextPieceWindow(myBoard, myScore));
        add(framePanel);
        myBoard.addObserver(this);
        myScore.addObserver(this);
        addKeyListener(new GameStateListener());
        myBoard.newGame();
    }
    /**
     * Sets the custom frame ops.
     */
    private void frameOps() {
        setSize(FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(myIcon.getImage());
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Creates a menu bar for options.
     * @return the menu to add to the frame.
     */
    private JMenuBar createMenuBar() {
        final JMenuBar menu = new JMenuBar();
        menu.add(makeFileOptions());
        return menu;
    }
    /**
     * Makes a menu for some useful game options.
     * @return the Menu Bar the goes on the frame.
     */
    private JMenuItem makeFileOptions() {
        final JMenu menu = new JMenu("File/About");
        final JMenuItem quit = new JMenuItem("Quit");
        final JMenuItem keys = new JMenuItem("Keys");
        final JMenuItem scoring = new JMenuItem("Score Info");
        final String controlMessage = " Move Left: Left Arrow and A \n Move Right: "
                        + "Right Arrow and D "
                        + "\n Move Down: Down Arrow and S \n Rotate: Up Arrow and W \n "
                        + "Drop: Space Bar \n New Game: N \n End Game: K \n Pause Game: P";
        
        final String scoreMessage = "Points Per Piece: 4 \n Points Per Line: \n 1 "
                        + "Line = 40 * Current Level \n 2 Lines = 100 * Current Level"
                        + "\n 3 Lines = 300 * Current Level \n 4 Lines = 1200 * Current Level";
        
        menu.add(scoring);
        menu.addSeparator();
        menu.add(keys);
        menu.addSeparator();
        menu.add(quit);
        
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                dispose();
            }
        });
        
        keys.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, controlMessage, "Key Bindings", 
                                              JOptionPane.INFORMATION_MESSAGE, 
                                              new ImageIcon(".\\Images\\keypad.png"));
            }
        });
        
        scoring.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, scoreMessage, "Scoring", 
                                              JOptionPane.INFORMATION_MESSAGE, myIcon);
            }
        });
        
        return menu;
    }
    @Override
    public void update(final java.util.Observable theObservable, final Object theObj) {
        if (theObj instanceof Boolean) {
            gameOver();
        } 
        if (theObj instanceof int[]) {
            final int[] level = (int[]) theObj;
            if (myTimer.getDelay() > SPEED_INCREASE) {
                myTimer.setDelay(TIMER_DELAY - (level[2] * SPEED_INCREASE));
            }
        }
        
    }
    /**
     * Handles the steps to end the game.
     */
    private void gameOver() {
        myGameOver = true;
        myTimer.stop();
        removeKeyListener(myMoveListener);
        SOUND_PLAYER.stopAll();
        SOUND_PLAYER.play(".\\Sounds\\GameOverMAN.wav");
        JOptionPane.showMessageDialog(this, "GAME OVER MAN, GAME OVER!" , "Tetris", 
                                      JOptionPane.INFORMATION_MESSAGE, null);
        myGameOver = true;
        
    }
    /**
     * Handles the actions to be taken if the game is paused.  Makes sure that
     * if the game has been ended they can't exploit the pause button to continue to play.
     */
    private void gamePause() {
        if (myPaused && !myGameOver) {
            myMoveListener = new PieceMovemenet();
            addKeyListener(myMoveListener);
            myTimer.start();
            myPaused = false;
            SOUND_PLAYER.loop(MAIN_SONG);
        } else if (!myPaused && !myGameOver) {
            SOUND_PLAYER.pause(MAIN_SONG);
            myTimer.stop();
            removeKeyListener(myMoveListener);
            myPaused = true;
        }
    }
    /**
     * Handles the actions to be taken for a new game.
     */
    private void newGame() {
        if (myGameOver) {
            myBoard.newGame();
            myScore.reset();
            myMoveListener = new PieceMovemenet();
            addKeyListener(myMoveListener);
            myTimer.start();
            myGameOver = false;
            SOUND_PLAYER.loop(MAIN_SONG);
        }
    }
    
    
    
    
    
    
    
    
//inner classes    
    
    
    /**
     * Listens for key strokes for major game state events.
     * @author Tyler Pitsch
     * @version 6/2/17
     *
     */
    private class GameStateListener implements KeyListener {

        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int keyCode = theEvent.getKeyCode();
            if (keyCode == KeyEvent.VK_K) {
                gameOver();
            } else if (keyCode == KeyEvent.VK_N) {
                newGame();
            } else if (keyCode == KeyEvent.VK_P) {
                gamePause();
            }
        }

        @Override
        public void keyReleased(final KeyEvent arg0) { }

        @Override
        public void keyTyped(final KeyEvent arg0) { }
        
    }
    
    /**
     * Private inner class to cause actions to take place on shapes when the timer
     * fires.
     * @author Tyler Pitsch
     * @version 5/26/17
     *
     */
    private class PieceListener implements ActionListener  {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.down();
        }
    }
    /**
     * Listens to key events to act on the piece.
     * @author Tyler Pitsch
     * @version 5/26/17
     *
     */
    private class PieceMovemenet implements KeyListener {
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int keyCode = theEvent.getKeyCode();
            if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                myBoard.left();
            } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                myBoard.down();
            } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                myBoard.right();
            } else if (keyCode == KeyEvent.VK_SPACE) {
                myBoard.drop();
            } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                myBoard.rotateCW();
            }
        }

        @Override
        public void keyReleased(final KeyEvent arg0) { }

        @Override
        public void keyTyped(final KeyEvent theEvent) { }
    }
    
}
