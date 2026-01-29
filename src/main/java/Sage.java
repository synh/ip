import java.util.Scanner;
public class Sage {
    public static void main(String[] args) {
        System.out.println("Hello there, Sage here.");
        System.out.println("How are you doing?");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Echo user input
        while (!input.equals("bye")) {
            System.out.println(input);
            input = scanner.nextLine();
        }

        System.out.println("Goodbye. Have a beautiful day.");
        scanner.close();
    }
}