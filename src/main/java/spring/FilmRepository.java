package spring;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, Long>{
	Film findByTitle(String title);
	Film findById(Long id);
	
	@Query(value = "SELECT * FROM FILM", nativeQuery = true)
	List<Film> selectAll();
}
