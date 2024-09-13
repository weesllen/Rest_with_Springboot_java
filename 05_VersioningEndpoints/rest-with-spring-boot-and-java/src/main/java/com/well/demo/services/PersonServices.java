package com.well.demo.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.well.demo.data.vo.v1.PersonVO;
import com.well.demo.data.vo.v2.PersonVOV2;
import com.well.demo.exceptions.ResourceNotFoundException;
import com.well.demo.mapper.PersonMapper;
import com.well.demo.mapper.custom.PersonMap;
import com.well.demo.model.Person;
import com.well.demo.repositories.PersonRepository;



@Service
public class PersonServices {
	
		private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
		@Autowired
		PersonRepository repository;
		
		@Autowired
		 PersonMap mapper;
	
		public List<PersonVO> findAll() {
		
		logger.info("Finding all People!!");
		return PersonMapper.parseListObjects( repository.findAll(), PersonVO.class);
		
	}
	
		public PersonVO findById(Long id) {
			
		logger.info("Finding one Person!!");	
		 var entity = repository.findById(id) 
					.orElseThrow(() -> new ResourceNotFoundException(" No records for this ID"));
		 return PersonMapper.parseObject(entity, PersonVO.class);
	}
			
		public PersonVO create(PersonVO person) {
			
			logger.info("Creating one Person!!");
			var entity = PersonMapper.parseObject(person, Person.class);
			var vo = PersonMapper.parseObject(repository.save(entity), PersonVO.class);
			return vo;
	}
		
		public PersonVOV2 createV2(PersonVOV2 person) {
			
			logger.info("Creating one Person!!");
			var entity = mapper.convertVoToEntity(person);
			var vo = mapper.convertToVo(repository.save(entity));
			return vo;
	}
		
		public PersonVO update(PersonVO person) {
			
			logger.info("Updating one Person!!");
			Person entity = repository.findById(person.getId())
					.orElseThrow(() -> new ResourceNotFoundException(" No records for this ID"));
			
			entity.setFirstName(person.getFirstName());
			entity.setLastName(person.getLastName());
			entity.setAddress(person.getAddress());
			entity.setGender(person.getGender());
	
			var vo = PersonMapper.parseObject(repository.save(entity), PersonVO.class);
			return vo;
	}
		public void delete(Long id) {
			logger.info("Deleting one Person!!");
			Person entity = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(" No records for this ID"));
			repository.delete(entity);
				
	}
	}