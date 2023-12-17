package com.kth.mssage.repository.local;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_city")
    private String regionCity;

    @Column(name = "region_town")
    private String regionTown;

    @Column(name = "region_village")
    private String regionVillage;

    @Column(name = "nx")
    private  String nx;

    @Column(name = "ny")
    private  String ny;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local that = (Local) o;

        String thisConcatenated = regionCity + " " + regionTown + " " + regionVillage;
        String thatConcatenated = that.regionCity + " " + that.regionTown + " " + that.regionVillage;

        return thisConcatenated.equals(thatConcatenated);
    }

    @Override
    public int hashCode() {
        return (regionCity + " " + regionTown + " " + regionVillage).hashCode();
    }
}
