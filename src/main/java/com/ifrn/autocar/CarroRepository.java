package com.ifrn.autocar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public class CarroRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Carro findById(int id) {
        return entityManager.find(Carro.class, id);
    }

    @Transactional
    public void save(Carro carro) {
        entityManager.persist(carro);
    }
}
