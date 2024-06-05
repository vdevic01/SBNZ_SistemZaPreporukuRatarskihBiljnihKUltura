package com.ftn.sbnz.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.service.model.Parcel;
import com.ftn.sbnz.service.model.User;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    // @Query(value = "SELECT parcel FROM Parcel parcel WHERE parcel.owner.id = ?1")
    List<Parcel> findParcelsByOwner(User owner);
}
