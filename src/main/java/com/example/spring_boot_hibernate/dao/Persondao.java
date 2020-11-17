package com.example.spring_boot_hibernate.dao;

import com.example.spring_boot_hibernate.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class Persondao {

    @Autowired
    private SessionFactory sessionFactory;

    public void savePerson(Person p){
        Transaction tx = getSession().beginTransaction();
        getSession().persist(p);
        tx.commit();
    }
    //retrieve all the rows of “Person” from the database:
    public List<Person> getPersons(){
        /*
         1. Get Session from the SessionFactory object
         2. Create an instance of CriteriaBuilder by calling the getCriteriaBuilder() method
         */
        CriteriaBuilder criteriaBuilder =getSession().getCriteriaBuilder();
        // 3. Create an instance of CriteriaQuery by calling the CriteriaBuilder createQuery() method
        CriteriaQuery<Person> cr=criteriaBuilder.createQuery(Person.class);
        Root<Person> root= cr.from(Person.class);
        cr.select(root); // we can add where method
        //4. Create an instance of Query by calling the Session createQuery() method
        Query<Person>query =getSession().createQuery(cr);
        //5. Call the getResultList() method of the query object which gives us the results
        return query.getResultList();
    }

    public void udpatePerson(Person p, int id){

        CriteriaBuilder cb =getSession().getCriteriaBuilder();
        CriteriaUpdate<Person> criteriaUpdate = cb.createCriteriaUpdate(Person.class);
        Root<Person> root = criteriaUpdate.from(Person.class);

        criteriaUpdate.set("name",p.getName());
        criteriaUpdate.set("dob",p.getDob());

        criteriaUpdate.where(cb.equal(root.get("id"), id));
        Transaction transaction = getSession().beginTransaction();
        getSession().createQuery(criteriaUpdate).executeUpdate();
        transaction.commit();
       /* Query<Person> query =getSession().createQuery(criteriaUpdate);
        Person singleResult = query.getSingleResult();
        return singleResult;*/
    }

    public void deletePerson(int id){
        CriteriaBuilder cb =getSession().getCriteriaBuilder();
        CriteriaDelete<Person> criteriaDelete = cb.createCriteriaDelete(Person.class);
        Root<Person> root = criteriaDelete.from(Person.class);
        criteriaDelete.where(cb.equal(root.get("id"), id));
        Transaction transaction = getSession().beginTransaction();
        getSession().createQuery(criteriaDelete).executeUpdate();
        transaction.commit();
    }

    private Session getSession(){
        Session session=sessionFactory.getCurrentSession();
        if(session==null) session=sessionFactory.openSession();
        return session;
    }
}
