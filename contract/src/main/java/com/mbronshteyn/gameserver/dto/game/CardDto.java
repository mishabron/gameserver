package com.mbronshteyn.gameserver.dto.game;

import java.util.ArrayList;
import java.util.List;

public class CardDto {

    Long cardNumber;
    boolean active;
    boolean played;
    boolean paid;
    int numberOfHits;
    List<HitDto> hits;
    String game;
    double balance;
    boolean freeGame;
    Bonus bonusPin;

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(int numberOfHits) {
        this.numberOfHits = numberOfHits;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public List<HitDto> getHits() {
        return hits;
    }

    public List<HitDto> getNonBonusHits() {
        List<HitDto> nonBonusHits = new ArrayList<HitDto>();
        for(HitDto hit : hits){
            if(hit.getBonusHit() == null){
                nonBonusHits.add(hit);
            }
        }
        return nonBonusHits;
    }

    public void setHits(List<HitDto> hits) {
        this.hits = hits;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isFreeGame() {
        return freeGame;
    }

    public void setFreeGame(boolean freeGame) {
        this.freeGame = freeGame;
    }

    public Bonus getBonusPin() {
        return bonusPin;
    }

    public void setBonusPin(Bonus bonusPin) {
        this.bonusPin = bonusPin;
    }
}
