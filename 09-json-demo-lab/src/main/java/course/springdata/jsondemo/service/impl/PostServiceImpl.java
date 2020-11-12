package course.springdata.jsondemo.service.impl;

import course.springdata.jsondemo.dao.PostRepository;
import course.springdata.jsondemo.entity.Post;
import course.springdata.jsondemo.exception.NonexisitingEntityException;
import course.springdata.jsondemo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;

    @Autowired
    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() ->
            new NonexisitingEntityException(String.format("Post with ID=%s does not exist.", id)));
    }

    @Transactional
    @Override
    public Post addPost(Post post) {
        post.setId(null);
        return postRepo.save(post);
    }

    @Transactional
    @Override
    public Post updatePost(Post post) {
        getPostById(post.getId());
        return postRepo.save(post);
    }

    @Transactional
    @Override
    public Post deletePost(Long id) {
        Post removed = getPostById(id);
        postRepo.deleteById(id);
        return removed;
    }

    @Override
    public long getPostsCount() {
        return postRepo.count();
    }
}
