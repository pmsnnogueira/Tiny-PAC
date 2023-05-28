package pt.isec.pa.tinypac.ui.guiLanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.IOException;


class TerminalFactory extends DefaultTerminalFactory{

    TerminalFactory(){
        super();
        terminalSize();
        changeTitle();
    }

    private void terminalSize(){
        TerminalSize terminalSize = new TerminalSize(40,35);
        this.setInitialTerminalSize(terminalSize);
    }

    private void changeTitle(){
        setTerminalEmulatorTitle("Tiny - Pac");
    }

    @Override
    public Terminal createTerminal() throws IOException {
        return super.createTerminal();
    }
}



public class GameLanternaUI implements IGameEngineEvolve {
    ModelManager modelManager;
    Screen screen;
    Terminal terminal;
    GameEngine gameEngine;

    private static final Integer GAME_ENGINE_TIME = 250;

    public GameLanternaUI() throws IOException {

        terminal = new TerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);

        terminal.setCursorVisible(false);

        gameMenu();

       // showMenu();
    }

    private void printMenu(){
        try{
            terminal.clearScreen();
            terminal.setCursorPosition(1,1);
            terminal.putString("===== Tiny-PAC =======");
            terminal.setCursorPosition(1,2);
            terminal.putString("|| 1 - Play Game    ||");
            terminal.setCursorPosition(1,3);
            terminal.putString("|| 2 - Top 5        ||");
            terminal.setCursorPosition(1,4);
            terminal.putString("|| 3 - Exit         ||");
            terminal.setCursorPosition(1,5);
            terminal.putString("======================");
            terminal.flush();
        }catch (IOException e){}
    }

    public void showMenu() throws IOException {
        printMenu();
        KeyStroke key = screen.readInput();
        if(key.getKeyType() == KeyType.Character){
            char c = key.getCharacter();
            if(c == '1'){
                gameMenu();
            }
            if(c == '2'){
                showTop();
            }
            if(c == '3'){
                terminal.close();
            }
        }
    }

    private void showTop(){
        printTop();
        try {
            KeyStroke key = screen.readInput();
            if(key.getKeyType() == KeyType.Character){
                char c = key.getCharacter();
                if(c == '1'){
                    showMenu();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void printTop(){
        try{
            terminal.clearScreen();
            terminal.setCursorPosition(1,1);
            terminal.putString("======== Tiny-PAC TOP5 =======");
            terminal.setCursorPosition(1,2);
            terminal.putString("|| POINTS - ....            ||");
            terminal.setCursorPosition(1,3);
            terminal.putString("|| POINTS - ....            ||");
            terminal.setCursorPosition(1,4);
            terminal.putString("|| POINTS -                 ||");
            terminal.setCursorPosition(1,5);
            terminal.putString("|| POINTS -                 ||");
            terminal.setCursorPosition(1,6);
            terminal.putString("|| POINTS -                 ||");
            terminal.setCursorPosition(1,7);
            terminal.putString("|| 1 - Back to Menu         ||");
            terminal.setCursorPosition(1,8);
            terminal.putString("==============================");
            terminal.flush();
        }catch (IOException e){}
    }


    private void gameMenu(){

        this.gameEngine = new GameEngine();
        this.modelManager = new ModelManager(gameEngine);
        gameEngine.registerClient(this);
        gameEngine.start(GAME_ENGINE_TIME);
        gameEngine.waitForTheEnd();
    }

    private void showPauseMenu() {
        try{
            terminal.clearScreen();
            terminal.resetColorAndSGR();
            terminal.setCursorPosition(1,1);
            terminal.putString("======== Tiny-PAC Pause =======");
            terminal.setCursorPosition(1,2);
            terminal.putString("|| 1 - Save and Exit           ||");
            terminal.setCursorPosition(1,3);
            terminal.putString("|| 2 - Exit without saving     ||");
            terminal.setCursorPosition(1,4);
            terminal.putString("|| 3 - Back to Game            ||");
            terminal.setCursorPosition(1,5);
            terminal.putString("===============================");
            terminal.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
            switch (modelManager.getState()){
                case WAIT_FOR_DIRECTIONS, LOCKED_GHOSTS, GAME, GHOST_VULNERABLE:{
                    showGame();
                    break;
                }
                case PAUSE :{
                    showPauseMenu();
                    break;
                }
            }
            //showGame();
            KeyStroke key = screen.pollInput();
            if(key != null){
                switch (modelManager.getState()){
                    case PAUSE: {
                        if(key.getKeyType() == KeyType.Character && key.getCharacter().equals('3')){
                            modelManager.resume();
                            terminal.clearScreen();
                            break;
                        }
                    }
                    default:{
                        switch (key.getKeyType()){
                            case Escape:{
                                modelManager.pause();
                                break;
                            }
                            case ArrowUp :{
                                modelManager.changeDirection(Direction.UP);
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
                    }
                }
                if(key.getKeyType() == KeyType.Character && key.getCharacter().equals('q')){     //Por enquanto sair do jogo
                    gameEngine.stop();
                   //gameEngine.registerClient(this);
                    screen.close();
                }
            }
        } catch (IOException e) { }
    }


    private void showGame() throws IOException {

        char[][] env = modelManager.showMaze();
        String information = modelManager.showGameInfo();
        if(env == null)
            return;
        screen.startScreen();
        screen.doResizeIfNecessary();
        terminal.resetColorAndSGR();        //tentar mudar isto para ser tudo pelo terminal
        terminal.putString(information);
        terminal.flush();
        terminal.setCursorVisible(false);
        terminal.setCursorPosition(0,2);
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
                screen.setCharacter(x,y + 1, TextCharacter.fromCharacter(env[y][x],tc,bc)[0]);
            }
        }
        screen.refresh();
    }
}

