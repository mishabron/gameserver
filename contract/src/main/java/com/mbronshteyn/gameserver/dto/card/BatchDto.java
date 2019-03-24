package com.mbronshteyn.gameserver.dto.card;

import java.math.BigDecimal;

public class BatchDto {

    private String gameName;
    private int numberOfCards;
    private BigDecimal cardPrice;
    private byte freeGame;
    private BigDecimal payout1;
    private BigDecimal payout2;
    private BigDecimal payout3;
    private int numberOfBonusPins;
    private int numberOfBooserPins;
    private int numberOfSuperPins;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
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

    public int getNumberOfBonusPins() {
        return numberOfBonusPins;
    }

    public void setNumberOfBonusPins(int numberOfBonusPins) {
        this.numberOfBonusPins = numberOfBonusPins;
    }

    public int getNumberOfBooserPins() {
        return numberOfBooserPins;
    }

    public void setNumberOfBooserPins(int numberOfBooserPins) {
        this.numberOfBooserPins = numberOfBooserPins;
    }

    public int getNumberOfSuperPins() {
        return numberOfSuperPins;
    }

    public void setNumberOfSuperPins(int numberOfSuperPins) {
        this.numberOfSuperPins = numberOfSuperPins;
    }
}
