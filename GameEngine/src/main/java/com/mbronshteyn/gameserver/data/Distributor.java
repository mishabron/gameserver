package com.mbronshteyn.gameserver.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the Distributor database table.
 * 
 */
@Entity
@NamedQuery(name="Distributor.findAll", query="SELECT d FROM Distributor d")
@EntityListeners(AuditingEntityListener.class)
public class Distributor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")	
	@JsonIgnore		
	private Long id;

	@Column(name="Address")
	private String address;

	@Column(name="City")
	private String city;

	@Column(name="Country")	
	private String country;

	@Column(name="Email")
	private String email;

	@Column(name="Name")
	private String name;

	@Column(name="Notes")
	private String notes;

	@Column(name="State")
	private String state;

	@Column(name="Telephone")
	private String telephone;

	@Column(name="UpdateTime")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonIgnore    
	private Date updateTime;

	@Column(name="Zip")
	private String zip;

	//bi-directional many-to-one association to Contact
	@OneToMany(mappedBy="distributor", fetch=FetchType.EAGER, cascade=CascadeType.ALL)	
	private List<Contact> contacts;

	@OneToMany(mappedBy="distributor", fetch=FetchType.EAGER, cascade=CascadeType.ALL)	
    @JsonIgnore 	
	private Set<Vendor> vendors;

	
	public Distributor() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setDistributor(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setDistributor(null);

		return contact;
	}

	public Set<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(Set<Vendor> vendors) {
		this.vendors = vendors;
	}

	public Vendor addVendor(Vendor vendor) {
		getVendors().add(vendor);
		vendor.setDistributor(this);

		return vendor;
	}

	public Vendor removeVendor(Vendor vendor) {
		getVendors().remove(vendor);
		vendor.setDistributor(null);

		return vendor;
	}	
	
}