package demos.springdata.restdemo.service.impl;

import demos.springdata.restdemo.dao.PostRepository;
import demos.springdata.restdemo.dao.UserRepository;
import demos.springdata.restdemo.exception.InvalidEntityException;
import demos.springdata.restdemo.model.Post;
import demos.springdata.restdemo.model.User;
import demos.springdata.restdemo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Date;

@Service
public class PostServiceImpl  implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;


    @Override
    public Collection<Post> getPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post addPost(Post post) {
        Long authorId;
        if(post.getAuthor() != null && post.getAuthor().getId() != null) {
            authorId = post.getAuthor().getId();
        } else {
            authorId = post.getAuthorId();
        }
        if(authorId != null) {
            User author = userRepo.findById(authorId)
                    .orElseThrow(() -> new InvalidEntityException("Author with ID=" + authorId + " does not exist."));
            post.setAuthor(author);
        }
        if(post.getCreated() == null) {
            post.setCreated(new Date());
        }
        post.setModified(post.getCreated());

        return postRepo.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public Post deletePost(Long id) {
        return null;
    }

    @Override
    public long getPostsCount() {
        return 0;
    }
}
