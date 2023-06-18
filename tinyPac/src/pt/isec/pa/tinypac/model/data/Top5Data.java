package pt.isec.pa.tinypac.model.data;

import java.io.Serial;
import java.io.Serializable;

/**
 * The top5Data class represents the data for a player score in the top5 list.
 * Implements the Serializable interface to allow object serialization.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Top5Data implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String userName;
    private Integer score;

    /**
     * Constructs a Top5Data object with the specified username and score.
     * @param userName The username.
     * @param score The score.
     */
    Top5Data(String userName, Integer score) {
        this.userName = userName;
        this.score = score;
    }

    /**
     * Retrieves the score
     * @return the score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Retrieves the username
     * @return the username
     */
    public String getUserName() {
        return userName;
    }
}
