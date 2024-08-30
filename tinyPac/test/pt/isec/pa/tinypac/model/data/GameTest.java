package pt.isec.pa.tinypac.model.data;

import org.junit.jupiter.api.*;
import pt.isec.pa.tinypac.utils.Obstacles;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test the Game Class")
class GameTest {
    Game game;
    @BeforeEach
    public void init(){
        this.game = new Game();
    }

    @DisplayName("Test the incFoodRemaining() method without a maze")
    @Test
    public void getMazeChar() {

        int row = 1;
        int col = 1;
        char expected = Obstacles.WALL.getSymbol();

        char c = game.getCharAtMazeElement(row,col);

        Assertions.assertEquals(' ' , c);
    }

    @DisplayName("Test the incFoodRemaining() method with elements")
    @Test
    public void getMazeCharWithElements() {
        int row = 0;
        int col = 28;
        char expected = Obstacles.WALL.getSymbol();

        GameManager gameManager = new GameManager();
        gameManager.loadMapLevel();
        game = gameManager.getGame();

        char c = game.getCharAtMazeElement(row,col);

        Assertions.assertEquals(expected , c);
    }

    @DisplayName("Test the getLevel() method")
    @Test
    public void getLevel() {

        int level = 2;

        game.setLevel(level);
        assertEquals(level,game.getLevel());
    }

    @DisplayName("Test the getLevel() method")
    @Test
    public void getScore() {

        int score = 256;

        game.setScore(256);
        assertEquals(score,game.getScore());
    }





   /* @DisplayName("Test the incFoodRemaining() method")
    @Test
    void incFoodRemaining() {
        int expectedValue = 0;

        game.incFoodRemaining();
        expectedValue++;

        Assertions.assertEquals(expectedValue, 1);
    }

    @Test
    void getFoodRemaining() {
        Assertions.assertEquals(0, game.getFoodRemaining());
    }

    @Test
    @Disabled
    void isAnyFoodRemaining() {
        Assertions.fail("Not Implemented yet");
    }

    @Test
    @Disabled
    void setFoodRemaining() {
        Assertions.fail("Not Implemented yet");
    }

    @Test
    @Disabled
    void changeDirection() {
        Assertions.fail("Not Implemented yet");
    }


    @AfterEach
    public void afterAll(){

    }*/
}