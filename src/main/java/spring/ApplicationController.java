package spring;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rest.client.FilmQuery;
import retrofit.RestAdapter;

@Controller
public class ApplicationController {
	@Autowired
	private FilmRepository repo;

	@Autowired
	private UserRepository repoUsers;

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/")
	public ModelAndView processIndex() {
		return new ModelAndView("index");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/adminFilms")
	public ModelAndView adminFilms() {
		return new ModelAndView("adminFilms");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/addFilm")
	public ModelAndView addFilm() {
		return new ModelAndView("addFilm");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/processFilmForm")
	public ModelAndView processFilmForm(@RequestParam String title, @RequestParam String content) {
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://www.omdbapi.com").build();
		FilmQuery service = adapter.create(FilmQuery.class);
		Film film = service.getFilm(title);
		if(film.getResponse() == false){
			System.out.println("Film not found");
		}
		film.setContent(content);
		repo.save(film);
		System.out.println("From processFilmForm: " + repo.findAll());
		return new ModelAndView("redirect:/adminFilms");
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/searchFilm")
	public ModelAndView searchFilm() {
		return new ModelAndView("searchFilm");
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/searchFilmForm")
	public ModelAndView searchFilmForm(@RequestParam String title) {
		System.out.println("From searchFilmForm: " + repo.findByTitle(title));
		return new ModelAndView("seeFilms").addObject("film", repo.findByTitle(title));
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/editFilmForm")
	public ModelAndView editFilmForm(@RequestParam String currentTitle, @RequestParam String newTitle,
			@RequestParam String newContent) {
		Film currentFilm = repo.findByTitle(currentTitle);
		System.out.println("Current film before: " + currentFilm);
		currentFilm.setTitle(newTitle);
		currentFilm.setContent(newContent);
		repo.save(currentFilm);
		System.out.println("Current film after: " + currentFilm);
		return new ModelAndView("redirect:/adminFilms");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/editFilm")
	public ModelAndView editFilm() {
		return new ModelAndView("editFilm");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/deleteFilmForm")
	public ModelAndView deleteFilmForm(@RequestParam String currentTitle) {
		Film currentFilm = repo.findByTitle(currentTitle);
		System.out.println("Current film before: " + currentFilm);
		repo.delete(currentFilm);
		return new ModelAndView("redirect:/adminFilms");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/deleteFilm")
	public ModelAndView deleteFilm() {
		return new ModelAndView("deleteFilm");
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/seeFilms")
	public ModelAndView seeFilms(@RequestParam String title) {
		System.out.println("From seeFilms: " + repo.findByTitle(title));
		Film film = repo.findByTitle(title);
		return new ModelAndView("seeFilms").addObject("film", film);
	}

	/* Security */
	@RequestMapping("/login")
	public ModelAndView processLogin() {
		return new ModelAndView("login");
	}

	/* Users */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/adminUsers")
	public ModelAndView adminUsers() {
		return new ModelAndView("adminUsers");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/addUser")
	public ModelAndView addUser() {
		return new ModelAndView("addUser");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/processUserForm")
	public ModelAndView processUserForm(@RequestParam String user, @RequestParam String password,
			@RequestParam String email) {
		GrantedAuthority[] userRoles = { new SimpleGrantedAuthority("ROLE_USER") };
		repoUsers.save(new User(user, password, email, Arrays.asList(userRoles)));
		System.out.println("From processUserForm: " + repoUsers.findAll());
		return new ModelAndView("redirect:/adminUsers");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/editUserForm")
	public ModelAndView editUserForm(@RequestParam String currentName, @RequestParam String newName,
			@RequestParam String newPassword, @RequestParam String newEmail) {
		User currentUser = repoUsers.findByUser(currentName);
		System.out.println("Current user before: " + currentUser.getUser() + ", " + currentUser.getPasswordHash() + ", "
				+ currentUser.getEmail());
		currentUser.setUser(newName);
		currentUser.setPasswordHash(newPassword);
		currentUser.setEmail(newEmail);
		System.out.println("Current user after: " + currentUser.getUser() + ", " + currentUser.getPasswordHash() + ", "
				+ currentUser.getEmail());
		repoUsers.save(currentUser);
		return new ModelAndView("redirect:/adminUsers");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/editUser")
	public ModelAndView editUser() {
		return new ModelAndView("editUser");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/deleteUserForm")
	public ModelAndView deleteUserForm(@RequestParam String currentName) {
		User currentFilm = repoUsers.findByUser(currentName);
		System.out.println("Current user before: " + currentName);
		repoUsers.delete(currentFilm);
		System.out.println("From deleteUserForm: " + repoUsers.findAll());
		return new ModelAndView("redirect:/adminUsers");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/deleteUser")
	public ModelAndView deleteUsers() {
		return new ModelAndView("deleteUser");
	}
}