package com.digitalinnovationone.herosapi;

import com.digitalinnovationone.herosapi.constrans.HeroesConstant;
import com.digitalinnovationone.herosapi.document.Heroes;
import com.digitalinnovationone.herosapi.repository.HeroesRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@SpringBootTest
class HerosapiApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	HeroesRepository heroesRepository;

	@Test
	public void getOneHeroNotFound(){
		webTestClient.get().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"),"99999")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	public void createOneHero(){
		Heroes heroes = new Heroes("99999", "Capitão Cuéca", "DC Zueiras", 3);

		webTestClient.post().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL)
				.accept(MediaType.APPLICATION_JSON)
				.syncBody(heroes)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody();
	}

	@Test
	public void getOneHeroById(){
		webTestClient.get().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"),"99999")
				.exchange()
				.expectStatus().isOk()
				.expectBody();
	}

	@Test
	public void deleteOneHeroById(){
		webTestClient.delete().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"),"99999")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNoContent()
				.expectBody(Void.class);
	}

}