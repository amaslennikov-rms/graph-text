package net.grayswander.graph.graphtext.cliprocessor.command;

import net.grayswander.graph.graphtext.cliprocessor.AbstractCliProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class load extends AbstractCliProcessor {
    @Override
    public void run(ApplicationArguments args) {
        // Stub
        System.out.println(args);
    }
}
