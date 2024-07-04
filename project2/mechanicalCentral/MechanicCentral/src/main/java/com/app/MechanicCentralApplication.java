package com.app;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MechanicCentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(MechanicCentralApplication.class, args);
	}

	@Bean
	public ModelMapper configureMapper() {
		System.out.println("in config mapper....");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;// method rets bean instance to SC
	}

}

// good practices i am lagging in the project: while using hardcore constant in our project, we should always store them seperately and use them wherever we want
// it is because whenever we want to change them in future we can do that from the single place and that will be applicable to allover the project wherever we are using that constant
// just like while doing pagination we used default values, but very api should implement pagination, so everytime gives a constant should be replaced by a singletime variable
// so using com.app.config file for this improvement