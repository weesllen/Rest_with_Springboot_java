package com.well.demo.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.well.demo.controllers.PersonController;
import com.well.demo.data.vo.v1.PersonVO;
import com.well.demo.exceptions.RequiredObjectisNullException;
import com.well.demo.exceptions.ResourceNotFoundException;
import com.well.demo.mapper.PersonMapper;
import com.well.demo.model.Person;
import com.well.demo.repositories.PersonRepository;

import jakarta.transaction.Transactional;



@Service
public class PersonServices {
	
		private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
		@Autowired
		PersonRepository repository;
		
		@Autowired
		PagedResourcesAssembler<PersonVO> assembler;
		
	
		public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
		
		logger.info("Finding all People!!");
		
		var personPage = repository.findAll(pageable);
		var personVosPage = personPage.map( p -> PersonMapper.parseObject(p, PersonVO.class));
		personVosPage.map(
				p -> p.add(
						linkTo(methodOn(PersonController.class)
						.findById(p.getKey())).withSelfRel()));
	
		
		Link link = linkTo(
				methodOn(PersonController.class)
				.findAll(pageable.getPageNumber(),
						 pageable.getPageSize(),
						 "asc")).withSelfRel();	
		
			return  assembler.toModel(personVosPage, link);
		
		
	}
		
		
		public PagedModel<EntityModel<PersonVO>> findPersonsByName(String firstName,Pageable pageable) {
			
			logger.info("Finding all People!!");
			
			var personPage = repository.findPersonByName(firstName,pageable);
			var personVosPage = personPage.map( p -> PersonMapper.parseObject(p, PersonVO.class));
			personVosPage.map(
					p -> p.add(
							linkTo(methodOn(PersonController.class)
									.findById(p.getKey())).withSelfRel()));
			
			
			Link link = linkTo(
					methodOn(PersonController.class)
					.findAll(pageable.getPageNumber(),
							pageable.getPageSize(),
							"asc")).withSelfRel();	
			
			return  assembler.toModel(personVosPage, link);
			
			
			
		}
	
		public PersonVO findById(Long id) {
			
		logger.info("Finding one Person!!");	
		 var entity = repository.findById(id) 
					.orElseThrow(() -> new ResourceNotFoundException(" No records for this ID"));
		 PersonVO vo = PersonMapper.parseObject(entity, PersonVO.class);
		 vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		 return vo;
	}
	
		
		public PersonVO create(PersonVO person) {
			
			if (person ==  null) throw new RequiredObjectisNullException();
			logger.info("Creating one Person!!");
			var entity = PersonMapper.parseObject(person, Person.class);
			var vo = PersonMapper.parseObject(repository.save(entity), PersonVO.class);
			vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
			return vo;
	}
		
		
		public PersonVO update(PersonVO person) {
			
			if (person ==  null) throw new RequiredObjectisNullException();
			logger.info("Updating one Person!!");
			Person entity = repository.findById(person.getKey())
					.orElseThrow(() -> new ResourceNotFoundException(" No records for this ID"));
			
			entity.setFirstName(person.getFirstName());
			entity.setLastName(person.getLastName());
			entity.setAddress(person.getAddress());
			entity.setGender(person.getGender());
	
			var vo = PersonMapper.parseObject(repository.save(entity), PersonVO.class);
			vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
			return vo;
	}
		
		@Transactional
		public PersonVO disablePerson(Long id) {
			
			logger.info("disabling one Person!!");
			
			repository.disablePerson(id);
			
			var entity = repository.findById(id) 
					.orElseThrow(() -> new ResourceNotFoundException(" No records for this ID"));
			 PersonVO vo = PersonMapper.parseObject(entity, PersonVO.class);
			 vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
			 return vo;
			 
		}	 
		public void delete(Long id) {
			logger.info("Deleting one Person!!");
			Person entity = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(" No records for this ID"));
			repository.delete(entity);
				
		}
}