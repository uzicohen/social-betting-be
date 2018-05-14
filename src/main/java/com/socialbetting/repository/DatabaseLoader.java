package com.socialbetting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.socialbetting.mock.MockDatabaseCreator;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private MockDatabaseCreator mockDatabaseCreator;

	@Override
	public void run(String... strings) throws Exception {
		this.mockDatabaseCreator.load();
	}

}