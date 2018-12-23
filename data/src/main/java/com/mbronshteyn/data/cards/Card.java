package com.mbronshteyn.data.cards;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;


/**
 * The persistent class for the Cards database table.
 * 
 */
@Entity
@Table(name="Cards")
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(insertable=false, updatable=false, nullable=false)
	private int id;
	
	@Column(name="ActivateDate")
	private Timestamp activateDate;

	@Column(name="Active")
	private boolean active;

	@Column(name="Barcode", nullable=false, length=7)
	private String barcode;

	@Column(name="card_number", nullable=false, length=12)
	private String cardNumber;

	@Column(name="Email", length=45)
	private String email;

	@Column(name="Game_id", nullable=false)
	private int game_id;

	@Column(name="Paid")
	private boolean paid;

	@Column(name="Played")
	private boolean played;

	@Column(name="UpdateBy", length=10)
	private String updateBy;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="WinPin")
	private int winPin;

	//bi-directional many-to-one association to Batch
	@ManyToOne
	@JoinColumn(name="Batch_id", referencedColumnName="id")
	private Batch batch;

	//bi-directional many-to-one association to Hit
	@OneToMany(mappedBy="card")
	private Set<Hit> hits;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Card() {
	}

	public Timestamp getActivateDate() {
		return this.activateDate;
	}

	public void setActivateDate(Timestamp activateDate) {
		this.activateDate = activateDate;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGame_id() {
		return this.game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public boolean getPaid() {
		return this.paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public boolean getPlayed() {
		return this.played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
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

	public int getWinPin() {
		return this.winPin;
	}

	public void setWinPin(int winPin) {
		this.winPin = winPin;
	}

	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Set<Hit> getHits() {
		return this.hits;
	}

	public void setHits(Set<Hit> hits) {
		this.hits = hits;
	}

	public Hit addHit(Hit hit) {
		getHits().add(hit);
		hit.getId().setCard(this);

		return hit;
	}

	public Hit removeHit(Hit hit) {
		getHits().remove(hit);
		hit.getId().setCard(null);

		return hit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return id == card.id &&
				active == card.active &&
				game_id == card.game_id &&
				paid == card.paid &&
				played == card.played &&
				winPin == card.winPin &&
				Objects.equals(activateDate, card.activateDate) &&
				Objects.equals(barcode, card.barcode) &&
				Objects.equals(cardNumber, card.cardNumber) &&
				Objects.equals(email, card.email) &&
				Objects.equals(updateBy, card.updateBy) &&
				Objects.equals(updateTime, card.updateTime) &&
				Objects.equals(batch, card.batch) &&
				Objects.equals(hits, card.hits);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, activateDate, active, barcode, cardNumber, email, game_id, paid, played, updateBy, updateTime, winPin, batch, hits);
	}
}