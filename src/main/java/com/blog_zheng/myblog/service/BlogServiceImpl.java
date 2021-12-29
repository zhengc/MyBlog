package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.dao.BlogRepository;
import com.blog_zheng.myblog.entity.Blog;
import com.blog_zheng.myblog.entity.Category;
import com.blog_zheng.myblog.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog saveBlog(Blog blog) {
        blog.setPublishDate(new Date());
        blog.setViewCount(0);
        blog.setUpdateDate(new Date());
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getById(id);
    }

    @Override
    public Page<Blog> blogList(Pageable pageable, Blog blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null && !blog.getTitle().equals("")) {
                    // like() is to do partial matching
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                Category c = blog.getCategory();
                if (c != null && c.getCategoryID() != null) {
                    // equal is to do exact matching
                    predicates.add(criteriaBuilder.equal(root.<Category>get("category").get("categoryID"), blog.getCategory().getCategoryID()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return query.getRestriction();
            }
        }, pageable);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = getBlog(id);
        if (b == null) {
            return null;
        }
        // we only want to copy the non-null values,
        // so we need to skip the null fields
        String[] namesToBeIgnored = MyBeanUtils.getAllNullProperties(blog);
        BeanUtils.copyProperties(blog, b, namesToBeIgnored);
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public long numBlogs() {
        return blogRepository.count();
    }

    @Override
    public List<Blog> getLatestBlogs(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateDate");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.getLatestBlogs(pageable);
    }
}
