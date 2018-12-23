package com.mbronshteyn.data.cards;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Game database table.
 * 
 */
@Entity
@Table(name="Game")
@NamedQuery(name="Game.findAll", query="SELECT g FROM Game g")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(insertable=false, updatable=false, nullable=false)
	private int id;

	@Column(name="Name", length=45)
	private String name;

	public Game() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}