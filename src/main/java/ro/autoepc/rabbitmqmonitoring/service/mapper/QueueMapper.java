package ro.autoepc.rabbitmqmonitoring.service.mapper;

import ro.autoepc.rabbitmqmonitoring.domain.*;
import ro.autoepc.rabbitmqmonitoring.service.dto.QueueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Queue and its DTO QueueDTO.
 */
@Mapper(componentModel = "spring", uses = {HostMapper.class})
public interface QueueMapper extends EntityMapper<QueueDTO, Queue> {

    @Mapping(source = "host.id", target = "hostId")
    @Mapping(source = "host.name", target = "hostName")
    QueueDTO toDto(Queue queue);

    @Mapping(source = "hostId", target = "host")
    Queue toEntity(QueueDTO queueDTO);

    default Queue fromId(Long id) {
        if (id == null) {
            return null;
        }
        Queue queue = new Queue();
        queue.setId(id);
        return queue;
    }
}
