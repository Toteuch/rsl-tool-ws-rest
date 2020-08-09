package com.toteuch.rsl.tool.webservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import com.toteuch.rsl.tool.webservice.model.Hero;

public interface HeroService {
	public List<Hero> findHero(Example<Hero> example, Pageable pageable);
	public Optional<Hero> findById(long id);
	public Hero createHero(Hero hero);
}
