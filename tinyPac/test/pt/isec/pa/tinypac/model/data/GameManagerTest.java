package pt.isec.pa.tinypac.model.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    GameManager gameManager;

    @BeforeEach
    void init(){
        this.gameManager = new GameManager();
    }


    @Test
    void loadLevelsWithNullFolder() {

        Boolean expected = false;
        String folder = null;

        Assertions.assertEquals(expected,gameManager.loadMapLevel(folder));
    }


    @Test
    void loadLevelsWithNotExistingFolder() {

        Boolean expected = false;
        String folder = "ola/testeErro";

        Assertions.assertEquals(expected,gameManager.loadMapLevel(folder));
    }

    @Test
    void loadLevelsExistingFolder() {
        Boolean expected = true;
        String folder = "files/levels/";

        Assertions.assertEquals(expected,gameManager.loadMapLevel(folder));
    }

    @Test
    void loadLevelsExistingFolderWithutLevels() {
        Boolean expected = false;
        String folder = "files/";
        Assertions.assertEquals(expected,gameManager.loadMapLevel(folder));
    }

}