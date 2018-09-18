import java.util.Scanner;


public class ChatBot {
	
	static String help_text = "..."; // я не знаю правил именования
	
	public static void main(String[] args) {
		System.out.println(help_text);
		Scanner scan = new Scanner(System.in);
		while (true) {
			String req = scan.nextLine();
			if ("/exit".equals(req))
				break;
			process_input(req);
		}
	}
	
	private static void process_input(String req) {
		if ("/help".equals(req)){
			System.out.println(help_text);			
		}		
		// ну и всё. сидим пердим придумываем суть
	}

}
