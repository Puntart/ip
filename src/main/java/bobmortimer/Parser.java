package bobmortimer;

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
        public Type type;
        public String[] args;
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
    }

    /**
     * Parses a raw user input into a Result describing the command and its arguments.
     *
     * @param input the raw user input string
     * @return a Result containing the command Type and its argument tokens
     */
    public Result parse(String input) {
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
            return new Result(Type.TODO, instruction);
        }
        if (instruction.toLowerCase().startsWith("find")) {
            return new Result(Type.FIND, instruction);
        }
        if (instruction.equals("statistics")) {
            return new Result(Type.STATISTICS);
        }
        if (deadlinePattern.matcher(instruction).matches()) {
            return parseDeadline(instruction);
        }
        if (eventPattern.matcher(instruction).matches()) {
            return parseEvent(instruction);
        }
        return new Result(Type.UNKNOWN, instruction);
    }

    /**
     * Parses a mark command.
     */
    private Result parseMark(String in) {
        return new Result(Type.MARK, in.split("\\s+")[1]);
    }

    /**
     * Parses an unmark command.
     */
    private Result parseUnmark(String in) {
        return new Result(Type.UNMARK, in.split("\\s+")[1]);
    }

    /**
     * Parses a delete command.
     */
    private Result parseDelete(String in) {
        return new Result(Type.DELETE, in.trim().split("\\s+")[1]);
    }

    /**
     * Parses a deadline command.
     */
    private Result parseDeadline(String in) {
        java.util.regex.Matcher deadlineMatcher = deadlinePattern.matcher(in);
        deadlineMatcher.matches();
        String description = deadlineMatcher.group(1).trim();
        String deadline = deadlineMatcher.group(2).trim();
        return new Result(Type.DEADLINE, description, deadline);
    }

    /**
     * Parses an event command.
     */
    private Result parseEvent(String in) {
        java.util.regex.Matcher eventMatcher = eventPattern.matcher(in);
        eventMatcher.matches();
        String description = eventMatcher.group(1).trim();
        String startDate = eventMatcher.group(2).trim();
        String endDate = eventMatcher.group(3).trim();
        return new Result(Type.EVENT, description, startDate, endDate);
    }
}
