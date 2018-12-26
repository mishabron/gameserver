package com.mbronshteyn.data.cards;

import javax.persistence.*;


/**
 * The persistent class for the BoosterPin database table.
 * 
 */
@Entity
@Table(name="BoosterPin")
@NamedQuery(name="BoosterPin.findAll", query="SELECT b FROM BoosterPin b")
public class BoosterPin extends AuxiliaryPin {
	private static final long serialVersionUID = 1L;

	public BoosterPin() {
	}
}