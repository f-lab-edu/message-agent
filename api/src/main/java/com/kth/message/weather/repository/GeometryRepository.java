package com.kth.message.weather.repository;


import com.kth.message.entity.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeometryRepository extends JpaRepository<Geometry, Long> {

	Geometry findByRegionCityAndRegionTownAndRegionVillage(String regionCity, String regionTown, String regionVillage);
}
