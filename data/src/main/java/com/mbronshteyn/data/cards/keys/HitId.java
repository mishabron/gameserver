package com.mbronshteyn.data.cards.keys;

import com.mbronshteyn.data.cards.Play;

import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@IdClass(HitId.class)
public class HitId implements Serializable {

    public HitId() {
    }

    private Integer sequence;

    private Play play;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitId hitId = (HitId) o;
        return Objects.equals(sequence, hitId.sequence) &&
                Objects.equals(play, hitId.play);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, play);
    }
}
