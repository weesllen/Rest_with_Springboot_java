package com.well.demo.unittestes.mockito.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.well.demo.data.vo.v1.PersonVO;
import com.well.demo.exceptions.RequiredObjectisNullException;
import com.well.demo.model.Person;
import com.well.demo.repositories.PersonRepository;
import com.well.demo.services.PersonServices;
import com.well.demo.unitters.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {
	
	MockPerson input;
	
	@InjectMocks
	private PersonServices services;
	
	@Mock
	PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		
		List <Person> List = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(List);
		
		var people = services.findAll(null);
		
		assertNotNull(people);
		assertEquals(14, people.size());
		
		var personOne = people.get(1);
		
		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());
		
		assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", personOne.getAddress());
		assertEquals("First Name Test1", personOne.getFirstName());
		assertEquals("Last Name Test1", personOne.getLastName());
		assertEquals("Female", personOne.getGender());
		
		
		var personTwo = people.get(2);
		
		assertNotNull(personTwo);
		assertNotNull(personTwo.getKey());
		assertNotNull(personTwo.getLinks());
		
		assertTrue(personTwo.toString().contains("links: [</api/person/v1/2>;rel=\"self\"]"));
		assertEquals("Addres Test2", personTwo.getAddress());
		assertEquals("First Name Test2", personTwo.getFirstName());
		assertEquals("Last Name Test2", personTwo.getLastName());
		assertEquals("Male", personTwo.getGender());
		
	}

	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
		
		var result = services.findById(entity.getId());
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		
	}

	@Test
	void testCreate() {
		
		Person entity = input.mockEntity(1);
		entity.setId(1);
		
		Person persisted = entity;
		persisted.setId(1);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1);
		
		
		when(repository.save(any(Person.class))).thenReturn(persisted);
		
		var result = services.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		
	}
	@Test
	void testCreateWithNullPerson() {
		
		Exception exception = assertThrows(RequiredObjectisNullException.class,() -> {
			services.create(null);
		});
		
		String ExpectedMessage = "It is not allowed number null !!!";
		String ActualMessage = exception.getMessage();
		assertTrue(ExpectedMessage.contains(ActualMessage));

	}
	@Test
	void testUpdateWithNullPerson() {
		
		Exception exception = assertThrows(RequiredObjectisNullException.class,() -> {
			services.update(null);
		});
		
		String ExpectedMessage = "It is not allowed number null !!!";
		String ActualMessage = exception.getMessage();
		assertTrue(ExpectedMessage.contains(ActualMessage));
		
	}

	@Test
	void testUpdate() {
		
	Person entity = input.mockEntity(1);
		
		Person persisted = entity;
		persisted.setId(1);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1);
		
		when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = services.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		
	}

	@Test
	void testDelete() {
		
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		services.delete(1L);
	}

}
