package io.tenants.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.tenants.database.admin.model.Tenant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Slf4j
public class BaseTenant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", updatable = false)
    @JsonIgnore
    private Tenant tenant;

    @PrePersist
    @PreUpdate
    @PreRemove
    public void onPrePersist() {
        this.tenant = TenantContextHolder.getContext().getTenant();
        log.info("onPrePersist with tenantId: {}", tenant.getId());
    }
}
