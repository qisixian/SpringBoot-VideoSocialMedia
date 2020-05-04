package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.entities.EditorSuggestPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorSuggestPostRepository extends MongoRepository<EditorSuggestPost, String> {
}
