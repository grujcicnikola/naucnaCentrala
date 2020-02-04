package com.example.scientificCenter.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;




@Entity
public class Journal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String issn;
	
	@Column(nullable = false)
	private Boolean isActivated;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<ScientificArea> areas = new HashSet<ScientificArea>();
	
	@Column(nullable = false)
	private Boolean isOpenAccess;
	
	@Column(nullable = false)
	private Double price;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Editor editorInChief;
	
	@Column(nullable = false)
	private int subscriptionNum;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Paper> papers = new HashSet<Paper>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<MethodOfPayment> methodOfPayment =new HashSet<MethodOfPayment>();
	
	@OneToMany(mappedBy="journal",fetch = FetchType.LAZY)
	private List<Subscription> subscriptions = new ArrayList<Subscription>();
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Membership> membership = new ArrayList<Membership>();
	
	public Journal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public Set<ScientificArea> getAreas() {
		return areas;
	}

	public void setAreas(Set<ScientificArea> areas) {
		this.areas = areas;
	}

	public Boolean getIsOpenAccess() {
		return isOpenAccess;
	}

	public void setIsOpenAccess(Boolean isOpenAccess) {
		this.isOpenAccess = isOpenAccess;
	}

	public Editor getEditorInChief() {
		return editorInChief;
	}

	public void setEditorInChief(Editor editorInChief) {
		this.editorInChief = editorInChief;
	}

	public Set<Paper> getPapers() {
		return papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getSubscriptionNum() {
		return subscriptionNum;
	}

	public void setSubscriptionNum(int subscriptionNum) {
		this.subscriptionNum = subscriptionNum;
	}

	public Set<MethodOfPayment> getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(Set<MethodOfPayment> methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}

	public Boolean getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public List<Membership> getMembership() {
		return membership;
	}

	public void setMembership(List<Membership> membership) {
		this.membership = membership;
	}
	
}
