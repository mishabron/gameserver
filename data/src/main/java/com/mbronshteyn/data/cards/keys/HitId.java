package com.mbronshteyn.data.cards.keys;

import com.mbronshteyn.data.cards.Card;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HitId implements Serializable {

    public HitId() {
    }

    @Column(name="Sequence", nullable=false)
    private int sequence;

    //bi-directional many-to-one association to Card
    @ManyToOne
    @JoinColumn(name="Card_id", referencedColumnName="id", nullable=false)
    private Card card;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
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
