package rest.client;


import retrofit.http.GET;
import retrofit.http.Query;
import spring.Film;

public interface FilmQuery {
	@GET("/")
	Film getFilm(@Query("t") String t);
}
