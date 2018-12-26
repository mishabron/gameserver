package com.mbronshteyn.data.cards;

import javax.persistence.*;


/**
 * The persistent class for the SuperPin database table.
 * 
 */
@Entity
@Table(name="SuperPin")
@NamedQuery(name="SuperPin.findAll", query="SELECT s FROM SuperPin s")
public class SuperPin extends AuxiliaryPin {
	private static final long serialVersionUID = 1L;

	public SuperPin() {
	}

}