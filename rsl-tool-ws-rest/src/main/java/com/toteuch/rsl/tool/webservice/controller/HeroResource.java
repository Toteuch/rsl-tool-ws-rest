package com.toteuch.rsl.tool.webservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.toteuch.rsl.tool.webservice.exception.BadRequestException;
import com.toteuch.rsl.tool.webservice.exception.ConflictException;
import com.toteuch.rsl.tool.webservice.exception.ResourceNotFoundException;
import com.toteuch.rsl.tool.webservice.model.Hero;
import com.toteuch.rsl.tool.webservice.model.HeroRarityEnum;
import com.toteuch.rsl.tool.webservice.model.HeroRoleEnum;
import com.toteuch.rsl.tool.webservice.model.HeroTypeEnum;
import com.toteuch.rsl.tool.webservice.service.HeroService;
import com.toteuch.rsl.tool.webservice.util.PaginationUtil;

@Component
@Path("/heros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HeroResource {
	@Autowired
	private HeroService heroSvc;
	
	@GET
	public Response findHeros(
			@QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") List<String> sort,
            @QueryParam("heroRarity") String heroRarity,
            @QueryParam("heroRole") String heroRole,
            @QueryParam("heroType") String heroType) throws BadRequestException {
		Hero model = new Hero();
		try {
			model.setHeroRarity(StringUtils.hasText(heroRarity)?HeroRarityEnum.valueOf(heroRarity):null);
			model.setHeroRole(StringUtils.hasText(heroRole)?HeroRoleEnum.valueOf(heroRole):null);
			model.setHeroType(StringUtils.hasText(heroType)?HeroTypeEnum.valueOf(heroType):null);
		} catch (IllegalArgumentException e) {
			throw new BadRequestException("Can't parse filter parameter :: " 
					+ (StringUtils.hasText(heroRarity)?" heroRarity="+heroRarity:"") 
					+ (StringUtils.hasText(heroRole)?" heroRole="+heroRole:"")
					+ (StringUtils.hasText(heroType)?" heroType="+heroType:""));
		}
		Example<Hero> example = Example.of(model, ExampleMatcher.matchingAny().withIgnoreCase().withIgnoreNullValues().withIgnorePaths("id"));
		Pageable pageable = PaginationUtil.getPageable(page, size, sort);
		List<Hero> heros = heroSvc.findHero(example, pageable);
		return Response.ok(new GenericEntity<List<Hero>>(heros) {}).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getHeroById(@PathParam(value = "id") Long userId) throws ResourceNotFoundException {
		Hero hero = heroSvc.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Hero not found :: " + userId));
		return Response.ok(new GenericEntity<Hero>(hero) {}).build();
	}
	
	@POST
	public Response createHero(Hero hero, @Context UriInfo uriInfo, @Context HttpServletRequest request) {
		Hero heroSaved = heroSvc.createHero(hero);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(heroSaved.getId()));
		return Response.created(builder.build()).build();
	}
}
