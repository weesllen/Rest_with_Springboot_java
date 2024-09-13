package com.well.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.well.demo.data.vo.v1.BookVO;
import com.well.demo.services.BookService;
import com.well.demo.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Books", description = "Libraries's Endpoints")
public class BookControllers {
	
	@Autowired
	BookService bookService;
	
	@GetMapping(value = "/{id}",
				produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Find a book by id", description = "Find a Book by id",
				tags = {"Books"},
				responses = {
						@ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class ))
						),
						@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
						@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
						@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
						@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
						})
	public BookVO findById(@PathVariable (value = "id") Long id) {
			return bookService.findById(id);
	}
	
	
	
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds all Books", description = "Find all Books",
				tags = {"Books"},
				responses = {
					@ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class ))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
					})
	public List<BookVO> findAll(){
		return bookService.findAll();
	}
	
	
	
	
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
				produces =  {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Signing up a Book", description = "Signing up a Book by passing  in a Jason XML  or YML Representation of the Books",
			tags = {"Books"},
			responses = {
					@ApiResponse(description = "Sucess", responseCode = "200", 
						content = @Content(schema = @Schema(implementation = BookVO.class ))
					),
						@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
						@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
						@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
						})
	public BookVO create(@RequestBody BookVO book) {
		 	return bookService.create(book);
	}
	
	
	
	
	@PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
				produces =  {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Update a Book", description = "Update a Book  by passing  in a Jason XML  or YML Representation of the Books",
		tags = {"Books"},
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class ))
				),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
					})
	public BookVO update(@RequestBody BookVO book) {
		return bookService.update(book);
	}
	
	
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a Book",
	description = "Delete a Book  by passing  in a Json XML  or YML Representation of the Books",
	tags = {"Books"},
	responses = {
		@ApiResponse(description = "No Content", responseCode = "200",content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
		})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	
	
	
	

}
