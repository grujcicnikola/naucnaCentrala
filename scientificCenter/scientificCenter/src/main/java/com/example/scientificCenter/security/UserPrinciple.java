package com.example.scientificCenter.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.scientificCenter.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrinciple implements UserDetails {
	
	private Long id;
	private String name;
	private String surname;
	@JsonIgnore
	private String password;
	private String email;
	
	private Collection<? extends GrantedAuthority> authorities;

	public UserPrinciple(Long id, String name, String surname, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}

	public static UserPrinciple build(User user){
		
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
		new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		
		return new UserPrinciple(user.getId(),
				user.getName(),
				user.getSurname(),
				user.getPassword(),
				user.getEmail(),
				authorities);
	}
	
	

	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		//vracam email jer se koristi email a ne username
		return email;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

	@Override
    public String toString(){
    	return "UP "+this.name + " ,pass: "+this.password;  
    }
}
