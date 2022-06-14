package io.tenants.database.saas.repository;

import io.tenants.database.saas.model.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CustomTenantRepository<Post, Long> {
}
