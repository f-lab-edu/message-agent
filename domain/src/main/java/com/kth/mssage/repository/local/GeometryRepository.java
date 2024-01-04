package com.kth.mssage.repository.local;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeometryRepository extends JpaRepository<Geometry, Long> {

    Geometry findByRegionCityAndRegionTownAndRegionVillage(String regionCity, String regionTown, String regionVillage);
}
