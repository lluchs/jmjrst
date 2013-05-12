package org.jis.generator;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GeneratorTest {
	
	Generator subject;
	File testFile;

	@Before
	public void setUp() throws Exception {
		subject = new Generator(null, 0);
		testFile = new File("testfile.zip");
	}

	@After
	public void tearDown() throws Exception {
		testFile.delete();
	}

	@Test
	public void testCreateZip() {
		subject.createZip(testFile, new Vector<File>());
		assertTrue("The zip file should have been created.", testFile.exists());
	}

}
