package com.indra.grupo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

@Dependent
public class BaseDAO<T> {


	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("indra");
	
    private EntityManager entityManager = factory.createEntityManager();
	
	public EntityManager getEntityManager(){
		
		return entityManager;
		
	}
	
	public void persistir(T obj) {
		
		getEntityManager().getTransaction().begin();
		
	    getEntityManager().persist(obj);
	    
	    getEntityManager().getTransaction().commit();
	    
	  }
	 
	  public void remover(T obj) {
		  
			entityManager.getTransaction().begin();
			
			Object merge = entityManager.merge(obj);

			entityManager.remove(merge);

			entityManager.getTransaction().commit();
			
	  }
	 
	  public void atualizar(T obj) {
		  
		  entityManager.getTransaction().begin();

			entityManager.merge(obj);

			entityManager.getTransaction().commit();
			
	  }
	 
	 
	  public T buscarPorId(Class<T> classe,Long id) {
	    return getEntityManager().find(classe,id);
	  }
	  
	  public void setParametrosQuery(Query query, Map<String, Object> params){
		  
		  Set<Entry<String, Object>> data = params.entrySet();

		  Iterator iterator = data.iterator();
		  
		  while(iterator.hasNext()){
			  
			  Entry entry = (Entry) iterator.next();
			  
			  query.setParameter((String) entry.getKey(), entry.getValue());
			  
		  }
		  
	  }
	  

}
