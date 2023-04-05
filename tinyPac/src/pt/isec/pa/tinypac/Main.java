package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.ui.text.TextInterface;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        TextInterface ui = new TextInterface(ui);
        ui.start();

    }
}