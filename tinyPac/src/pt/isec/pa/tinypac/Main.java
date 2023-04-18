package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.ui.text.TextInterface;

public class Main {
    public static void main(String[] args) {
        /*IGameEngine gameEngine = new GameEngine();
        TestClient client = new TestClient();
        gameEngine.registerClient(client);
        gameEngine.start(500);
        gameEngine.waitForTheEnd();*/


        Context context = new Context();
        TextInterface ui = new TextInterface(context);
        ui.start();

    }
}