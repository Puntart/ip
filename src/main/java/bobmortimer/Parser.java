package bobmortimer;

//Javadocs here were made by ChatGPT
/**
 * Parses user input into command results for the BobMortimer app.
 */
public class Parser {

    private static final java.util.regex.Pattern deadlinePattern =
            java.util.regex.Pattern.compile("(?i)^deadline\\s+(.*?)\\s*/by\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*$");

    private static final java.util.regex.Pattern eventPattern =
            java.util.regex.Pattern.compile("(?i)^event\\s+(.*?)\\s*/from\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*/to\\s+"
                    + "([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*$");

    public Parser() { }

    /**
     * Enumerates the supported command types recognized by the parser.
     */
    public enum Type { BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN, FIND, STATISTICS }

    /**
     * Result of parsing an input: the command {@link Type} and its argument tokens (if any).
     */
    public static class Result {
        private Type type;
        private String[] args;
        /**
         * Creates a new Result.
         *
         * @param type the parsed command type
         * @param args the parsed arguments associated with the command (may be empty)
         */
        public Result(Type type, String... args) {
            this.type = type;
            this.args = args;
        }

        public Type getType() { return type; }
        public String[] getArgs() { return args; }
    }

    /**
     * Parses a raw user input into a Result describing the command and its arguments.
     *
     * @param input the raw user input string
     * @return a Result containing the command Type and its argument tokens
     */
    //ChatGPT suggested to shorten this method by adding the parse helper methods
    public Result parse(String input) throws BobException {
        String instruction = input.trim();

        if (instruction.equals("bye")) {
            return new Result(Type.BYE);
        }
        if (instruction.equals("list")) {
            return new Result(Type.LIST);
        }
        if (instruction.matches("^mark\\s+\\d+$")) {
            return parseMark(instruction);
        }
        if (instruction.matches("^unmark\\s+\\d+$")) {
            return parseUnmark(instruction);
        }
        if (instruction.matches("(?i)^delete\\s+\\d+$")) {
            return parseDelete(instruction);
        }
        if (instruction.toLowerCase().startsWith("todo")) {
            return parseTodo(instruction);
        }
        if (instruction.toLowerCase().startsWith("find")) {
            return new Result(Type.FIND, instruction);
        }
        if (instruction.equals("statistics")) {
            return new Result(Type.STATISTICS);
        }
        if (instruction.toLowerCase().startsWith("deadline")) {
            return parseDeadline(instruction);
        }
        if (instruction.toLowerCase().startsWith("event")) {
            return parseEvent(instruction);
        }
        return new Result(Type.UNKNOWN, instruction);
    }

    /**
     * Parses a mark command.
     *
     * @param instruction the full input (e.g., "mark 3")
     * @return the Result with type MARK and the index argument
     */
    private Result parseMark(String instruction) {
        return new Result(Type.MARK, instruction.split("\\s+")[1]);
    }

    /**
     * Parses an unmark command.
     *
     * @param instruction the full input (e.g., "unmark 2")
     * @return the Result with type UNMARK and the index argument
     */
    private Result parseUnmark(String instruction) {
        return new Result(Type.UNMARK, instruction.split("\\s+")[1]);
    }

    /**
     * Parses a delete command.
     *
     * @param instruction the full input (e.g., "delete 5")
     * @return the Result with type DELETE and the index argument
     */
    private Result parseDelete(String instruction) {
        return new Result(Type.DELETE, instruction.trim().split("\\s+")[1]);
    }

    /**
     * Parses a todo command.
     *
     * @param instruction the full input starting with "todo"
     * @return the Result with type TODO and original input as argument
     * @throws BobException if the description is empty or whitespace only
     */
    private Result parseTodo(String instruction) throws BobException {
        String rest = instruction.substring(4).trim();
        if (rest.isEmpty()) {
            throw new BobException("That's not how you input a todo mate");
        }
        return new Result(Type.TODO, instruction);
    }

    /**
     * Parses a deadline command.
     *
     * @param instruction the full input (e.g., "deadline desc /by YYYY-MM-DD")
     * @return the Result with type DEADLINE, description and date
     */
    private Result parseDeadline(String instruction) throws BobException {
        java.util.regex.Matcher deadlineMatcher = deadlinePattern.matcher(instruction);
        if (!deadlineMatcher.matches()) {
            throw new BobException("That's not how you input a deadline mate.\n"
                + "Try a format like deadline return book /by 2019-10-15");
        }
        String description = deadlineMatcher.group(1).trim();
        String deadline = deadlineMatcher.group(2).trim();
        return new Result(Type.DEADLINE, description, deadline);
    }

    /**
     * Parses an event command.
     *
     * @param instruction the full input (e.g., "event desc /from YYYY-MM-DD /to YYYY-MM-DD")
     * @return the Result with type EVENT, description, start date and end date
     */
    private Result parseEvent(String instruction) throws BobException {
        java.util.regex.Matcher eventMatcher = eventPattern.matcher(instruction);
        if (!eventMatcher.matches()) {
            throw new BobException("That's not how you input a event mate.\n"
                + "Try a format like event project meeting /from 2019-10-15 /to 2019-10-16");
        }
        String description = eventMatcher.group(1).trim();
        String startDate = eventMatcher.group(2).trim();
        String endDate = eventMatcher.group(3).trim();
        return new Result(Type.EVENT, description, startDate, endDate);
    }
}
