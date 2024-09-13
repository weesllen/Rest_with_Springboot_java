 package com.well.demo.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.well.demo.controllers.BookControllers;
import com.well.demo.data.vo.v1.BookVO;
import com.well.demo.exceptions.RequiredObjectisNullException;
import com.well.demo.exceptions.ResourceNotFoundException;
import com.well.demo.mapper.BookMapper;
import com.well.demo.model.Book;
import com.well.demo.repositories.BookRepository;


@Service
public class BookService {
	
	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	BookRepository bookRepository;
	
	PagedResourcesAssembler<BookVO> assembler;


	public BookVO findById(Long key) {
		
		logger.info("Find a Book by Key");
		
		Book entity = bookRepository.findById(key)
				.orElseThrow(() -> new ResourceNotFoundException("No Record this Key!!"));
		BookVO vob = BookMapper.parseBook(entity, BookVO.class);
		vob.add(linkTo(methodOn(BookControllers.class).findById(vob.getKey())).withSelfRel());
		return vob;
	}

	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
		
		logger.info("Finds all Books ");
		
		var bookPage = bookRepository.findAll(pageable);
		var bookVosPage = bookPage.map(b -> BookMapper.parseBook(b,BookVO.class));
			bookVosPage.map(b -> b.add(
					linkTo(methodOn(BookControllers.class)
							.findById(b.getKey())).withSelfRel()));
			
				
		Link link = linkTo(
				methodOn(BookControllers.class)
				.findAll(pageable.getPageNumber(),
					pageable.getPageSize(),
					"asc")).withSelfRel();
					
		return assembler.toModel(bookVosPage,link);
	}

	public BookVO create(BookVO book) {
		
		logger.info("Creating a new book ");
		
		if(book == null) throw new RequiredObjectisNullException();
		var entity = BookMapper.parseBook(book, Book.class);
		var vob = BookMapper.parseBook(bookRepository.save(entity), BookVO.class);
		vob.add(linkTo(methodOn(BookControllers.class).findById(vob.getKey())).withSelfRel());
		
		return vob;
	}

	public BookVO update(BookVO book) {
		
		logger.info("Updatting a new book ");
		
		if (book == null)throw new RequiredObjectisNullException();
		var entity = bookRepository.findById(book.getKey())
				.orElseThrow(() -> new  ResourceNotFoundException("No Record this Key!!!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getlaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vob = BookMapper.parseBook(entity, BookVO.class);
		vob.add(linkTo(methodOn(BookControllers.class).findById(vob.getKey())).withSelfRel());
		return vob;
	}

	public void delete(Long key) {
		
		logger.info("Deleting a new book ");
		
		Book entity = bookRepository.findById(key)
				.orElseThrow(() -> new RequiredObjectisNullException("No Record this Key"));
			bookRepository.delete(entity);
		
	}

}
