package com.mbronshteyn.data.cards;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;


/**
 * The persistent class for the Batch database table.
 * 
 */
@Entity
@Table(name="Batch")
@NamedQuery(name="Batch.findAll", query="SELECT b FROM Batch b")
public class Batch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(insertable=false, updatable=false, nullable=false)
	private int id;
	
	@Column(name="Barcode", nullable=false, length=7)
	private String barcode;

	@Column(name="Batchdate", nullable=false)
	private Timestamp batchdate;

	@Column(name="CardPrice", precision=10, scale=2)
	private BigDecimal cardPrice;

	@Column(name="CardsInBatch")
	private int cardsInBatch;

	@Column(name="FreeGame")
	private byte freeGame;

	@Column(name="Game_id", nullable=false)
	private int game_id;

	@Column(name="Payout1", precision=10, scale=2)
	private BigDecimal payout1;

	@Column(name="Payout2", precision=10, scale=2)
	private BigDecimal payout2;

	@Column(name="Payout3", precision=10, scale=2)
	private BigDecimal payout3;

	@Column(name="UpdateBy", length=10)
	private String updateBy;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="Userid", length=10)
	private String userid;

	//bi-directional many-to-one association to BonusPin
	@OneToMany(mappedBy="batch")
	private Set<BonusPin> bonusPins;

	//bi-directional many-to-one association to BoosterPin
	@OneToMany(mappedBy="batch")
	private Set<BoosterPin> boosterPins;

	//bi-directional many-to-one association to Card
	@OneToMany(mappedBy="batch", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Card> cards;

	//bi-directional many-to-one association to SuperPin
	@OneToMany(mappedBy="batch")
	private Set<SuperPin> superPins;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Batch() {
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Timestamp getBatchdate() {
		return this.batchdate;
	}

	public void setBatchdate(Timestamp batchdate) {
		this.batchdate = batchdate;
	}

	public BigDecimal getCardPrice() {
		return this.cardPrice;
	}

	public void setCardPrice(BigDecimal cardPrice) {
		this.cardPrice = cardPrice;
	}

	public int getCardsInBatch() {
		return this.cardsInBatch;
	}

	public void setCardsInBatch(int cardsInBatch) {
		this.cardsInBatch = cardsInBatch;
	}

	public byte getFreeGame() {
		return this.freeGame;
	}

	public void setFreeGame(byte freeGame) {
		this.freeGame = freeGame;
	}

	public int getGame_id() {
		return this.game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public BigDecimal getPayout1() {
		return this.payout1;
	}

	public void setPayout1(BigDecimal payout1) {
		this.payout1 = payout1;
	}

	public BigDecimal getPayout2() {
		return this.payout2;
	}

	public void setPayout2(BigDecimal payout2) {
		this.payout2 = payout2;
	}

	public BigDecimal getPayout3() {
		return this.payout3;
	}

	public void setPayout3(BigDecimal payout3) {
		this.payout3 = payout3;
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

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Set<BonusPin> getBonusPins() {
		return this.bonusPins;
	}

	public void setBonusPins(Set<BonusPin> bonusPins) {
		this.bonusPins = bonusPins;
	}

	public BonusPin addBonusPin(BonusPin bonusPin) {
		getBonusPins().add(bonusPin);
		bonusPin.getId().setBatch(this);

		return bonusPin;
	}

	public BonusPin removeBonusPin(BonusPin bonusPin) {
		getBonusPins().remove(bonusPin);
		bonusPin.getId().setBatch(null);

		return bonusPin;
	}

	public Set<BoosterPin> getBoosterPins() {
		return this.boosterPins;
	}

	public void setBoosterPins(Set<BoosterPin> boosterPins) {
		this.boosterPins = boosterPins;
	}

	public BoosterPin addBoosterPin(BoosterPin boosterPin) {
		getBoosterPins().add(boosterPin);
		boosterPin.getId().setBatch(this);

		return boosterPin;
	}

	public BoosterPin removeBoosterPin(BoosterPin boosterPin) {
		getBoosterPins().remove(boosterPin);
		boosterPin.getId().setBatch(null);

		return boosterPin;
	}

	public Set<Card> getCards() {
		return this.cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	public Card addCard(Card card) {
		getCards().add(card);
		card.setBatch(this);

		return card;
	}

	public Card removeCard(Card card) {
		getCards().remove(card);
		card.setBatch(null);

		return card;
	}

	public Set<SuperPin> getSuperPins() {
		return this.superPins;
	}

	public void setSuperPins(Set<SuperPin> superPins) {
		this.superPins = superPins;
	}

	public SuperPin addSuperPin(SuperPin superPin) {
		getSuperPins().add(superPin);
		superPin.getId().setBatch(this);

		return superPin;
	}

	public SuperPin removeSuperPin(SuperPin superPin) {
		getSuperPins().remove(superPin);
		superPin.getId().setBatch(null);

		return superPin;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Batch batch = (Batch) o;
		return id == batch.id &&
				cardsInBatch == batch.cardsInBatch &&
				freeGame == batch.freeGame &&
				game_id == batch.game_id &&
				Objects.equals(barcode, batch.barcode) &&
				Objects.equals(batchdate, batch.batchdate) &&
				Objects.equals(cardPrice, batch.cardPrice) &&
				Objects.equals(payout1, batch.payout1) &&
				Objects.equals(payout2, batch.payout2) &&
				Objects.equals(payout3, batch.payout3) &&
				Objects.equals(updateBy, batch.updateBy) &&
				Objects.equals(updateTime, batch.updateTime) &&
				Objects.equals(userid, batch.userid) &&
				Objects.equals(bonusPins, batch.bonusPins) &&
				Objects.equals(boosterPins, batch.boosterPins) &&
				Objects.equals(cards, batch.cards) &&
				Objects.equals(superPins, batch.superPins);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, barcode, batchdate, cardPrice, cardsInBatch, freeGame, game_id, payout1, payout2, payout3, updateBy, updateTime, userid, bonusPins, boosterPins, cards, superPins);
	}
}