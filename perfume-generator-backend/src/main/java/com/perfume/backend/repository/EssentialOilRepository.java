package com.perfume.backend.repository;

import com.perfume.backend.domain.model.EssentialOil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository JPA pour l'accès aux huiles essentielles.
 */
public interface EssentialOilRepository extends JpaRepository<EssentialOil, Long> {

    /**
     * Recherche une huile essentielle par son nom (insensible à la casse).
     *
     * @param name nom de l'huile
     * @return Optional contenant l'huile si trouvée
     */
    Optional<EssentialOil> findByNameIgnoreCase(String name);
}
