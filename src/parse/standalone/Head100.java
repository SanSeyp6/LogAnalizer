package parse.standalone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Head100 {
	public static void main(String[] args) {
		String unparsedMessagesFileName="D:\\Универ\\Магистратура\\Диссертация\\Eclipse Workspaces\\log_analysis\\unparsed_messages.txt";
		String head100="D:\\Универ\\Магистратура\\Диссертация\\Eclipse Workspaces\\log_analysis\\head100.txt";
		List<String> templates;

		try {
			templates = Files.readAllLines(Paths.get(unparsedMessagesFileName));
			templates.subList(0, 100);
			Files.write(Paths.get(head100), templates, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
