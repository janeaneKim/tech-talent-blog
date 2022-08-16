package com.techtalentsouth.techtalentblog.blogpost;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {

}
