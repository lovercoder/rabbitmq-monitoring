package ro.autoepc.rabbitmqmonitoring.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Queue entity.
 */
public class QueueDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Integer count;

    @NotNull
    private String description;

    private Long hostId;

    private String hostName;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QueueDTO queueDTO = (QueueDTO) o;
        if (queueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), queueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QueueDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", count=" + getCount() +
            ", description='" + getDescription() + "'" +
            ", host=" + getHostId() +
            ", host='" + getHostName() + "'" +
            "}";
    }
}
