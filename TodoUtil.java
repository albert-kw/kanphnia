
import java.util.Scanner;

public class TodoUtil {

    private static Scanner input = new Scanner (System.in);

    public static String prompt (String message) {
        String userInput = "";

        do {
            System.out.print (message);
            userInput = input.nextLine();

        } while (userInput.isEmpty() ||
            userInput.trim().isEmpty() ||
            userInput == null
        );

        return userInput;
    } //end stc String prompt (String)

} //end class TodoUtil
