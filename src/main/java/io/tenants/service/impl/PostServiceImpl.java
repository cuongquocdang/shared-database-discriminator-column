package io.tenants.service.impl;

import io.tenants.database.CurrentTenant;
import io.tenants.database.saas.model.Post;
import io.tenants.database.saas.repository.PostRepository;
import io.tenants.service.PostService;
import io.tenants.web.dto.PostCreationDto;
import io.tenants.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(PostCreationDto postCreationDto) {
        Post post = new Post();
        post.setContent(postCreationDto.getContent());
        // do not publish with a new post
        post.setPublished(false);
        postRepository.save(post);
    }

    @Override
    @CurrentTenant
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .build())
                .toList();
    }

    @Override
    public void deletePostById(Long id) {
        if (!postRepository.existsById(id)) {
            log.error("Not found with id: {}", id);
            throw new RuntimeException("Not found");
        }

        // only delete the record that belongs to tenant
        postRepository.deleteById(id);
    }
}
