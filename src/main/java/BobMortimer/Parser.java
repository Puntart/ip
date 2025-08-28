package BobMortimer;

public class Parser {

    public Parser() { }

    public enum Type { BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN }

    private static final java.util.regex.Pattern pDeadline =
            java.util.regex.Pattern.compile("(?i)^deadline\\s+(.*?)\\s*/by\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*$");

    private static final java.util.regex.Pattern pEvent =
            java.util.regex.Pattern.compile("(?i)^event\\s+(.*?)\\s*/from\\s+([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*/to\\s+" +
                    "([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*$");

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

        if (in.equals("bye"))  return new Result(Type.BYE);
        if (in.equals("list")) return new Result(Type.LIST);

        if (in.matches("^mark\\s+\\d+$"))
            return new Result(Type.MARK, in.split("\\s+")[1]);

        if (in.matches("^unmark\\s+\\d+$"))
            return new Result(Type.UNMARK, in.split("\\s+")[1]);

        if (in.matches("(?i)^delete\\s+\\d+$"))
            return new Result(Type.DELETE, in.trim().split("\\s+")[1]);

        if (in.toLowerCase().startsWith("todo"))
            return new Result(Type.TODO, in);

        java.util.regex.Matcher md = pDeadline.matcher(in);
        if (md.matches()) {
            String description = md.group(1).trim();
            String deadline = md.group(2).trim();
            return new Result(Type.DEADLINE, description, deadline);
        }

        java.util.regex.Matcher me = pEvent.matcher(in);
        if (me.matches()) {
            String description = me.group(1).trim();
            String startDate = me.group(2).trim();
            String endDate = me.group(3).trim();
            return new Result(Type.EVENT, description, startDate, endDate);
        }

        return new Result(Type.UNKNOWN, in);
    }
}
