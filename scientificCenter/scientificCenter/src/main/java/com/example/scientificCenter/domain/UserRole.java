package com.example.scientificCenter.domain;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "roles")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60, name="name")
	private UserRoleName name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="role_permissions", 
				joinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
	private Set<Permission> permissions;
	
	public UserRole() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserRoleName getName() {
		return name;
	}

	public void setName(UserRoleName name) {
		this.name = name;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	

}
