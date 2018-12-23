package com.mbronshteyn.gameserver.dto;

import javax.persistence.Column;
import java.math.BigDecimal;

public class BatchDto {

    private String gameName;
    private int cards;
    private BigDecimal cardPrice;
    private byte freeGame;
    private BigDecimal payout1;
    private BigDecimal payout2;
    private BigDecimal payout3;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }

    public BigDecimal getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(BigDecimal cardPrice) {
        this.cardPrice = cardPrice;
    }

    public byte getFreeGame() {
        return freeGame;
    }

    public void setFreeGame(byte freeGame) {
        this.freeGame = freeGame;
    }

    public BigDecimal getPayout1() {
        return payout1;
    }

    public void setPayout1(BigDecimal payout1) {
        this.payout1 = payout1;
    }

    public BigDecimal getPayout2() {
        return payout2;
    }

    public void setPayout2(BigDecimal payout2) {
        this.payout2 = payout2;
    }

    public BigDecimal getPayout3() {
        return payout3;
    }

    public void setPayout3(BigDecimal payout3) {
        this.payout3 = payout3;
    }
}
