package spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ApplicationController{
	@Autowired
	private FilmRepository repo;
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/")
	public ModelAndView processIndex(){
		return new ModelAndView("index");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/adminFilms")
	public ModelAndView adminFilms(){
		return new ModelAndView("adminFilms");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/addFilm")
	public ModelAndView addFilm(){
		return new ModelAndView("addFilm");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/processFilmForm")
	public ModelAndView processFilmForm(@RequestParam String title, @RequestParam String content){
		Film film2 = new Film();
		film2.setTitle(title);
		film2.setContent(content);
		repo.save(film2);
		System.out.println("From processFilmForm: " + repo.findAll());
		return new ModelAndView("redirect:/adminFilms");
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/searchFilm")
	public ModelAndView searchFilm(){
		return new ModelAndView("searchFilm");
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/searchFilmForm")
	public ModelAndView searchFilmForm(@RequestParam String title, RedirectAttributes redirectAttributes){
		System.out.println("From searchFilmForm: " + repo.findByTitle(title));
		redirectAttributes.addAttribute("films" , repo.findByTitle(title));
		return new ModelAndView("redirect:/seeFilms");	// Temporal <--
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/editFilmForm")
	public ModelAndView editFilmForm(@RequestParam String currentTitle, @RequestParam String newTitle, @RequestParam String newContent){
		Film currentFilm = repo.findByTitle(currentTitle);
		System.out.println("Current film before: " + currentFilm);
		// Falta poner control de que sea null. Si es así, poner un mensaje por pantalla e intentar de nuevo
		currentFilm.setTitle(newTitle);
		currentFilm.setContent(newContent);
		repo.save(currentFilm);
		System.out.println("Current film after: " + currentFilm);
		return new ModelAndView("redirect:/adminFilms");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/editFilm")
	public ModelAndView editFilm(){
		return new ModelAndView("editFilm");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/deleteFilmForm")
	public ModelAndView deleteFilmForm(@RequestParam String currentTitle){
		Film currentFilm = repo.findByTitle(currentTitle);
		System.out.println("Current film before: " + currentFilm);
		// Falta poner control de que sea null. Si es así, poner un mensaje por pantalla e intentar de nuevo
		repo.delete(currentFilm);
		return new ModelAndView("redirect:/adminFilms");
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/deleteFilm")
	public ModelAndView deleteFilm(){
		return new ModelAndView("deleteFilm");
	}
	
	/*
	@RequestMapping("/searchFilmFormForEdit")
	public ModelAndView searchFilmFormForEdit(@ModelAttribute("title") final Object mapping1FormObject, final BindingResult mapping1BindingResult, final Model model, final RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("title" , mapping1FormObject);
		return new ModelAndView("redirect:/editFilm");
	}
	
	@RequestMapping("/editFilm")
	public ModelAndView editFilm(@ModelAttribute("title") final Object mapping1FormObject, final BindingResult mapping11BindingResult, final Model model){
		model.addAttribute("transformationTitle", mapping1FormObject);
		return new ModelAndView("editFilm");
	}*/
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping("/seeFilms")
	public ModelAndView seeFilms(){
		System.out.println("From seeFilms: " + repo.findAll());
		return new ModelAndView("seeFilms").addObject("films", repo.findAll());
	}
	
	
	/* Security */
	@RequestMapping("/login")
	public ModelAndView processLogin() {
		return new ModelAndView("login");
	}
}