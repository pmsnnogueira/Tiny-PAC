package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.ModelManager;
import utils.Direction;

import java.io.IOException;

public class GameLanternaUI implements IGameEngineEvolve {
    ModelManager modelManager;
    Screen screen;
    GameEngine gameEngine;

    public GameLanternaUI() throws IOException {

        screen = new DefaultTerminalFactory().createScreen();
        screen.setCursorPosition(null);


        this.gameEngine = new GameEngine();
        this.modelManager = new ModelManager(gameEngine);
        gameEngine.registerClient(this);
        //gameEngine.registerClient(modelManager.getGameManager());


        gameEngine.start(250);
        gameEngine.waitForTheEnd();

        showInitialMenu();
    }

    public void showInitialMenu(){




    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
            showGame();
            KeyStroke key = screen.pollInput();
            if(key != null){
                switch (key.getKeyType()){
                    case ArrowUp :{ modelManager.changeDirection(Direction.UP);

                        System.out.println("UP");
                        break;
                    }
                    case ArrowDown : {
                        modelManager.changeDirection(Direction.DOWN);
                        break;
                    }
                    case ArrowLeft :{
                        modelManager.changeDirection(Direction.LEFT);
                        break;
                    }
                    case ArrowRight :{
                        modelManager.changeDirection(Direction.RIGHT);
                        break;
                    }
                }

                if(key.getKeyType() == KeyType.Escape ||  (key.getKeyType() == KeyType.Character && key.getCharacter().equals('q'))){     //Por enquanto sair do jogo
                    gameEngine.stop();
                   //gameEngine.registerClient(this);
                    screen.close();
                }
            }
        } catch (IOException e) { }
    }


    private void showGame() throws IOException {

        char[][] env = modelManager.showMaze();
        if(env == null)
            return;
        screen.startScreen();
        screen.doResizeIfNecessary();
        for (int y = 0; y < env.length; y++) {
            for (int x = 0; x < env[0].length; x++) {
                TextColor tc = switch(env[y][x]) {
                    case 'x' -> TextColor.ANSI.BLUE;
                    case 'o' -> TextColor.ANSI.WHITE;
                    case 'F' -> TextColor.ANSI.CYAN;
                    case 'Y' -> TextColor.ANSI.WHITE;
                    case 'O' -> TextColor.ANSI.WHITE;
                    case 'W' -> TextColor.ANSI.YELLOW;
                    default -> TextColor.ANSI.BLACK;
                };
                TextColor bc = switch(env[y][x]) {
                    case '*' -> TextColor.ANSI.YELLOW;
                    case 'o' -> TextColor.ANSI.BLACK;
                    case 'F' -> TextColor.ANSI.BLACK;
                    case 'Y' -> TextColor.ANSI.WHITE;
                    case 'O' -> TextColor.ANSI.YELLOW;
                    //Ghosts
                    case 'P' -> TextColor.ANSI.BLUE;
                    case 'B' -> TextColor.ANSI.RED;
                    case 'W' -> TextColor.ANSI.WHITE;
                    default -> TextColor.ANSI.BLACK;
                };
                screen.setCharacter(x,y, TextCharacter.fromCharacter(env[y][x],tc,bc)[0]);
            }
        }
        screen.refresh();
    }
}

