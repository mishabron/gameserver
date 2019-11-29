package com.mbronshteyn.data.cards.keys;

import com.mbronshteyn.data.cards.CardBatch;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuxiliaryPinId implements Serializable {

    //bi-directional many-to-one association to CardBatch
    @ManyToOne
    @JoinColumn(name="Batch_id", referencedColumnName="id", nullable=false, insertable = false, updatable = false)
    protected CardBatch batch;

    @Column(name="Position", nullable=false)
    protected int pin;

    @Column(name="Sequence", nullable=false)
    private int sequence;

    public AuxiliaryPinId() {
    }

    public AuxiliaryPinId(CardBatch batch, int sequence, int pin) {
        this.batch = batch;
        this.sequence = sequence;
        this.pin = pin;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public CardBatch getBatch() {
        return batch;
    }

    public void setBatch(CardBatch batch) {
        this.batch = batch;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuxiliaryPinId that = (AuxiliaryPinId) o;
        return sequence == that.sequence &&
                Objects.equals(batch, that.batch) &&
                Objects.equals(pin, that.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, pin, sequence);
    }
}
