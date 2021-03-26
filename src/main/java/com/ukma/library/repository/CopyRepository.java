package com.ukma.library.repository;

import com.ukma.library.model.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

	Optional<Copy> findFirstByBookIsbnAndIsAvailableTrue(String isbn);
}
