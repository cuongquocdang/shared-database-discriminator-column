package io.tenants.database.saas.repository;

import io.tenants.database.BaseTenant;
import io.tenants.database.TenantContextHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomTenantRepository<T extends BaseTenant, ID> extends JpaRepository<T, ID> {

    @Override
    default void deleteById(ID id) {
        deleteByIdAndTenantId(id, (Long) TenantContextHolder.getContext().getTenantId());
    }

    @Override
    default boolean existsById(ID id) {
        return existsByIdAndTenantId(id, (Long) TenantContextHolder.getContext().getTenantId());
    }

    boolean existsByIdAndTenantId(ID id, Long tenantId);

    void deleteByIdAndTenantId(ID id, Long tenantId);
}
