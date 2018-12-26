package com.mbronshteyn.data.cards.keys;

import com.mbronshteyn.data.cards.Card;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@IdClass(HitId.class)
public class HitId implements Serializable {

    public HitId() {
    }

    private Integer sequence;

    private Card card;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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
        HitId hitId = (HitId) o;
        return sequence == hitId.sequence &&
                Objects.equals(card, hitId.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, card);
    }
}
