package com.well.demo.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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
		
	
		public List<PersonVO> findAll() {
		
		logger.info("Finding all People!!");
			 
		var persons = PersonMapper.parseListObjects( repository.findAll(), PersonVO.class);
		persons 
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel())); 
			return persons;
		
		
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