package com.mbronshteyn.data.cards;

import javax.persistence.*;


/**
 * The persistent class for the BonusPin database table.
 * 
 */
@Entity
@Table(name="BonusPin")
@NamedQuery(name="BonusPin.findAll", query="SELECT b FROM BonusPin b")
public class BonusPin extends AuxiliaryPin {

	private static final long serialVersionUID = 1L;

	public BonusPin() {
	}
}