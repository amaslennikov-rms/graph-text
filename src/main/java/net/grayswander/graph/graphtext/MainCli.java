package net.grayswander.graph.graphtext;

import net.grayswander.graph.graphtext.cliprocessor.CliProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class MainCli implements ApplicationRunner {

	@Autowired
	private Map<String, CliProcessor> cliProcessors;

	public static void main(String[] args) {
		SpringApplication.run(MainCli.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			String command = args.getNonOptionArgs().get(0);
			List<String> args_except_command = Arrays.asList(args.getSourceArgs()).stream().filter(s -> !command.equals(s)).collect(Collectors.toList());
			cliProcessors.get(command).run(new DefaultApplicationArguments(args_except_command.toArray(new String[]{})));
		} finally {
			System.exit(0);
		}
	}

}
