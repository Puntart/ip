import java.util.Scanner;

public class BobMortimer {
    public static void main(String[] args) {
        String logo = " ____      ___     ____        __  __     ___     ____     _____    _____   __  __    _____    ____    \n" +
                "| __ )    / _ \\   | __ )       |  \\/  |   / _ \\   |  _ \\   |_   _|    ___    |  \\/  |  | ____|  |  _ \\   \n" +
                "|  _ \\   | | | |  |  _ \\       | |\\/| |  | | | |  | |_) |    | |      | |    | |\\/| |  |  _|    | |_) |  \n" +
                "| |_) |  | |_| |  | |_) |      | |  | |  | |_| |  |  _ <     | |      | |    | |  | |  | |___   |  _ <   \n" +
                "|____/    \\___/   |____/       |_|  |_|   \\___/   |_| \\_\\    |_|      |_|    |_|  |_|  |_____|  |_| \\_\\ :) \n";
        String LINE = "____________________________________________________________";
        Scanner userInput = new Scanner(System.in);
        String tasks[] = new String[100];
        int taskNo = 0;


        //Greeting
        System.out.println(LINE + "\n" +
                "Hello I'm\n" + logo
                + "\nWhat can I do for you?\n"
                + LINE + "\n");

        //User input
        while (true) {
            String instruction = userInput.nextLine();
            if(instruction.equals("bye")) {
                break;
            }
            else if(instruction.equals("list")) {
                System.out.println("\n" + LINE + "\n");
                for(int i=0; i < taskNo; i++) {
                    System.out.println((i+1) + ". " + tasks[i]);
                }
                continue;
            }
            System.out.println(instruction + "\n" + LINE + "\n");
            tasks[taskNo] = instruction;
            taskNo++;
        }

        //exit
        System.out.println("Bye. Hope to see you again soon!\n"
                        + LINE);
    }
}
