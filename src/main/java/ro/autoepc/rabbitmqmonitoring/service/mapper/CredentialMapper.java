package ro.autoepc.rabbitmqmonitoring.service.mapper;

import ro.autoepc.rabbitmqmonitoring.domain.*;
import ro.autoepc.rabbitmqmonitoring.service.dto.CredentialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Credential and its DTO CredentialDTO.
 */
@Mapper(componentModel = "spring", uses = {HostMapper.class})
public interface CredentialMapper extends EntityMapper<CredentialDTO, Credential> {

    @Mapping(source = "host.id", target = "hostId")
    @Mapping(source = "host.name", target = "hostName")
    CredentialDTO toDto(Credential credential);

    @Mapping(source = "hostId", target = "host")
    Credential toEntity(CredentialDTO credentialDTO);

    default Credential fromId(Long id) {
        if (id == null) {
            return null;
        }
        Credential credential = new Credential();
        credential.setId(id);
        return credential;
    }
}
