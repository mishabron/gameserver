package com.mbronshteyn.gameserver.dto.game;

import java.math.BigDecimal;
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
    private double payout1;
    private double payout2;
    private double payout3;
    private String email;
    boolean freeAttempt;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public boolean isFreeAttempt() {
        return freeAttempt;
    }

    public void setFreeAttempt(boolean freeAttempt) {
        this.freeAttempt = freeAttempt;
    }

    public double getPayout1() {
        return payout1;
    }

    public void setPayout1(double payout1) {
        this.payout1 = payout1;
    }

    public double getPayout2() {
        return payout2;
    }

    public void setPayout2(double payout2) {
        this.payout2 = payout2;
    }

    public double getPayout3() {
        return payout3;
    }

    public void setPayout3(double payout3) {
        this.payout3 = payout3;
    }
}
