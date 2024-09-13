package com.well.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.well.demo.data.vo.v1.BookVO;
import com.well.demo.model.Book;

public class BookMapper {
	
	public static ModelMapper mapperB = new ModelMapper();
	
	static {
		mapperB.createTypeMap(Book.class, BookVO.class)
		.addMapping(Book::getId, BookVO::setKey);
		
		mapperB.createTypeMap(BookVO.class, Book.class)
		.addMapping(BookVO::getKey, Book::setId);
	}
	
	
	public static <O, D> D parseBook(O origin, Class<D> destination) {
		return mapperB.map(origin, destination);
	}
	

	
	public static <O, D> List<D> parseBooks(List<O> origin, Class<D> destination) {
		List<D> destinationbooks = new ArrayList<>();
		for (O o : origin) {	
			 destinationbooks.add(mapperB.map(o, destination));
		}
		return destinationbooks;
		
	}
	


}
