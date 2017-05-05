package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ApplicationController{
	@Autowired
	private FilmRepository repo;
	
	@RequestMapping("/")
	public ModelAndView processIndex(){
		return new ModelAndView("index");
	}
	
	@RequestMapping("/adminFilms")
	public ModelAndView adminFilms(){
		return new ModelAndView("adminFilms");
	}
	
	@RequestMapping("/addFilm")
	public ModelAndView addFilm(){
		return new ModelAndView("addFilm");
	}
	
	@RequestMapping("/processFilmForm")
	public ModelAndView processFilmForm(@RequestParam String title, @RequestParam String year){
		Film film1 = new Film();
		film1.setTitle("Up");
		film1.setYear("2010");
		Film film2 = new Film();
		film2.setTitle(title);
		film2.setYear(year);
		repo.save(film1);
		repo.save(film2);
		System.out.println("From processFilmForm: " + repo.findAll());
		return new ModelAndView("redirect:/adminFilms");
	}
	
	@RequestMapping("/seeFilms")
	public ModelAndView seeFilms(){
		System.out.println("From seeFilms: " + repo.findAll());
		return new ModelAndView("seeFilms").addObject("films", repo.findAll());
	}
}