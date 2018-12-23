package com.mbronshteyn.data.cards;

import com.mbronshteyn.data.cards.keys.HitId;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the Hit database table.
 * 
 */
@Entity
@Table(name="Hit")
@NamedQuery(name="Hit.findAll", query="SELECT h FROM Hit h")
public class Hit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HitId id;

	@Column(name="BonusHit")
	private boolean bonusHit;

	@Column(name="HtTime", nullable=false)
	private Timestamp htTime;

	@Column(name="Number_1")
	private int number_1;

	@Column(name="Number_2")
	private int number_2;

	@Column(name="Number_3")
	private int number_3;

	@Column(name="Number_4")
	private int number_4;

	public Hit() {
	}

	public boolean getBonusHit() {
		return this.bonusHit;
	}

	public void setBonusHit(boolean bonusHit) {
		this.bonusHit = bonusHit;
	}

	public Timestamp getHtTime() {
		return this.htTime;
	}

	public void setHtTime(Timestamp htTime) {
		this.htTime = htTime;
	}

	public int getNumber_1() {
		return this.number_1;
	}

	public void setNumber_1(int number_1) {
		this.number_1 = number_1;
	}

	public int getNumber_2() {
		return this.number_2;
	}

	public void setNumber_2(int number_2) {
		this.number_2 = number_2;
	}

	public int getNumber_3() {
		return this.number_3;
	}

	public void setNumber_3(int number_3) {
		this.number_3 = number_3;
	}

	public int getNumber_4() {
		return this.number_4;
	}

	public void setNumber_4(int number_4) {
		this.number_4 = number_4;
	}

	public HitId getId() {
		return id;
	}

	public void setId(HitId id) {
		this.id = id;
	}

	public boolean isBonusHit() {
		return bonusHit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hit hit = (Hit) o;
		return bonusHit == hit.bonusHit &&
				number_1 == hit.number_1 &&
				number_2 == hit.number_2 &&
				number_3 == hit.number_3 &&
				number_4 == hit.number_4 &&
				Objects.equals(id, hit.id) &&
				Objects.equals(htTime, hit.htTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, bonusHit, htTime, number_1, number_2, number_3, number_4);
	}
}