package ro.autoepc.rabbitmqmonitoring.service.mapper;

import ro.autoepc.rabbitmqmonitoring.domain.*;
import ro.autoepc.rabbitmqmonitoring.service.dto.HostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Host and its DTO HostDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HostMapper extends EntityMapper<HostDTO, Host> {



    default Host fromId(Long id) {
        if (id == null) {
            return null;
        }
        Host host = new Host();
        host.setId(id);
        return host;
    }
}
