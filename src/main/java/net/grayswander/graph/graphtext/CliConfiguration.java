package net.grayswander.graph.graphtext;

import net.grayswander.graph.graphtext.cliprocessor.AbstractCliProcessor;
import net.grayswander.graph.graphtext.cliprocessor.CliProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class CliConfiguration {
    @Bean
    Map<String, CliProcessor> getCilProcessirs(Set<AbstractCliProcessor> processors) {
        return processors.stream().collect(Collectors.toUnmodifiableMap(AbstractCliProcessor::getMapKey,processor -> processor));
//        Map<String, CliProcessor> map = Collections.emptyMap();
//        processors.stream().forEach(processor -> map.put(processor.getMapKey(), processor));
//        return Collections.unmodifiableMap(map);
    }
}
