package com.andreas.rentals.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreas.rentals.entities.Car;
import com.andreas.rentals.entities.CarSpecification;
import com.andreas.rentals.entities.Category;
import com.andreas.rentals.entities.Rental;
import com.andreas.rentals.entities.Specification;
import com.andreas.rentals.repositories.RentalRepository;

@Service
public class RentalService {

	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private CarService carService;

	@Autowired
	private CarSpecificationService carSpecificationService;

	public List<Rental> findAll() {
		return rentalRepository.findAll();
	}

	public Rental findById(Long id) {
		Optional<Rental> obj = rentalRepository.findById(id);
		return obj.get();
	}

	public void createRental(Rental rental) {
		rental.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		rental.setUpdatedAt(new Date(Calendar.getInstance().getTime().getTime()));
		rentalRepository.save(rental);
	}

	public List<Car> findAvailable(Date startDate, Date endDate) {

		List<Car> cars = carService.findAll();

		for (Rental rental : findAll()) {
			if (checkAvailable(startDate, endDate, rental.getStartDate(), rental.getEndDate())) {
				cars.remove(rental.getCar());
			}
		}
		return cars;
	}

	public List<Car> findAvailable(Date startDate, Date endDate, Category category, Specification specification) {

		List<Car> cars = carService.findAll();

		for (Rental rental : findAll()) {
			if (checkAvailable(startDate, endDate, rental.getStartDate(), rental.getEndDate())) {
				cars.remove(rental.getCar());
			}
		}

		for (Car car : cars) {
			for (CarSpecification carSpecs : carSpecificationService.getCarSpecifications(car)) {
				if (!carSpecs.getSpecification().equals(specification) && !car.getCategory().equals(category))
					cars.remove(car);
			}
		}

		return cars;
	}

	public List<Car> findAvailable(Date startDate, Date endDate, Category category) {

		List<Car> cars = carService.findAll();
		System.out.println(cars);
		for (Rental rental : findAll()) {
			if (checkAvailable(startDate, endDate, rental.getStartDate(), rental.getEndDate())) {
				cars.remove(rental.getCar());
			}
		}

		for (Car car : cars) {
			if (!car.getCategory().equals(category))
				cars.remove(car);
		}

		return cars;
	}

	public List<Car> findAvailable(Date startDate, Date endDate, Specification specification) {

		List<Car> cars = carService.findAll();

		for (Rental rental : findAll()) {
			if (checkAvailable(startDate, endDate, rental.getStartDate(), rental.getEndDate())) {
				cars.remove(rental.getCar());
			}
		}

		for (Car car : cars) {
			for (CarSpecification carSpecs : carSpecificationService.getCarSpecifications(car)) {
				if (!carSpecs.getSpecification().equals(specification))
					cars.remove(car);
			}
		}

		return cars;
	}

	private boolean between(Date date, Date init, Date finish) {
		return date.after(init) && date.before(finish);
	}

	private boolean checkAvailable(Date searchStart, Date searchEnd, Date rentalStart, Date rentalEnd) {
		boolean sameDate = searchEnd.equals(rentalStart) || searchEnd.equals(rentalEnd) || searchStart.equals(rentalEnd)
				|| searchStart.equals(rentalStart);

		return between(searchEnd, rentalStart, rentalEnd) || between(searchStart, rentalStart, rentalEnd)
				|| between(rentalStart, searchStart, searchEnd) || sameDate;
	}
}
