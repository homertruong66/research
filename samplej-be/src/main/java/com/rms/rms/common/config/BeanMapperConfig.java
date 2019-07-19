package com.rms.rms.common.config;

import com.rms.rms.common.view_model.OrderLineCreateModel;
import com.rms.rms.service.model.OrderLine;
import com.rms.rms.service.model.Product;
import com.rms.rms.service.model.Role;
import org.hibernate.Hibernate;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * homertruong
 *
 * User ModelMapper for bean mapping
 */

@Configuration
public class BeanMapperConfig {

    @Bean
    public ModelMapper config() {
        ModelMapper modelMapper = new ModelMapper();

        // Converters
        Converter<String, Role> strToRole = new AbstractConverter<String, Role>() {
            protected Role convert(String source) {
                Role role = new Role();
                role.setName(source);
                return role;
            }
        };

        Converter<Role, String> roleToStr = new AbstractConverter<Role, String>() {
            protected String convert(Role source) {
                return source.getName();
            }
        };

        Converter<OrderLineCreateModel, OrderLine> olcmToOl = new AbstractConverter<OrderLineCreateModel, OrderLine>() {
            protected OrderLine convert(OrderLineCreateModel source) {
                OrderLine destination = new OrderLine();
                destination.setPrice(source.getPrice());
                destination.setQuantity(source.getQuantity());
                Product product = modelMapper.map(source.getProduct(), Product.class);
                destination.setProduct(product);
                return destination;
            }
        };

        modelMapper.addConverter(strToRole);
        modelMapper.addConverter(roleToStr);
        modelMapper.addConverter(olcmToOl);

        //ignore persistentSet hibernate lazy
        modelMapper.getConfiguration().setPropertyCondition(context -> {
            if (context.getSource() instanceof PersistentCollection) {
                if (!Hibernate.isInitialized(context.getSource()))
                    return false;
            }
            return true;
        });

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
