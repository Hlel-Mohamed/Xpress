package com.biblio.xpress.repository;

import com.biblio.xpress.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonRepository<T extends Person> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
