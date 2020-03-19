package demos.springdata.jsondemo.service;

import demos.springdata.jsondemo.dao.PostRepository;
import demos.springdata.jsondemo.dao.UserRepository;
import demos.springdata.jsondemo.events.PostCreationEvent;
import demos.springdata.jsondemo.exception.EntityNotFoundException;
import demos.springdata.jsondemo.exception.InvalidEntityException;
import demos.springdata.jsondemo.model.Post;
import demos.springdata.jsondemo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@Slf4j
@Validated
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    // single TransactionTemplate shared amongst all methods in this instance
    private final PlatformTransactionManager transactionManager;

    // single TransactionTemplate shared amongst all methods in this instance
    private final TransactionTemplate transactionTemplate;

    // use constructor-injection to supply the PlatformTransactionManager
    public PostServiceImpl(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public Collection<Post> getPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post addPost(@Valid Post post) {
        Long authorId;
        if(post.getAuthor() != null) {
          authorId = post.getAuthor().getId();
        } else {
            authorId = post.getAuthorId();
        }
        if(authorId != null) {
            User author = userService.getUserById(authorId);
            post.setAuthor(author);
        }
        if(post.getCreated() == null) {
            post.setCreated(new Date());
        }
        post.setUpdated(new Date());
        return postRepo.save(post);

    }

    @Override
    public Post updatePost(Post article) {
        article.setUpdated(new Date());
        Post old = getPostById(article.getId());
        if(article.getAuthor() != null && article.getAuthor().getId() != old.getAuthor().getId())
            throw new InvalidEntityException("Author of article could not ne changed");
        article.setAuthor(old.getAuthor());
        return postRepo.save(article);
    }

    @Override
    public Post getPostById(long id) {
        return postRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Post with ID=%s not found.", id)));
    }

    @Override
    public Post deletePost(long id) {
        Post old = postRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Post with ID=%s not found.", id)));
        postRepo.deleteById(id);
        return old;
    }

    @Override
    public long getPostsCount() {
        return postRepo.count();
    }

    // Declarative transaction
    @Transactional
    public List<Post> createPostsBatch(List<Post> articles) {
        List<Post> created = articles.stream()
                .map(article -> {
                    Post resultPost = addPost(article);
                    applicationEventPublisher.publishEvent(new PostCreationEvent(resultPost));
                    return resultPost;
                }).collect(Collectors.toList());
        return created;
    }

////    Programmatic transaction
//    public List<Post> createPostsBatch(List<Post> articles) {
//        return transactionTemplate.execute(new TransactionCallback<List<Post>>() {
//            // the code in this method executes in a transactional context
//            public List<Post> doInTransaction(TransactionStatus status) {
//                List<Post> created = articles.stream()
//                        .map(article -> {
//                            try {
//                                return addPost(article);
//                            } catch (ConstraintViolationException ex) {
//                                log.error(">>> Constraint violation inserting articles: {} - {}", article, ex.getMessage());
//                                status.setRollbackOnly();
//                                return null;
//                            }
//                        }).collect(Collectors.toList());
//                return created;
//            }
//        });
//    }

//    // Managing transaction directly using PlatformTransactionManager
//    public List<Post> createPostsBatch(List<Post> articles) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        // explicitly setting the transaction name is something that can only be done programmatically
//        def.setName("createPostsBatchTransaction");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        def.setTimeout(5);
//
//        // Do in transaction
//        TransactionStatus status = transactionManager.getTransaction(def);
//        List<Post> created = articles.stream()
//            .map(article -> {
//                try {
//                    Post resultPost = addPost(article);
//                    applicationEventPublisher.publishEvent(new PostCreationEvent(resultPost));
//                    return resultPost;
//                } catch (ConstraintViolationException ex) {
//                    log.error(">>> Constraint violation inserting article: {} - {}", article, ex.getMessage());
//                    transactionManager.rollback(status); // ROLLBACK
//                    throw ex;
//                }
//            }).collect(Collectors.toList());
//
//        transactionManager.commit(status); // COMMIT
//        return created;
//    }

    @TransactionalEventListener
    public void handlePostCreatedTransactionCommit(PostCreationEvent creationEvent) {
        log.info(">>> Transaction COMMIT for article: {}", creationEvent.getPost());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handlePostCreatedTransactionRollaback(PostCreationEvent creationEvent) {
        log.info(">>> Transaction ROLLBACK for article: {}", creationEvent.getPost());
    }
}
