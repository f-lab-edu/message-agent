package com.kth.message.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Geometry {

	@Id
	private Long id;

	@Column(name = "region_city")
	private String regionCity;

	@Column(name = "region_town")
	private String regionTown;

	@Column(name = "region_village")
	private String regionVillage;

	@Column(name = "nx")
	private String nx;

	@Column(name = "ny")
	private String ny;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Geometry that = (Geometry) o;

		return ( (null == regionCity && null == that.regionCity) || (null != regionCity && null != that.regionCity && regionCity.equals(that.regionCity)) )
			&& ( (null == regionTown && null == that.regionTown) || (null != regionTown && null != that.regionTown && regionTown.equals(that.regionTown)) )
			&& ( (null == regionVillage && null == that.regionVillage) || (null != regionVillage && null != that.regionVillage && regionVillage.equals(that.regionVillage)) );
	}

	@Override
	public int hashCode() {
		return Objects.hash(regionCity, regionTown, regionVillage);
	}
}
