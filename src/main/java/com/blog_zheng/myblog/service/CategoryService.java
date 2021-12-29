package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    /**
     * Create a new category and return the new category object
     *
     * @param category the category that we want to create. It MUST NOT be null.
     * @return the category that we just created
     */
    Category saveCategory(Category category);

    /**
     * get a category based on the passed key id
     *
     * @param id the id of the category that we want to query for
     * @return a reference to the entity with the given id
     */
    Category getCategory(Long id);

    /**
     * return a list of existing categories
     *
     * @param pageable it contains requested page information
     * @return a list of existing categories
     */
    Page<Category> categoryList(Pageable pageable);

    /**
     * update the category that has the given id and assign the value
     * of the passed category object to the old category, the id
     * will not be modified.
     *
     * @param id       the id of the category that we want to update
     * @param category the update source
     * @return the modified category
     */
    Category updateCategory(Long id, Category category);

    /**
     * delete a category from the database based on the provided category id
     *
     * @param id the id of the category that we want to remove
     */
    void deleteCategory(Long id);

    /**
     * return the category with the passed name if it exists in the database
     *
     * @param name the name of a category
     * @return some category with the passed name
     */
    Category getCategoryByName(String name);

    /**
     * @return returns a list of existing categories
     */
    List<Category> getCategoryList();

    /**
     * return the top k categories sorted by the size of their corresponding list of blogs
     *
     * @param k the number of categories you want to show
     * @return the top k categories
     */
    List<Category> getTopKCategory(Integer k);
}
