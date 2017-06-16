/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import model.Battle;
import model.Pokemon;
import model.Trainer;

/**
 *
 * @author USER
 */
@Stateless
public class StukemonEJB {
    @PersistenceUnit
    EntityManagerFactory emf;

       // Insertamos camión 
    public boolean insertarTrainer(Trainer t) {
        if (!existeTrainer(t)) {
            EntityManager em = emf.createEntityManager();
            em.persist(t);
            em.close();
            return true;
        }
        return false;
    }
       public boolean insertarPokemon(Pokemon p) {
        if (!existePokemon(p)) {
            EntityManager em = emf.createEntityManager();
            em.persist(p);
            em.close();
            return true;
        }
        return false;
    }
       public boolean insertarBatalla(Battle b){
            EntityManager em = emf.createEntityManager();
            em.persist(b);
            em.close();
            return true;
       }
        public List<Trainer> selectAllTrainers() {
        return emf.createEntityManager().createNamedQuery("Trainer.findAll").getResultList();
    }
        public List<Trainer> selectAllTrainersOrder() {
        return emf.createEntityManager().createNamedQuery("Trainer.findAllOrder").getResultList();
    }
        public List<Pokemon> selectAllPokemons() {
        return emf.createEntityManager().createNamedQuery("Pokemon.findAll").getResultList();
    }
        public List<Pokemon> selectAllPokemonsOrder() {
        return emf.createEntityManager().createNamedQuery("Pokemon.findAllOrder").getResultList();
    }
      public boolean existePokemon(Pokemon p) {
        return (emf.createEntityManager().find(Trainer.class, p.getName())) != null;
    }

    public boolean existeTrainer(Trainer t) {
        return (emf.createEntityManager().find(Trainer.class, t.getName())) != null;
    }
    
    public Trainer getTrainerByName(String name) {
        return emf.createEntityManager().find(Trainer.class, name);
    }
    public Pokemon getPokemonByName(String name) {
        return emf.createEntityManager().find(Pokemon.class, name);
    }
       public boolean modificarPokemon(Pokemon p) {
        EntityManager em = emf.createEntityManager();
        Pokemon pokemon = em.find(Pokemon.class, p.getName());
        boolean ok = false;
        if (pokemon != null) {
            pokemon.setLife(p.getLife());
            em.persist(pokemon);
            ok = true;
        }
        em.close();
        return ok;
    }
        public boolean modificarVidaP(Pokemon p) {
        EntityManager em = emf.createEntityManager();
        Pokemon pokemon = em.find(Pokemon.class, p.getName());
        boolean ok = false;
        if (pokemon != null) {
            pokemon.setLife(p.getLife());
            em.persist(pokemon);
            ok = true;
        }
        em.close();
        return ok;
    }      
        public boolean modificarLevel(Pokemon p) {
        EntityManager em = emf.createEntityManager();
        Pokemon pokemon = em.find(Pokemon.class, p.getName());
        boolean ok = false;
        if (pokemon != null) {
            pokemon.setLevel(p.getLevel());
            em.persist(pokemon);
            ok = true;
        }
        em.close();
        return ok;
    }
       public boolean modificarPuntos(Trainer t) {
        EntityManager em = emf.createEntityManager();
        Trainer trainer = em.find(Trainer.class, t.getName());
        boolean ok = false;
        if (trainer != null) {
            trainer.setPoints(t.getPoints());
            em.persist(trainer);
            ok = true;
        }
        em.close();
        return ok;
    }
       public boolean modificarPociones(Trainer t) {
        EntityManager em = emf.createEntityManager();
        Trainer trainer = em.find(Trainer.class, t.getName());
        boolean ok = false;
        if (trainer != null) {
            trainer.setPotions(t.getPotions());
            em.persist(trainer);
            ok = true;
        }
        em.close();
        return ok;
    }

        public boolean borrarPokemon(Pokemon p) {
        EntityManager em = emf.createEntityManager();
        Pokemon pokemon = em.find(Pokemon.class, p.getName());
        boolean ok = false;
        if (p != null) {
            em.remove(pokemon);
            ok = true;
        } 
        em.close();
        return ok;
        }   
        
        public boolean addVida(Pokemon p){
            EntityManager em = emf.createEntityManager();
            Pokemon pokemon = em.find(Pokemon.class, p.getName());
            boolean ok = false;
            if(p != null){
                pokemon.setLife(pokemon.getLife() + 50);
                em.persist(pokemon);
                ok = true;
            }
            em.close();
            return ok;
        }
}

