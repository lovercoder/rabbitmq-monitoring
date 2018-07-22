package ro.autoepc.rabbitmqmonitoring.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ro.autoepc.rabbitmqmonitoring.domain.enumeration.State;

/**
 * A DTO for the Host entity.
 */
public class HostDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 2)
    private String description;

    @NotNull
    @Size(min = 5)
    private String dns;

    @NotNull
    private Integer port;

    private State state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HostDTO hostDTO = (HostDTO) o;
        if (hostDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hostDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HostDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dns='" + getDns() + "'" +
            ", port=" + getPort() +
            ", state='" + getState() + "'" +
            "}";
    }
}
