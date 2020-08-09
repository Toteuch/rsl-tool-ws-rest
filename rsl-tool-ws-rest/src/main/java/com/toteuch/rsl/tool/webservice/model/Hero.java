package com.toteuch.rsl.tool.webservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hero {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Version
	private int version;
	
	@Column(unique=true)
	private String name;
	
	@Column(name = "hero_type", nullable = false)
	private HeroTypeEnum heroType;
	
	@Column(name = "hero_role", nullable = false)
	private HeroRoleEnum heroRole;
	
	@Column(name = "hero_rarity", nullable = false)
	private HeroRarityEnum heroRarity;
}
