package com.toteuch.rsl.tool.webservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toteuch.rsl.tool.webservice.model.Hero;
import com.toteuch.rsl.tool.webservice.repository.HeroRepository;
import com.toteuch.rsl.tool.webservice.service.HeroService;

@Service
public class HeroServiceImpl implements HeroService {

	@Autowired
	private HeroRepository heroRepo;
	
	@Override
	public List<Hero> findHero(Example<Hero> example, Pageable pageable) {
		return heroRepo.findAll(example,pageable).getContent();
	}

	@Override
	public Optional<Hero> findById(long id) {
		return heroRepo.findById(id);
	}

	@Override
	public Hero createHero(Hero hero) {
		return heroRepo.save(hero);
		
	}
	
}
