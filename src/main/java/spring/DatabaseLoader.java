package spring;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private FilmRepository filmRepository;

    @PostConstruct
    private void initDatabase() {
        // User #1: "root", with password "p2" and roles "USER" and "ADMIN"
        GrantedAuthority[] adminRoles = {
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN") };
        userRepository.save(new User("root", "p2", "root@root.com",Arrays.asList(adminRoles)));
        
        // Film #1: "Interstellar" with some attributes
        Film film = new Film();
        film.setTitle("Interstellar");
        film.setContent("https://www.youtube.com/embed/0vxOhd4qlnA?ecver=1");
        film.setYear("2014");
        filmRepository.save(film);
    }
}

