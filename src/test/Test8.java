package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Test8 {
	public static void main(String[] args) {
		try {
			List<String> messages = Files.readAllLines(Paths.get("D:\\my_all_messages_20160118\\unparsedMessages_0.txt"));
			
			int sum=0;
			for(String message: messages){
				sum+=message.length();
			}
			System.out.println("Average length: "+ sum/messages.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

// current result
// Average length: 152
