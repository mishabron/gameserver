package com.mbronshteyn.data.cards;

import com.mbronshteyn.data.cards.keys.HitId;
import com.mbronshteyn.gameserver.dto.game.Bonus;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the Hit database table.
 * 
 */
@Entity
@Table(name="Hit")
@NamedQuery(name="Hit.findAll", query="SELECT h FROM Hit h")
@EntityListeners(AuditingEntityListener.class)
@IdClass(HitId.class)
public class Hit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="Sequence", nullable=false)
    private int sequence;

	//bi-directional many-to-one association to Play
	@Id
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "Play_no", referencedColumnName = "Play_no", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "Card_id", referencedColumnName = "Card_id", nullable = false, insertable = false, updatable = false)
	})
	private Play play;

	@Enumerated(EnumType.STRING)
	@Column(name="BonusHit")
	private Bonus bonusHit;

	@Column(name="FirstPlay")
	private boolean firstPlay;

	@Column(name="HtTime", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date hitTime;

	@Column(name="Number_1")
	private Integer number_1;

	@Column(name="Number_2")
	private Integer number_2;

	@Column(name="Number_3")
	private Integer number_3;

	@Column(name="Number_4")
	private Integer number_4;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Batch_id", referencedColumnName="id", nullable=false)
	private CardBatch batch;

	@Column(name="Request_No")
	private int requestNo;

	public Hit() {
	}

	public Bonus getBonusHit() {
		return this.bonusHit;
	}

	public void setBonusHit(Bonus bonusHit) {
		this.bonusHit = bonusHit;
	}

	public Date getHitTime() {
		return this.hitTime;
	}

	public void setHitTime(Date hitTime) {
		this.hitTime = hitTime;
	}

	public Integer getNumber_1() {
		return this.number_1;
	}

	public void setNumber_1(Integer number_1) {
		this.number_1 = number_1;
	}

	public Integer getNumber_2() {
		return this.number_2;
	}

	public void setNumber_2(Integer number_2) {
		this.number_2 = number_2;
	}

	public Integer getNumber_3() {
		return this.number_3;
	}

	public void setNumber_3(Integer number_3) {
		this.number_3 = number_3;
	}

	public Integer getNumber_4() {
		return this.number_4;
	}

	public void setNumber_4(Integer number_4) {
		this.number_4 = number_4;
	}

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}

	public CardBatch getBatch() {
		return batch;
	}

	public void setBatch(CardBatch batch) {
		this.batch = batch;
	}

	public boolean isFirstPlay() {
		return firstPlay;
	}

	public void setFirstPlay(boolean firstPlay) {
		this.firstPlay = firstPlay;
	}

	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}

	public int getRequestNo() {
		return requestNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hit hit = (Hit) o;
		return sequence == hit.sequence &&
				bonusHit == hit.bonusHit &&
				Objects.equals(play, hit.play) &&
				Objects.equals(hitTime, hit.hitTime) &&
				Objects.equals(number_1, hit.number_1) &&
				Objects.equals(number_2, hit.number_2) &&
				Objects.equals(number_3, hit.number_3) &&
				Objects.equals(number_4, hit.number_4);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sequence, bonusHit, hitTime, number_1, number_2, number_3, number_4);
	}
}