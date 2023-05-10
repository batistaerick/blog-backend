package com.erick.blog.repositories;

import com.erick.blog.domains.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
