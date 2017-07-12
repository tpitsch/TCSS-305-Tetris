/*
 * TCSS 305 - Assignment 6: Tetris
 * Spring 2017
 */
package view;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main Tetris kickoff. 
 * @author Tyler Pitsch
 * @version 5/19/17
 *
 */
public final class TetrisMain {
    /**
     * Prevents this class from being instantiated.
     */
    private TetrisMain() { }
    /**
     * Main method that starts the game.
     * @param theArgs command line arguments.
     */
    public static void main(final String[] theArgs) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
    
    
    
}
