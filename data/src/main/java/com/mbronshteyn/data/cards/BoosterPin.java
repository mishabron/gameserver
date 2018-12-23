package com.mbronshteyn.data.cards;

import com.mbronshteyn.data.cards.keys.BoosterPinId;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the BoosterPin database table.
 * 
 */
@Entity
@Table(name="BoosterPin")
@NamedQuery(name="BoosterPin.findAll", query="SELECT b FROM BoosterPin b")
public class BoosterPin implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BoosterPinId id;

	@Column(name="Active")
	private boolean active;

	@Column(name="UpdateBy", length=10)
	private String updateBy;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="Used")
	private boolean used;

	public BoosterPin() {
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

	public BoosterPinId getId() {
		return id;
	}

	public void setId(BoosterPinId id) {
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
		BoosterPin that = (BoosterPin) o;
		return active == that.active &&
				used == that.used &&
				Objects.equals(id, that.id) &&
				Objects.equals(updateBy, that.updateBy) &&
				Objects.equals(updateTime, that.updateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, active, updateBy, updateTime, used);
	}
}