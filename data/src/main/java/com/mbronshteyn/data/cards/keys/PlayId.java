package com.mbronshteyn.data.cards.keys;

import com.mbronshteyn.data.cards.Card;

import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@IdClass(PlayId.class)
public class PlayId implements Serializable {

    public PlayId() {
    }

    private Integer playNumber;

    private Card card;

    public Integer getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(Integer playNumber) {
        this.playNumber = playNumber;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayId playId = (PlayId) o;
        return Objects.equals(playNumber, playId.playNumber) &&
                Objects.equals(card, playId.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playNumber, card);
    }
}
