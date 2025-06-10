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

    @AfterEach
    void tearDown(){
        carroRepository.deleteAll();
    }

}
