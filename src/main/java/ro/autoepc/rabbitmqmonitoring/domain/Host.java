package ro.autoepc.rabbitmqmonitoring.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import ro.autoepc.rabbitmqmonitoring.domain.enumeration.State;

/**
 * A Host.
 */
@Entity
@Table(name = "host")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Host implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 2)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Size(min = 5)
    @Column(name = "dns", nullable = false)
    private String dns;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Host name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Host description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDns() {
        return dns;
    }

    public Host dns(String dns) {
        this.dns = dns;
        return this;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public Integer getPort() {
        return port;
    }

    public Host port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public State getState() {
        return state;
    }

    public Host state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Host host = (Host) o;
        if (host.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), host.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Host{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dns='" + getDns() + "'" +
            ", port=" + getPort() +
            ", state='" + getState() + "'" +
            "}";
    }
}
