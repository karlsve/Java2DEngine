package testing.randomcolors;

import java.awt.Color;

import javax.swing.SwingUtilities;

import engine.Engine;

public class RandomColorField {

    private Engine engine = null;

    private final String TITLE = "Random Colors";

    private final int COLUMNS = 80;
    private final int ROWS = 60;
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;
    private final Color SCREEN_COLOR = Color.LIGHT_GRAY;

    private final int UPDATE_RATE = 15;

    public RandomColorField() {
        engine = new Engine(TITLE, SCREEN_WIDTH, SCREEN_HEIGHT,
                COLUMNS, ROWS, false, UPDATE_RATE, SCREEN_COLOR);

        for(int i=0; i<COLUMNS; i++) {
            for(int j=0; j<ROWS; j++) {
                engine.addObject(new RandomColorObject(i, j, this.engine));
            }
        }
        engine.engineStart();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandomColorField();
            }
        });
    }

}
