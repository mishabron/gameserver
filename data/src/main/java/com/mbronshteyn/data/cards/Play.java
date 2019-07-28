package com.mbronshteyn.data.cards;

import com.mbronshteyn.data.cards.keys.PlayId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="Play")
@NamedQuery(name="Play.findAll", query="SELECT p FROM Play p")
@EntityListeners(AuditingEntityListener.class)
@IdClass(PlayId.class)
public class Play implements Serializable {

    @Id
    @Column(name="Play_no", nullable=false)
    private int playNumber;

    //bi-directional many-to-one association to Card
    @Id
    @ManyToOne
    @JoinColumn(name="Card_id", referencedColumnName="id", nullable=false, insertable = false, updatable = false)
    private Card card;

    @Column(name="WinPin")
    private String winPin;

    @Column(name="UpdateTime")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;

    @Column(name="UpdateBy", length=10)
    @CreatedBy
    private String createdBy;

    //bi-directional many-to-one association to Hit
    @OneToMany(mappedBy="play",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @OrderBy("Sequence ASC")
    private List<Hit> hits = new ArrayList<>();

    public int getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getWinPin() {
        return winPin;
    }

    public void setWinPin(String winPin) {
        this.winPin = winPin;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Play play = (Play) o;
        return playNumber == play.playNumber &&
                Objects.equals(card, play.card) &&
                Objects.equals(winPin, play.winPin) &&
                Objects.equals(createTime, play.createTime) &&
                Objects.equals(createdBy, play.createdBy) &&
                Objects.equals(hits, play.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playNumber, card, winPin, createTime, createdBy, hits);
    }
}
