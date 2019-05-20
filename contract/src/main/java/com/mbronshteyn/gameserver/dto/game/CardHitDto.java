package com.mbronshteyn.gameserver.dto.game;

public class CardHitDto {

    String deviceId;
    Long cardNumber;
    String game;
    Integer hit1;
    Integer hit2;
    Integer hit3;
    Integer hit4;
    boolean bonusHit;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getHit1() {
        return hit1;
    }

    public void setHit1(Integer hit1) {
        this.hit1 = hit1;
    }

    public Integer getHit2() {
        return hit2;
    }

    public void setHit2(Integer hit2) {
        this.hit2 = hit2;
    }

    public Integer getHit3() {
        return hit3;
    }

    public void setHit3(Integer hit3) {
        this.hit3 = hit3;
    }

    public Integer getHit4() {
        return hit4;
    }

    public void setHit4(Integer hit4) {
        this.hit4 = hit4;
    }

    public boolean isBonusHit() {
        return bonusHit;
    }

    public void setBonusHit(boolean bonusHit) {
        this.bonusHit = bonusHit;
    }
}
