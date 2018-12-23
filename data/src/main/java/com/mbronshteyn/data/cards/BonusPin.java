package com.mbronshteyn.data.cards;

import com.mbronshteyn.data.cards.keys.BonusPinId;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the BonusPin database table.
 * 
 */
@Entity
@Table(name="BonusPin")
@NamedQuery(name="BonusPin.findAll", query="SELECT b FROM BonusPin b")
public class BonusPin implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BonusPinId id;

	@Column(name="Active")
	private boolean active;

	@Column(name="UpdateBy", length=10)
	private String updateBy;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="Used")
	private boolean used;

	public BonusPin() {
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public boolean getUsed() {
		return this.used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public BonusPinId getId() {
		return id;
	}

	public void setId(BonusPinId id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isUsed() {
		return used;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BonusPin bonusPin = (BonusPin) o;
		return active == bonusPin.active &&
				used == bonusPin.used &&
				Objects.equals(id, bonusPin.id) &&
				Objects.equals(updateBy, bonusPin.updateBy) &&
				Objects.equals(updateTime, bonusPin.updateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, active, updateBy, updateTime, used);
	}
}