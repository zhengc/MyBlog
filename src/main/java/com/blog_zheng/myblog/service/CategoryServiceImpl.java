package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.customExceptions.NotFoundException;
import com.blog_zheng.myblog.dao.CategoryRepository;
import com.blog_zheng.myblog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public Page<Category> categoryList(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category c = getCategory(id);
        if (c == null) {
            throw new NotFoundException("The category with the given id does not exist.");
        }
        c.setName(category.getName());
        return categoryRepository.save(c);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getTopKCategory(Integer k) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, k, sort);
        return categoryRepository.findTopK(pageable);
    }
}
