package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    /**
     * Create a new tag and return the new tag object
     *
     * @param tag the tag that we want to create. It MUST NOT be null.
     * @return the tag that we just created
     */
    Tag saveTag(Tag tag);

    /**
     * get a tag based on the passed key id
     *
     * @param id the id of the tag that we want to query for
     * @return a reference to the entity with the given id
     */
    Tag getTag(Long id);

    /**
     * return a list of existing tags
     *
     * @param pageable it contains requested page information
     * @return a list of existing tags
     */
    Page<Tag> tagList(Pageable pageable);

    /**
     * update the tag that has the given id and assign the value
     * of the passed tag object to the old tag, the id
     * will not be modified.
     *
     * @param id  the id of the tag that we want to update
     * @param tag the update source
     * @return the modified tag
     */
    Tag updateTag(Long id, Tag tag);

    /**
     * delete a tag from the database based on the provided tag id
     *
     * @param id the id of the tag that we want to remove
     */
    void deleteTag(Long id);

    /**
     * return the tag with the passed name if it exists in the database
     *
     * @param name the name of a tag
     * @return some tag with the passed name
     */
    Tag getTagByName(String name);

    /**
     * @return a list containing all the existing tags
     */
    List<Tag> getTagList();
}
