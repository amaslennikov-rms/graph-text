package net.grayswander.graph.graphtext.cliprocessor;

import org.springframework.boot.ApplicationRunner;

public abstract class AbstractCliProcessor implements Mappable<String>, CliProcessor {
    @Override
    public String getMapKey() {
        return this.getClass().getSimpleName();
    }
}
