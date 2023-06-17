package pt.isec.pa.tinypac.model.data;

import java.io.Serial;
import java.io.Serializable;

public class Top5Data implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String userName;
    private Integer score;

    Top5Data(String userName, Integer score) {
        this.userName = userName;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }
}
