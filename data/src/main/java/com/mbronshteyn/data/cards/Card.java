package com.mbronshteyn.data.cards;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


/**
 * The persistent class for the Cards database table.
 * 
 */
@Entity
@Table(name="Cards")
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
@EntityListeners(AuditingEntityListener.class)
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(insertable=false, updatable=false, nullable=false)
	private int id;
	
	@Column(name="ActivateDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activateDate;

	@Column(name="Active")
	private boolean active;

	@Column(name="Barcode", nullable=false, length=7)
	private String barcode;

	@Column(name="card_number", nullable=false, length=12)
	private Long cardNumber;

	@Column(name="Price", precision=10, scale=2)
	private BigDecimal price;

	@Column(name="Email", length=45)
	private String email;

	@Column(name="Game_id", nullable=false)
	private int gameId;

	@Column(name="Paid")
	private boolean paid;

	@Column(name="Played")
	private boolean played;

	@Column(name="UpdateBy", length=10)
	@CreatedBy
	private String createdBy;

	@Column(name="NumberOfHits")
	private int numberOfHits;

	@Column(name="DeviceId", length=20)
	private String deviceId;

	@Column(name="UpdateTime")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	//bi-directional many-to-one association to CardBatch
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Batch_id", referencedColumnName="id")
	private CardBatch batch;

	//bi-directional many-to-one association to Hit
	@OneToMany(mappedBy="card",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@OrderBy("UpdateTime DESC")
	private List<Play> plays = new ArrayList<>();

	@Column(name="ActivateBy", length=45)
	private String activateBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Card() {
		Play play = new Play();
		play.setCard(this);
		plays.add(play);
	}

	public Date getActivateDate() {
		return this.activateDate;
	}

	public void setActivateDate(Date activateDate) {
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

	public Long getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGameId() {
		return this.gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
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

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWinPin() {
		return getPlays().get(0).getWinPin();
	}

	public void setWinPin(String winPin) {
		getPlays().get(0).setWinPin(winPin);
	}

	public CardBatch getBatch() {
		return this.batch;
	}

	public void setBatch(CardBatch batch) {
		this.batch = batch;
	}

	public Set<Hit> getHits() {
		return getPlays().get(0).getHits();
	}

	public boolean isActive() {
		return active;
	}

	public boolean isPaid() {
		return paid;
	}

	public boolean isPlayed() {
		return played;
	}

	public int getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(int numberOfHits) {
		this.numberOfHits = numberOfHits;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getActivateBy() {
		return activateBy;
	}

	public void setActivateBy(String activateBy) {
		this.activateBy = activateBy;
	}

	public List<Play> getPlays() {
		return plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}

	public Hit addHit(Hit hit) {
		numberOfHits = numberOfHits +1;
		hit.setPlay(plays.get(0));
		hit.setSequence(numberOfHits);
		getHits().add(hit);
		return hit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return id == card.id &&
				active == card.active &&
				gameId == card.gameId &&
				paid == card.paid &&
				played == card.played &&
				Objects.equals(activateDate, card.activateDate) &&
				Objects.equals(barcode, card.barcode) &&
				Objects.equals(cardNumber, card.cardNumber) &&
				Objects.equals(email, card.email) &&
				Objects.equals(createdBy, card.createdBy) &&
				Objects.equals(createTime, card.createTime) &&
				Objects.equals(batch, card.batch) &&
				Objects.equals(plays, card.plays);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, activateDate, active, barcode, cardNumber, email, gameId, paid, played, createdBy, createTime);
	}
}