package org.wcci.reviews;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReviewPopulator implements CommandLineRunner {

	@Resource
	private ReviewRepository reviewRepo;

	@Resource
	private AlbumRepository albumRepo;

	@Resource
	private TrackRepository trackRepo;

	@Override
	public void run(String... args) throws Exception {

		Album abbeyRoad = new Album("Abbey Road");
		abbeyRoad = albumRepo.save(abbeyRoad);
		Album rubberSoul = new Album("Rubber Soul");
		rubberSoul = albumRepo.save(rubberSoul);
		Album letItBe = new Album("Let It Be");
		letItBe = albumRepo.save(letItBe);

		Review abbeyRoad1 = new Review("11th studio album by the English rock band the Beatles", "16 tracks",
				abbeyRoad);
		abbeyRoad1 = reviewRepo.save(abbeyRoad1);

		trackRepo.save(new Track("Come Together", abbeyRoad1));
		trackRepo.save(new Track("Something", abbeyRoad1));

	}

}
