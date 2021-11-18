package com.br.refera.sysorder.loader;

import com.br.refera.sysorder.entity.Category;
import com.br.refera.sysorder.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class DatabaseLoader implements CommandLineRunner {

	private final CategoryRepository categoryRepository;

	@Override
	public void run(String... strings) throws Exception {
		List<Category> categoryList = (List<Category>) categoryRepository.findAll();
		if (categoryList.isEmpty()) {
			Category category = Category.builder().desc("Hidráulica").build();
			categoryRepository.save(category);

			Category category2 = Category.builder().desc("Pintura").build();
			categoryRepository.save(category2);

			Category category3 = Category.builder().desc("Elétrica").build();
			categoryRepository.save(category3);
		}
	}

}
