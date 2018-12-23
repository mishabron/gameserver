package com.mbronshteyn.data.cards.keys;

import com.mbronshteyn.data.cards.Batch;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SuperPinId implements Serializable {

    //bi-directional many-to-one association to Batch
    @ManyToOne
    @JoinColumn(name="Batch_id", referencedColumnName="id", nullable=false)
    private Batch batch;

    @Column(name="Pin", nullable=false)
    private int pin;

    public SuperPinId() {
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
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
        SuperPinId that = (SuperPinId) o;
        return pin == that.pin &&
                Objects.equals(batch, that.batch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, pin);
    }
}
