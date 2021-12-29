package com.blog_zheng.myblog.dao;

import com.blog_zheng.myblog.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // use the naming convention to query database
    Category findByName(String name);

    @Query("select c from Category c")
    List<Category> findTopK(Pageable pageable);
}
