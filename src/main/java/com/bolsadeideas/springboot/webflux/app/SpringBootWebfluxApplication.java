package com.bolsadeideas.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webflux.app.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	@Autowired
	private ProductoDao dao;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("productos").subscribe();

		Flux.just(new Producto("nombre1", 111.11), new Producto("nombre2", 112.11), new Producto("nombre3", 113.11),
				new Producto("nombre4", 114.11), new Producto("nombre5", 115.11), new Producto("nombre6", 116.11),
				new Producto("nombre7", 117.11), new Producto("nombre8", 118.11), new Producto("nombre9", 119.11))
				.flatMap(producto -> {
					producto.setCreateAt(new Date());
					return dao.save(producto);
				}).subscribe(producto -> log.info("insert: " + producto.getId() + " " + producto.getNombre()));

	}

}
