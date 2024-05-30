package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Permission(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + "]";
	}
    
    // getters and setters
}