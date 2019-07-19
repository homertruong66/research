package com.example.demo.consumer.repository;

import com.example.demo.consumer.domain.Primary;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by hungnguyenv3 on 10/16/15.
 */
public interface PrimaryRepository extends CrudRepository<Primary, Long> {
}
