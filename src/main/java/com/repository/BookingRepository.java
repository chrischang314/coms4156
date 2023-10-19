package org.example.repository;


import org.example.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}