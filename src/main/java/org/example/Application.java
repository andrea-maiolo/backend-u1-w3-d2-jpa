package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;
import org.example.dao.EventoDao;
import org.example.entities.Evento;
import org.example.entities.TipoEvento;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

public class Application {
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u1w3d2gestioneeventi");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EventoDao edao = new EventoDao(em);

        Evento compleanno = new Evento("compleanno",
                LocalDate.of(2025, 04, 03), "descrizione di compleanno"
                , TipoEvento.PUBBLICO, 15);

        Evento meeting = new Evento("meeting",
                LocalDate.of(2025, 07, 23), "meeting di lavoro"
                , TipoEvento.PRIVATO, 6);

        //edao.save(compleanno);
//        Evento eventoFromDb = edao.getById(1);
//        System.out.println(eventoFromDb);

        // edao.delete(1);

        // edao.save(meeting);
//        try {
//            Event eventFromDB = edao.findById(2);
//            System.out.println(eventFromDB);
//
//        } catch (NotFoundException ex) {
//            System.out.println(ex.getMessage());
//        }


        Faker faker = new Faker(Locale.ITALY);
        Random random = new Random();

        Supplier<Evento> eventSupp = () -> {
            String sTitolo = faker.esports().game();
            int sNMP = random.nextInt(3, 20);
            String sDescri = faker.esports().event();
            TipoEvento sTipo = TipoEvento.PUBBLICO;
            //TipoEvento.values(random.nextInt(0,1));
            LocalDate sLd = LocalDate.of(random.nextInt(2020, 2025), random.nextInt(1, 12),
                    random.nextInt(1, 30));

            Evento eventSupplied = new Evento(sTitolo, sLd, sDescri, sTipo, sNMP);
            return eventSupplied;
        };

        for (int i = 0; i < 7; i++) {
            edao.save(eventSupp.get());
        }

        em.close();
        emf.close();

    }
}
