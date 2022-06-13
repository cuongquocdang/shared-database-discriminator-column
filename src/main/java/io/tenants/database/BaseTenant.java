package io.tenants.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.tenants.database.admin.model.Tenant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;

import static io.tenants.database.BaseTenant.TENANT_FILTER;
import static io.tenants.database.BaseTenant.TENANT_PARAMETER;

@MappedSuperclass
@FilterDef(name = TENANT_FILTER, parameters = {@ParamDef(name = TENANT_PARAMETER, type = "long")})
@Filter(name = TENANT_FILTER, condition = "tenant_id = :tenantId")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Slf4j
public class BaseTenant extends BaseEntity {

    // determine to retrieve data of own tenant
    public static final String TENANT_FILTER = "tenantFilter";
    public static final String TENANT_PARAMETER = "tenantId";

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
