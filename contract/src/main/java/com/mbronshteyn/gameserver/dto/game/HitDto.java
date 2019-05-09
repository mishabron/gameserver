package com.mbronshteyn.gameserver.dto.game;

public class HitDto {

    int sequence;
    PinNumber number_1;
    PinNumber number_2;
    PinNumber number_3;
    PinNumber number_4;
    boolean bonusHit;

    public int getSequence() {
        return sequence;
}

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isBonusHit() {
        return bonusHit;
    }

    public void setBonusHit(boolean bonusHit) {
        this.bonusHit = bonusHit;
    }

    public PinNumber getNumber_1() {
        return number_1;
    }

    public void setNumber_1(PinNumber number_1) {
        this.number_1 = number_1;
    }

    public PinNumber getNumber_2() {
        return number_2;
    }

    public void setNumber_2(PinNumber number_2) {
        this.number_2 = number_2;
    }

    public PinNumber getNumber_3() {
        return number_3;
    }

    public void setNumber_3(PinNumber number_3) {
        this.number_3 = number_3;
    }

    public PinNumber getNumber_4() {
        return number_4;
    }

    public void setNumber_4(PinNumber number_4) {
        this.number_4 = number_4;
    }
}
