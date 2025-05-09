package com.ifrn.autocar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarroController {

    @Autowired
    CarroRepository carroRepository;

    @GetMapping("/carro")
    public String hello(){
        Carro carro = carroRepository.findById(1);

        return carro.getModelo();
    }

    @PostMapping("/salvar")
    public void salvarCarro(@RequestBody Carro carro){
        carroRepository.save(carro);
    }
}
