package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.customExceptions.NotFoundException;
import com.blog_zheng.myblog.dao.TagRepository;
import com.blog_zheng.myblog.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getById(id);
    }

    @Override
    public Page<Tag> tagList(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = getTag(id);
        if (t == null) {
            throw new NotFoundException("Sorry, no such tag with the given id exists.");
        }
        t.setName(tag.getName());
        return saveTag(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> getTagList() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getTagList(String input) {
        List<Long> list = convert(input);
        if (list == null) {
            return null;
        }
        return tagRepository.findAllById(list);
    }

    @Override
    public List<Tag> getTopKTags(Integer k) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, k, sort);
        return tagRepository.findTopK(pageable);
    }

    /*
    return a list of IDs. return null if the input string is null or empty
     */
    private List<Long> convert(String input) {
        if (input == null || input.equals("")) {
            return null;
        }
        String[] ids = input.split(",");
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            try {
                result.add(new Long(ids[i]));
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }
}
