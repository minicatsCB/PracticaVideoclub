package rest.client;

import retrofit.RestAdapter;
import spring.Film;

public class FilmMain {
	 public static void main(String[] args) throws Exception {
		 RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://www.omdbapi.com").build();
		    FilmQuery service = adapter.create(FilmQuery.class);
		    
		    String filmName = "Star Trek";
		    Film film = service.getFilm(filmName);
		    System.out.println(film);
	}
}
    
