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

    @Column(name="Pin", nullable=false)
    protected String pin;

    public AuxiliaryPinId() {
    }

    public AuxiliaryPinId(CardBatch batch, String pin) {
        this.batch = batch;
        this.pin = pin;
    }

    public CardBatch getBatch() {
        return batch;
    }

    public void setBatch(CardBatch batch) {
        this.batch = batch;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuxiliaryPinId that = (AuxiliaryPinId) o;
        return pin == that.pin &&
                Objects.equals(batch, that.batch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, pin);
    }
}
