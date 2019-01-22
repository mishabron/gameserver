package com.mbronshteyn.data.cards;

import com.mbronshteyn.data.cards.keys.AuxiliaryPinId;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuxiliaryPin implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected AuxiliaryPinId id;

	@Column(name="Active")
	protected boolean active;

	@Column(name="UpdateBy", length=10)
	protected String updateBy;

	@Column(name="UpdateTime")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	protected Date updateTime;

	@Column(name="Used")
	protected boolean used;

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

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean getUsed() {
		return this.used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public AuxiliaryPinId getId() {
		return id;
	}

	public void setId(AuxiliaryPinId id) {
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
		AuxiliaryPin bonusPin = (AuxiliaryPin) o;
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