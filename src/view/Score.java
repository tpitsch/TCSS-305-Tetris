package view;

import java.util.Observable;
import java.util.Observer;
import model.Board;
import model.TetrisPiece;

/**
 * Keeps track of score and levels.
 * @author Tyler Pitsch
 * @version 6/2/17
 *
 */
public class Score extends Observable implements Observer {
    /**
     * Lowest points possible.
     */
    private static final int MIN_POINTS = 40;
    /**
     *  Low amount of points.
     */
    private static final int LOW_POINTS = 100;
    /**
     * High amount of points.
     */
    private static final int HIGH_POINTS = 300;
    /**
     * Max amount of points.
     */
    private static  final int MAX_POINTS = 1200;
    /**
     * Number of lines to get the to the next level.
     */
    private static final int LINES_TO_LEVEL = 5;
    /**
     * Single piece points.
     */
    private static final int PIECE_POINTS = 4;
    /**
     * High amount of lines.
     */
    private static final int HIGH_LINES = 3;
    /**
     * Max lines.
     */
    private static final int MAX_LINES = 4;
    
    
    /**
     * Total points.
     */
    private int myTotalPoints;
    /**
     * Total lines cleared.
     */
    private int myTotalLines;
    /**
     * Current level.
     */
    private int myLevel;
    /**
     * the first piece to not score.
     */
    private boolean myFirstPiece;
    /**
     * Ticker for the level.
     */
    private int myTicker;
    /**
     * Constructor.
     * @param theBoard that we are using currently.
     */
    public Score(final Board theBoard) {
        super();
        theBoard.addObserver(this);
        myTotalPoints = 0;
        myTotalLines = 0;
        myLevel = 1;
        myTicker = 0;
        myFirstPiece = true;
    }
    /**
     * Gets information from the board.
     */
    @Override
    public void update(final Observable arg0, final Object theObj) {
        if (theObj instanceof Integer[]) {
            final Integer[] i = (Integer[]) theObj;
            if (i.length == 1) {
                myTotalPoints += MIN_POINTS * myLevel;
                myTotalLines++;
                tickerCount(i.length);
            } else if (i.length == 2) {
                myTotalPoints += LOW_POINTS * myLevel;
                myTotalLines += 2;
                tickerCount(i.length);
            } else if (i.length == HIGH_LINES) {
                myTotalPoints += HIGH_POINTS * myLevel;
                myTotalLines += HIGH_LINES;
                tickerCount(i.length);
            } else if (i.length == MAX_LINES) {
                myTotalPoints += MAX_POINTS * myLevel;
                myTotalLines += MAX_LINES;
                tickerCount(i.length);
            }
            setChanged();
            notifyObservers(changedData());
        } else if (theObj instanceof TetrisPiece) {
            if (myFirstPiece) {
                myFirstPiece = false;
            } else {
                myTotalPoints += PIECE_POINTS;
                setChanged();
                notifyObservers(changedData());
            }
        }
        

    }
    /**
     * Keeps the ticker under the total amount of allowed lines to level.
     * @param theNumber the total number of lines to add.
     */
    private void tickerCount(final int theNumber) {
        myTicker += theNumber;
        if (myTicker >= LINES_TO_LEVEL) {
            myLevel++;
            myTicker -= LINES_TO_LEVEL;
        }
    }
    /**
     * Compiles all the relevant data to give to listeners.
     * @return the data for the listeners.
     */
    private int[] changedData() {
        final int[] data = new int[MAX_LINES];
        data[0] = myTotalPoints;
        data[1] = myTotalLines;
        data[2] = myLevel;
        data[HIGH_LINES] = LINES_TO_LEVEL - myTicker;
        return data;
        
    }
    /**
     * Resets all the data when a new game starts.
     */
    public void reset() {
        myTotalPoints = 0;
        myTotalLines = 0;
        myLevel = 1;
        myTicker = 0;
        myFirstPiece = true;
        setChanged();
        notifyObservers(changedData());
        
    }
}
