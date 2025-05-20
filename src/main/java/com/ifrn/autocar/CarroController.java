package com.ifrn.autocar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarroController {

    @Autowired
    CarroRepository carroRepository;

    @GetMapping("/carro/{id}")
    public ResponseEntity<Carro> getById(@PathVariable int id) {
        Optional<Carro> carroOptional = carroRepository.findById(id);
        if (carroOptional.isPresent()) {
            return ResponseEntity.ok(carroOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/carros")
    public List<Carro> getAll() {
        return carroRepository.findAll();
    }

    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public Carro salvarCarro(@RequestBody Carro carro) {
        return carroRepository.save(carro);
    }

    @DeleteMapping("/carro/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCarro(@PathVariable int id) {
        carroRepository.deleteById(id);
    }

    @PutMapping("/carro/{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable int id, @RequestBody Carro carroAtualizado) {
        Optional<Carro> carroOptional = carroRepository.findById(id);
        if (carroOptional.isPresent()) {
            Carro carroExistente = carroOptional.get();
            carroExistente.setModelo(carroAtualizado.getModelo());
            carroExistente.setMarca(carroAtualizado.getMarca());
            carroExistente.setAno(carroAtualizado.getAno());

            Carro carroSalvo = carroRepository.save(carroExistente);
            return ResponseEntity.ok(carroSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/rabbitmq")
    public String enviarMensagem(String mensagem) {
        System.out.println("Mensagem: " + mensagem);
        RabbitMQ.escreverMensagem(mensagem);

        return "Carro salvo com sucesso";
    }
}