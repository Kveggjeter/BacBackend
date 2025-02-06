package com.bac.bacbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BIG BOSS SOURCES FOR THE CODE:
 * LETTUCE:
 * 			* https://www.youtube.com/watch?v=mv4Q7c3Psc0&t=384s&ab_channel=DiegoPacheco
 * 				- Article, Controller, Application, LettuceConfig, ArticleRepository
 * 			* https://www.youtube.com/watch?v=H_DFM_CCEGI&ab_channel=JOYofLIFE
 * 				- Controller, Application, pom.xml, application.properties
 * REDIS WITH DOCKER:
 * 			* https://www.youtube.com/watch?v=P0sMMVdoG-U&ab_channel=StackOverflowPlusPlus
 * 			*
 * REDIS:
 * 		* https://www.youtube.com/watch?v=DOIWQddRD5M&t=570s&ab_channel=Fireship
 * MULTITHREADING:
 * 		* https://www.youtube.com/watch?v=r_MbozD32eo&t=511s&ab_channel=CodingwithJohn
 * 			- 	NewsMinerMain, Scraper
 * 		* https://www.youtube.com/watch?v=J09TLPgwd0Y&t=219s&ab_channel=BroCode
 * 			- NewsMinerMain, Scraper
 * CRAWLING/SCRAPING:
 * 		* https://www.youtube.com/watch?v=vxk6YPRVg_o&t=600s&ab_channel=ForrestKnight
 * 			- WebCrawler
 *
 *
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

/**
 * @Bean
 *     CommandLineRunner runScraper(NewsMinerMain newsMiner) {
 *         return args -> {
 *             // Kalles når Spring Boot har startet
 *             newsMiner.start();
 *         };
 *     }
 */

// eller?

/**
@SpringBootApplication
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

@Service
public class NewsMinerMain {

	// Som over

	@Scheduled(fixedRate = 30_000) // hver 30 sekunder, bare som demo
	public void scheduledScrape() {
		start(); // Start samme logikk
	}
}

**/