package com.example.scientificCenter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity

@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
    @Column(nullable = false)
    private String email;

	@Column(nullable = false)
	private boolean activated;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private Double lon;
	
	@Column
	private Double lat;
	
	@Column
	private Boolean isRecenzent;
	
	@Column
	private String title;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<ScientificArea> areas = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_roles", 
			joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
		    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	public Set<UserRole> roles;
	
	@OneToMany(mappedBy="user",fetch = FetchType.LAZY)
	private List<Subscription> subscriptions = new ArrayList<Subscription>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public User(User user) {
		// TODO Auto-generated constructor stub
		this.activated=user.activated;
		this.areas=user.areas;
		this.city=user.city;
		this.country=user.country;
		this.email=user.email;
		this.id=user.id;
		this.isRecenzent=user.isRecenzent;
		this.lat=user.lat;
		this.lon=user.lon;
		this.name=user.name;
		this.surname=user.surname;
		this.password=user.password;
		this.username=user.username;
		this.title=user.title;
		this.roles=user.roles;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public Boolean getIsRecenzent() {
		return isRecenzent;
	}



	public void setIsRecenzent(Boolean isRecenzent) {
		this.isRecenzent = isRecenzent;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	


	public List<ScientificArea> getAreas() {
		return areas;
	}



	public void setAreas(List<ScientificArea> areas) {
		this.areas = areas;
	}



	public Set<UserRole> getRoles() {
		return roles;
	}



	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}



	public Double getLon() {
		return lon;
	}



	public void setLon(Double lon) {
		this.lon = lon;
	}



	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	

}
