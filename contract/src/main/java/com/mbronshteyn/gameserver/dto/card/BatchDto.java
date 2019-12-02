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
    private int numberOfBonusPins1;
    private int numberOfBonusPins2;
    private int numberOfBonusPins3;
    private int numberOfSuperPins1;
    private int numberOfSuperPins2;
    private int numberOfSuperPins3;

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

    public int getNumberOfBonusPins1() {
        return numberOfBonusPins1;
    }

    public void setNumberOfBonusPins1(int numberOfBonusPins1) {
        this.numberOfBonusPins1 = numberOfBonusPins1;
    }

    public int getNumberOfSuperPins1() {
        return numberOfSuperPins1;
    }

    public void setNumberOfSuperPins1(int numberOfSuperPins1) {
        this.numberOfSuperPins1 = numberOfSuperPins1;
    }

    public int getNumberOfBonusPins2() {
        return numberOfBonusPins2;
    }

    public void setNumberOfBonusPins2(int numberOfBonusPins2) {
        this.numberOfBonusPins2 = numberOfBonusPins2;
    }

    public int getNumberOfBonusPins3() {
        return numberOfBonusPins3;
    }

    public void setNumberOfBonusPins3(int numberOfBonusPins3) {
        this.numberOfBonusPins3 = numberOfBonusPins3;
    }

    public int getNumberOfSuperPins2() {
        return numberOfSuperPins2;
    }

    public void setNumberOfSuperPins2(int numberOfSuperPins2) {
        this.numberOfSuperPins2 = numberOfSuperPins2;
    }

    public int getNumberOfSuperPins3() {
        return numberOfSuperPins3;
    }

    public void setNumberOfSuperPins3(int numberOfSuperPins3) {
        this.numberOfSuperPins3 = numberOfSuperPins3;
    }
}
