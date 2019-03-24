package com.mbronshteyn.gameserver.dto.game;

public class HitDto {

    int sequence;
    Integer number_1;
    Integer number_2;
    Integer number_3;
    Integer number_4;
    boolean bonusHit;

    public int getSequence() {
        return sequence;
}

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Integer getNumber_1() {
        return number_1;
    }

    public void setNumber_1(Integer number_1) {
        this.number_1 = number_1;
    }

    public Integer getNumber_2() {
        return number_2;
    }

    public void setNumber_2(Integer number_2) {
        this.number_2 = number_2;
    }

    public Integer getNumber_3() {
        return number_3;
    }

    public void setNumber_3(Integer number_3) {
        this.number_3 = number_3;
    }

    public Integer getNumber_4() {
        return number_4;
    }

    public void setNumber_4(Integer number_4) {
        this.number_4 = number_4;
    }

    public boolean isBonusHit() {
        return bonusHit;
    }

    public void setBonusHit(boolean bonusHit) {
        this.bonusHit = bonusHit;
    }
}
