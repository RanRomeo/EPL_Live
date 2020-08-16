package table.football.soccer.livescore.epl.epllive.model;

import android.support.annotation.NonNull;

/**
 * Created by Ran on 20-Aug-18.
 */

public class player_model  implements Comparable<player_model>{


    String  playerName;
    String  playerId;
    String  playerNo;
    String  playerPosition;
    String  playerCountry;
    Boolean captain;
    int     playerType;
    String  matchPostion;
    String  playerImageLink;

    public player_model() {
    }

    //player id is playerLink
    public player_model(String playerName, String playerId, String playerNo, String playerPosition, String playerCountry, Boolean captain, int playerType, String matchPostion, String playerImageLink) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.playerNo = playerNo;
        this.playerPosition = playerPosition;
        this.playerCountry = playerCountry;
        this.captain = captain;
        this.playerType = playerType;
        this.matchPostion = matchPostion;
        this.playerImageLink = playerImageLink;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(String playerNo) {
        this.playerNo = playerNo;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getPlayerCountry() {
        return playerCountry;
    }

    public void setPlayerCountry(String playerCountry) {
        this.playerCountry = playerCountry;
    }

    public Boolean getCaptain() {
        return captain;
    }

    public void setCaptain(Boolean captain) {
        this.captain = captain;
    }

    public int getPlayerType() {
        return playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public String getMatchPostion() {
        return matchPostion;
    }

    public void setMatchPostion(String matchPostion) {
        this.matchPostion = matchPostion;
    }

    public String getPlayerImageLink() {
        return playerImageLink;
    }

    public void setPlayerImageLink(String playerImageLink) {
        this.playerImageLink = playerImageLink;
    }

    @Override
    public int compareTo(@NonNull player_model compare_number) {
        int compareQuantity = ((player_model) compare_number).getPlayerType();
        return compareQuantity - this.playerType;
    }
}


