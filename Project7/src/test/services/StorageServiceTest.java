package services;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import models.Exercise;

public class StorageServiceTest {

	@Test
	public void CreateStorageTest() {
		
		StorageService.getInstance();
		File file = new File(StorageService.getInstance().getDefaultRelativeFilePath());
		assertTrue(file.exists());
	}
	
	@Test
	public void CreateStorage() {
		Exercise add =new Exercise();
		StorageService.getInstance().getExerciseCatalog().getExercises().add(add);
		add.setName("TEST");
		assertEquals(add.getName(), StorageService.getInstance().getExerciseCatalog().getExercises().get(0).getName());
	}
	

}
