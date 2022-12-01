package guru.sfg.beer.inventory.service.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


@Slf4j
@Converter
public class UuidConverter
        implements AttributeConverter<UUID,String> {
    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        log.debug("UUID object: "+attribute);
        String str = attribute.toString();
        log.debug("db str: "+str);
        return  str;
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        log.debug("db str: "+dbData);
        UUID uuid = UUID.fromString(dbData);
        log.debug("UUID object: "+uuid);
        return uuid;
    }
}