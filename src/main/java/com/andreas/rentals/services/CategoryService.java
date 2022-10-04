package com.andreas.rentals.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Category;
import com.andreas.rentals.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		return obj.get();
	}

	public Category findByName(String name) {
		for (Category category : findAll()) {
			if (category.getName().toLowerCase().equals(name.toLowerCase())) {
				return category;
			}
		}
		return null;
	}

	public void createSpecification(Category category) {
		category.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		categoryRepository.save(category);
	}
}
