package ru.geekbrains.supershop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.Review;
import ru.geekbrains.supershop.persistence.entities.Shopuser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<List<Review>> findByProduct(Product product);
    Optional<List<Review>> findByShopuser(Shopuser shopuser);
    Optional<Review> findById(UUID id);

    //На вариант с Optional идея ругается
    List<Review> findAll();

    @Modifying(clearAutomatically = true)
    @Query("update Review r set r.visible =:visible where r.id =:uuid")
    void updateVisible(@Param("uuid") UUID uuid, @Param("visible") boolean visible);

}