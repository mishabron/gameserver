package com.mbronshteyn.data.cards;

import com.mbronshteyn.data.cards.keys.BonusPinId;
import com.mbronshteyn.data.cards.keys.SuperPinId;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * The persistent class for the SuperPin database table.
 * 
 */
@Entity
@Table(name="SuperPin")
@NamedQuery(name="SuperPin.findAll", query="SELECT s FROM SuperPin s")
public class SuperPin implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SuperPinId id;

	@Column(name="Active")
	private boolean active;

	@Column(name="UpdateBy", length=10)
	private String updateBy;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="Used")
	private boolean used;

	public SuperPin() {
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

	public SuperPinId getId() {
		return id;
	}

	public void setId(SuperPinId id) {
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
		SuperPin superPin = (SuperPin) o;
		return active == superPin.active &&
				used == superPin.used &&
				Objects.equals(id, superPin.id) &&
				Objects.equals(updateBy, superPin.updateBy) &&
				Objects.equals(updateTime, superPin.updateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, active, updateBy, updateTime, used);
	}
}