package com.well.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.unitters.mapper.mocks.MockBook;
import com.well.demo.data.vo.v1.BookVO;
import com.well.demo.mapper.BookMapper;
import com.well.demo.model.Book;

public class DozerConverterTest {
    
    MockBook inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTest() {
        BookVO output = BookMapper.parseBook(inputObject.mockEntity(), BookVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Some Author", output.getAuthor());
        assertNotNull( output.getlaunchDate());
        assertEquals(25D, output.getPrice());
        assertEquals("Some Title", output.getTitle());
      
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<BookVO> outputList = BookMapper.parseBooks(inputObject.mockEntityList(), BookVO.class);
        BookVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Some Author0", outputZero.getAuthor());
        assertNotNull( outputZero.getlaunchDate());
        assertEquals(25D, outputZero.getPrice());
        assertEquals("Some Title0", outputZero.getTitle());
        
        BookVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(0L), outputSeven.getKey());
        assertEquals("Some Author7", outputSeven.getAuthor());
        assertNotNull( outputSeven.getlaunchDate());
        assertEquals(25D, outputSeven.getPrice());
        assertEquals("Some Title7", outputSeven.getTitle());
        
        BookVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(0L), outputTwelve.getKey());
        assertEquals("Some Author12", outputTwelve.getAuthor());
        assertNotNull( outputTwelve.getlaunchDate());
        assertEquals(25D, outputTwelve.getPrice());
        assertEquals("Some Title12", outputTwelve.getTitle());
    }

    @Test
    public void parseVOToEntityTest() {
        Book output = BookMapper.parseBook(inputObject.mockVO(), Book.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Some Author0", output.getAuthor());
        assertNotNull(output.getLaunchDate());
        assertEquals(25D, output.getPrice());
        assertEquals("Some Title0", output.getTitle());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Book> outputList = BookMapper.parseBooks(inputObject.mockVOList(), Book.class);
        Book outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Some Author0", outputZero.getAuthor());
        assertNotNull(outputZero.getLaunchDate());
        assertEquals(25D, outputZero.getPrice());
        assertEquals("Some Title0", outputZero.getTitle());
        
        Book outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Some Author7", outputSeven.getAuthor());
        assertNotNull(outputSeven.getLaunchDate());
        assertEquals(25D, outputSeven.getPrice());
        assertEquals("Some Title7", outputSeven.getTitle());
        
        Book outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Some Author12", outputTwelve.getAuthor());
        assertNotNull(outputTwelve.getLaunchDate());
        assertEquals(25D, outputTwelve.getPrice());
        assertEquals("Some Title12", outputTwelve.getTitle());
    }
}
