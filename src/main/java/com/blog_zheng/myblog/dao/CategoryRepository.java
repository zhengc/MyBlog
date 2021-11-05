package com.blog_zheng.myblog.dao;

import com.blog_zheng.myblog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
