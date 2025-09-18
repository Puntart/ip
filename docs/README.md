# User Guide
## This is Bob Mortimer

![sth](https://puntart.github.io/ip/Ui.png)

Welcome to the Bob Mortimer User Guide! Ever have too many tasks to keep track of when you're locked in? The Legendary Bob Mortimer has got you covered mate.

## How To Start
1. Ensure you have Java 17 or above installed in your Computer
2. Download the v0.2 jar file [here](https://github.com/Puntart/ip/releases)
3. Run it using java -jar iP.jar in the terminal

# Features

## Viewing Your Task List

Look at your current task list.

Format: `list`

## Adding To Do Tasks

Add a To Do task to the list. A To Do task only has the task description.

Format: `todo DESCRIPTION`

Example: `todo finish assignment`

Example Expected Output:

```
Got it. I've added this task:
[T][] finish assignment
Now you have 3 tasks in the list
```


## Adding Deadline Tasks

Add a Deadline task to the list. A Deadline task consists of the task description and due date.

Format: `deadline DESCRIPTION /by YYYY-MM-DD`

Example: `deadline bake brownies /by 2025-09-18`

Example Expected Output:

```
Got it. I've added this task:
[D][ ] bake brownies (by: Sep 18 2025)
Now you have 4 tasks in the list
```

## Adding Event Tasks

Add an Event task to the list. An Event task consists of the task description, start date and end date.

Format: `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD`

Example: `event finals week /from 2025-11-20 /to 2025-11-27`

Example Expected Output:

```
Got it. I've added this task:
[E][ ] finals week (from: Nov 20 2025 to: Nov 27 2025)
Now you have 5 tasks in the list
```

## Marking Tasks

Mark a task as done. A marked task will have an X marked in the list. To choose the desired task to mark, input its index shown on the list.

Do note that the index MUST be a positive in the list. Same goes for unmark and delete commands.

Format: `mark INDEX`

Example: `mark 2`

Example Expected Output:

```
Nice! It's done!:
[D][X] return book (by: Oct 15 2019)
```

## Unmarking Tasks

Mark a task as not done. The task will be unmarked in the list. To choose the desired task to mark, input its index shown on the list.

Format: `unmark INDEX`

Example: `unmark 2`

Example Expected Output:

```
OK, not done!:
[D][] return book (by: Oct 15 2019)
```

## Deleting Tasks

Remove a task from the list. To choose the desired task to delete, input its index shown on the list.

Format: `delete INDEX`

Example: `delete 2`

Example Expected Output:

```
Ok, I have removed the task:
[D][ ] return book (by: Oct 15 2019)
Now you have 4 tasks in the list
```

## Finding Tasks

Find a task in the list. The chatbot will provide the tasks that contains your input.

Format: `find KEYWORD`

Example: `find book`

Example Expected Output:

```
Here are the matching tasks in your list:
1. [T][X] read book
2. [D][ ] borrow book (by: May 29 2025)
```

Notice that the keyword may not even be a full word to work. Any task description that contains the keyword will be provided. However, do keep in mind that it is case-sensitive.

Example: `find oo`

## Viewing Statistics

Obtain statstics of how many tasks are done and how many tasks are not done. If you have too little tasks done, you might be insulted.

Example: `statistics`

Example Expected Output:

```
Here are some statistics!
2 are done!
7 are not done!
Get to it you lazy sausage.
```

## Exiting

just say `bye`

## Command Summary

Here is the summary of the commands. Enjoy your new companion!

| Action      | Format                                                                  | Example |
|-------------|-------------------------------------------------------------------------|---------|
| List        | `list`                                                                  | `list` |
| Add ToDo    | `todo DESCRIPTION`                                                      | `todo finish assignment` |
| Add Deadline| `deadline DESCRIPTION /by YYYY-MM-DD`                                   | `deadline bake brownies /by 2025-09-18` |
| Add Event   | `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD`                     | `event finals week /from 2025-11-20 /to 2025-11-27` |
| Mark        | `mark INDEX`                                                            | `mark 2` |
| Unmark      | `unmark INDEX`                                                          | `unmark 2` |
| Delete      | `delete INDEX`                                                          | `delete 2` |
| Find        | `find KEYWORD`                                          | `find book` |
| Statistics  | `statistics`                                                            | `statistics` |
| Exit        | `bye`                                                                   | `bye` |

### Notes on command format
- Parameters are in `UPPER_CASE`.  
  Example: in `todo DESCRIPTION`, replace `DESCRIPTION`.
- `INDEX` is the number shown in the current task list and must be a
  positive integer.
- `find` is case-sensitive but matches any of the given keywords.
