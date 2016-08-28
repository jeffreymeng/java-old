import java.util.Arrays;
import java.util.Scanner;

import apcs.Window;

public class smartbot {

	public static void main(String[] args) {
		/*
		 * String day = Window.ask("Please enter they day of your bitrhday.");
		 * String month =
		 * Window.ask("Please enter they month of your bitrhday."); String year
		 * = Window.ask("Please enter they year of your bitrhday.");
		 * System.out.print("Your birthday is " + month + "/" + day + "/" +
		 * year);
		 */
		/*
		 * Scanner input = new Scanner(System.in);
		 * 
		 * System.out.println("please enter your weight in pounds:"); double
		 * pounds = input.nextInt() * 703;
		 * System.out.println("Please enter your height in inches:"); double
		 * height = Math.pow(input.nextInt(), 2);
		 * System.out.println("Your BMI is " + pounds / height + ".");
		 */
		/*
		 * Scanner input = new Scanner(System.in);
		 * System.out.println("Please enter a word"); String word =
		 * input.nextLine(); String result = ""; for (int i = word.length(); i >
		 * 0; i --) { result = result + word.split("")[i]; }
		 * System.out.println(result);
		 */
		/*
		 * Scanner input = new Scanner(System.in); String sentence =
		 * "I bought a dog. I named the dog will.";
		 * System.out.println("Please enter a word to replace"); String replace
		 * = input.nextLine();
		 * System.out.println("Please enter a word to replace with"); String
		 * replaceWith = input.nextLine(); sentence = sentence.replace(replace,
		 * replaceWith); System.out.println(sentence);
		 */
		/*
		 * Scanner input = new Scanner(System.in);
		 * 
		 * System.out.println(
		 * "Please enter your name in the following format:[first_name] [last_name]"
		 * ); String[] name = input.nextLine().split(" ");
		 * System.out.println("Please enter your mothers maiden name"); String
		 * mothersname = input.nextLine().substring(0, 2);
		 * System.out.println("Please enter the town in which you were born.");
		 * String town = input.nextLine().substring(0, 3);
		 * System.out.println("Your star wars name is: " + name[1].substring(0,
		 * 3) + name[0].substring(0, 2) + " " + mothersname + town);
		 */

		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Smartbot AI. Please ask a question, then press enter");
		String question = input.nextLine();
		String[] q;
		while (true) {
			q = question.split(" ");
			if (q[0] == "what") {
				if (q[1] == "is") {
					if (q[3] == "miles" || q[3] == "mi") {
						if (q[4] == "in" || q[4] == "to") {
							if (q[5] == "km" || q[5] == "kilometers") {
								System.out.println(q[2] + "miles is " + (Integer.parseInt(q[2]) * 1.60934));
							}
						}
					}
				}
			} else {
				System.out.println("Sorry, Smartbot AI does not understand your question.");
				
			}
			System.out.println("Ask another question");
			question = input.nextLine();
		}

	}

}
