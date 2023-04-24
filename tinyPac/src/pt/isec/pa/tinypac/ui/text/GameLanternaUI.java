package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.GameEngineState;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.ModelManager;
import utils.Obstacles;
import utils.PAInput;

import java.io.IOException;

public class GameLanternaUI implements IGameEngineEvolve {
    ModelManager modelManager;
    Screen screen;
    GameEngine gameEngine;

    public GameLanternaUI(GameManager gameManager) throws IOException {

        screen = new DefaultTerminalFactory().createScreen();
        screen.setCursorPosition(null);

        this.modelManager = new ModelManager(gameManager);

        this.gameEngine = new GameEngine();
        gameEngine.registerClient(this);
        gameEngine.registerClient(modelManager.getGameManager());


        gameEngine.start(500);
        gameEngine.waitForTheEnd();

        show();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
            show();
            KeyStroke key = screen.pollInput();
            if (( key != null &&
                            (key.getKeyType() == KeyType.Escape ||
                                    (key.getKeyType() == KeyType.Character &&
                                            key.getCharacter().equals('q'))))
            ){

                gameEngine.stop();
                gameEngine.registerClient(this);
                screen.close();
            }
        } catch (IOException e) { }
    }


    private void show() throws IOException {

        char[][] env = modelManager.showMaze();
        if(env == null)
            return;
        screen.startScreen();
        screen.doResizeIfNecessary();
        for (int y = 0; y < env.length; y++) {
            for (int x = 0; x < env[0].length; x++) {
                TextColor tc = switch(env[y][x]) {
                    case 'x' -> TextColor.ANSI.BLUE;
                    default -> TextColor.ANSI.BLACK;
                };
                TextColor bc = switch(env[y][x]) {
                    case '*' -> TextColor.ANSI.YELLOW;
                    //case 'o' -> TextColor.ANSI.WHITE;
                    case 'B' -> TextColor.ANSI.BLUE;
                    default -> TextColor.ANSI.BLACK;
                };
                screen.setCharacter(x,y, TextCharacter.fromCharacter(env[y][x],tc,bc)[0]);
            }
        }
        screen.refresh();
    }
}

