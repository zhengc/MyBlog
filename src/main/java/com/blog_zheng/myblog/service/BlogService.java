package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    /**
     * Save the passed in blog into database
     *
     * @param blog the blog that is supposed to be saved
     * @return the saved blog
     */
    Blog saveBlog(Blog blog);

    /**
     * return the blog associated with the passed in id
     *
     * @param id the id of the blog that we want to get
     * @return the blog that is associated with the given id
     */
    Blog getBlog(Long id);

    /**
     * return a page object that contains the result of querying all the blogs
     *
     * @param pageable pageable object
     * @param blog     a blog object that has some required fields' values to filter out some
     *                 blogs
     * @return a page object that contains a collection of filtered blogs
     */
    Page<Blog> blogList(Pageable pageable, Blog blog);

    /**
     * update the blog based on the given id with the passed in blog,
     * the initial id of the blog will not be changed.
     *
     * @param id   id of the blog that is about to be updated
     * @param blog the source of update
     * @return the updated blog
     */
    Blog updateBlog(Long id, Blog blog);

    /**
     * delete the blog that is associated with the given id
     *
     * @param id id of the blog that we want to delete
     */
    void deleteBlog(Long id);

    /**
     * @return the number of blogs in the database
     */
    long numBlogs();

    /**
     * return a list of latest updated blogs
     *
     * @param size the number of blogs to be returned
     * @return a list of latest updated blogs
     */
    List<Blog> getLatestBlogs(Integer size);
}
