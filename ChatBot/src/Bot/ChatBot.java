package Bot;
import java.util.Scanner;


public class ChatBot {
	
	public static void main(String[] args) {
		System.out.println(Bot.Dialog.help_text);
		Scanner scan = new Scanner(System.in);
		Bot.Dialog dialog = new Bot.Dialog();
		while (true) {
			String req = scan.nextLine();
			if ("/exit".equals(req))
				break;
			String answer = dialog.process_input(req);
			System.out.println(answer);

		}
	}

}
