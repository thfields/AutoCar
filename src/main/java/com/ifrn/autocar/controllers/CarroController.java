package com.ifrn.autocar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifrn.autocar.repositories.CarroRepository;
import com.ifrn.autocar.comunication.RabbitMQ;
import com.ifrn.autocar.models.Carro;
import com.ifrn.autocar.models.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CarroController {

    @Autowired
    CarroRepository carroRepository;

    @Autowired
    RabbitMQ rabbitMQ;

    @GetMapping("/carro/{id}")
    public ResponseEntity<Carro> getById(@PathVariable Long id) {
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
    public void deletarCarro(@PathVariable Long id) {
        carroRepository.deleteById(id);
    }

    @PutMapping("/carro/{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable Long id, @RequestBody Carro carroAtualizado) {
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
    public String enviarMensagem(@RequestBody String mensagem) {
        System.out.println("Mensagem: " + mensagem);
        rabbitMQ.escreverMensagem(mensagem);  // Agora usando a instância
        return "Mensagem enviada com sucesso";
    }

    @GetMapping("/rabbitmq")
    public void lerMensagem() throws IOException {
        String resposta = rabbitMQ.lerMensagem();  // Agora usando a instância
        ObjectMapper mapper = new ObjectMapper();
        Carro a = mapper.readValue(resposta, Carro.class);
        carroRepository.save(a);
        System.out.println("Resposta: " + resposta);
    }

    @GetMapping("/logs")
    public List<Log> getLogs() {
        return rabbitMQ.getLogRepository().findAll();
    }
}