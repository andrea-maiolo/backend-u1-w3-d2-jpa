package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import org.example.entities.Evento;

public class EventoDao {
    private EntityManager em;

    public EventoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Evento event) {
        //create trasaction
        EntityTransaction transaction = em.getTransaction();

        //open transactio
        transaction.begin();

        //add obj to Persistence context
        em.persist(event);

        //add to actual database
        transaction.commit();
    }

    public Evento getById(long id) {
        Evento eventoTrovato = em.find(Evento.class, id);
        if (eventoTrovato == null) {
            throw new EntityNotFoundException("evento non trovato");
        }

        return eventoTrovato;
    }


    public void delete(long id) {
        Evento eventoTrovato = this.getById(id);
        em.getTransaction().begin();
        em.remove(eventoTrovato);
        em.getTransaction().commit();
    }
}
