package com.ifrn.autocar;

import com.ifrn.autocar.models.Carro;
import com.ifrn.autocar.repositories.CarroRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class AutoCarApplicationTests {

    @Autowired
    CarroRepository carroRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testSaveCarro(){
        Carro carro = new Carro();
        carro.setMarca("Porsche");
        carro.setModelo("Cayman");
        carro.setAno(2025);
        carroRepository.save(carro);

        String expected = "Porsche";
        String actual = carro.getMarca();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testDeleteCarro(){
        Carro carro = new Carro();
        carro.setMarca("Chevrolet");
        carro.setModelo("Onix");
        carro.setAno(2022);
        carro = carroRepository.save(carro);
        System.out.println("carro criado: " + carro.getMarca());

        int expected = 1;
        int actual = carroRepository.findAll().size();
        Assertions.assertEquals(expected, actual);

        Long id = carro.getId();

        carroRepository.deleteById(id);
        expected = 0;
        actual = carroRepository.findAll().size();
        Assertions.assertEquals(expected, actual);

        if (carroRepository.existsById(id)) {
            System.out.println("carro não foi deletado");
        } else {
            System.out.println("carro foi deletado");
        }

    }

    @Test
    void testPutCarro(){
        Carro carro = new Carro();
        carro.setMarca("Porsche");
        carro.setModelo("Cayman");
        carro.setAno(2025);
        carroRepository.save(carro);
        System.out.println("carro criado: " + carro.getModelo());

        String expected = "Boxster";

        carro.setModelo("Boxster");
        carroRepository.save(carro);
        String actual = carro.getModelo();
        Assertions.assertEquals(expected, actual);
        System.out.println("carro alterado: " + carro.getModelo());
    }

    @Test
    void testFindAllCarros(){
        Carro carro = new Carro();
        carro.setMarca("Chevrolet");
        carro.setModelo("Onix");
        carro.setAno(2022);
        carro = carroRepository.save(carro);
        System.out.println("carro criado: " + carro.getMarca());

        Carro carro2 = new Carro();
        carro2.setMarca("Porsche");
        carro2.setModelo("Boxster");
        carro2.setAno(2022);
        carro2 = carroRepository.save(carro2);
        System.out.println("carro criado: " + carro2.getMarca());

        Carro carro3 = new Carro();
        carro3.setMarca("volkswagen");
        carro3.setModelo("Fusca");
        carro3.setAno(1997);
        carro3 = carroRepository.save(carro3);
        System.out.println("carro criado: " + carro3.getMarca());

        int expected = 3;
        int actual = carroRepository.findAll().size();
        Assertions.assertEquals(expected, actual);

        System.out.println("carros criados: " + carroRepository.findAll());
    }

    @Test
    void testFindCarroById(){
        Carro carro3 = new Carro();
        carro3.setMarca("volkswagen");
        carro3.setModelo("Fusca");
        carro3.setAno(1997);
        carro3 = carroRepository.save(carro3);
        System.out.println("carro criado: " + carro3.getMarca());

        String expected = "Fusca";
        if( carroRepository.findById(carro3.getId()).isPresent() ){
            String actual = carroRepository.findById(carro3.getId()).get().getModelo();
            Assertions.assertEquals(expected, actual);
        }
    }

    @AfterEach
    void tearDown(){
        carroRepository.deleteAll();
    }

}
