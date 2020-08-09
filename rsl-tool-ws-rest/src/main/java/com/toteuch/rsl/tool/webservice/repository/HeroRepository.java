package com.toteuch.rsl.tool.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toteuch.rsl.tool.webservice.model.Hero;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long>{

}
