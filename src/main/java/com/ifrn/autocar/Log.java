package com.ifrn.autocar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String operacao; // "ENVIO" ou "RECEBIMENTO"
    private String mensagem;
    private LocalDateTime dataHora;
    private String fila;

    public Log() {
        this.dataHora = LocalDateTime.now();
    }

    public Log(String operacao, String mensagem, String fila) {
        this();
        this.operacao = operacao;
        this.mensagem = mensagem;
        this.fila = fila;
    }
}