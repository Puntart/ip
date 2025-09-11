package BobMortimer;

public class Parser {

    private static final java.util.regex.Pattern deadlinePattern =
            java.util.regex.Pattern.compile("(?i)^deadline\\s+(.*?)\\s*/by\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*$");

    private static final java.util.regex.Pattern eventPattern =
            java.util.regex.Pattern.compile("(?i)^event\\s+(.*?)\\s*/from\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*/to\\s+"
                    + "([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*$");

    public Parser() { }

    public enum Type { BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN, FIND }

    public static class Result {
        public Type type;
        public String[] args;
        public Result(Type type, String... args) {
            this.type = type;
            this.args = args;
        }
    }

    public Result parse(String input) {
        String in = input.trim();

        if (in.equals("bye")) {
            return new Result(Type.BYE);
        }
        if (in.equals("list")) {
            return new Result(Type.LIST);
        }

        if (in.matches("^mark\\s+\\d+$")) {
            return new Result(Type.MARK, in.split("\\s+")[1]);
        }

        if (in.matches("^unmark\\s+\\d+$")) {
            return new Result(Type.UNMARK, in.split("\\s+")[1]);
        }

        if (in.matches("(?i)^delete\\s+\\d+$")) {
            return new Result(Type.DELETE, in.trim().split("\\s+")[1]);
        }

        if (in.toLowerCase().startsWith("todo")) {
            return new Result(Type.TODO, in);
        }

        if (in.toLowerCase().startsWith("find")) {
            return new Result(Type.FIND, in);
        }

        java.util.regex.Matcher deadlineMatcher = deadlinePattern.matcher(in);
        if (deadlineMatcher.matches()) {
            String description = deadlineMatcher.group(1).trim();
            String deadline = deadlineMatcher.group(2).trim();
            return new Result(Type.DEADLINE, description, deadline);
        }

        java.util.regex.Matcher eventMatcher = eventPattern.matcher(in);
        if (eventMatcher.matches()) {
            String description = eventMatcher.group(1).trim();
            String startDate = eventMatcher.group(2).trim();
            String endDate = eventMatcher.group(3).trim();
            return new Result(Type.EVENT, description, startDate, endDate);
        }

        return new Result(Type.UNKNOWN, in);
    }
}
