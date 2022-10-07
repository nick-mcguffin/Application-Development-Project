package com.wilma.service.forum;

import com.wilma.entity.Tag;
import com.wilma.repository.TagRepository;
import com.wilma.service.CrudOpsImpl;
import org.springframework.stereotype.Service;

@Service
public class TagService extends CrudOpsImpl<Tag, Integer, TagRepository> {

}
