package sample;

import java.util.Comparator;

public class PlayerInfo {
    private String name;
    private int score;

    public PlayerInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public static Comparator<PlayerInfo> scoreComp = (playerInfo, t1) -> {
        double diff = playerInfo.getScore() - t1.getScore();
        if (diff < 0)
            return 1;
        else if (diff == 0)
            return 0;
        else
            return -1;
    };

    public String toString() {
        return name + "   " + score;
    }

}
