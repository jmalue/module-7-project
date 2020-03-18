package org.wcci.reviews;

import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {

	Album findByName(String albumName);

}
