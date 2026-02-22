# Sage User Guide

Sage is an old man who spends his days by the sea, letting the breeze carry his thoughts.
If you ask nicely, he'll help you keep track of your todolist—with quiet wisdom and
zero judgment. He prefers the simple things in life, much like this task tracker.  

<img src="Ui.png">

### 3 types of tasks:
- Todo (task description)
- Deadline (task description + deadline)
- Event (task description + start date + end date)

### Features
- Ability to **list** all tasks
- Ability to **mark** tasks as complete
- Ability to **unmark** tasks as incomplete
- Ability to **delete** tasks
- Ability to **find** tasks matching keywords
- Automatic sorting by task type, completion status, date and alphabetical order

## Adding ToDos
Add a ToDo containing the description of the task.

Command format:  
`todo [description]`

Example:  
`todo buy books`

Output:
```
Got it. I've added this task:
[T][ ] buy books
You've now set out to do 1 thing(s).
```

## Adding Deadlines
Add a Deadline containing the description and due date.

Command format:  
`deadline [description] /by [YYYY-MM-DD]`

Example:  
`deadline submit report /by 2026-02-28`

Output:
```
Got it. I've added this task:
[D][ ] submit report (by: 28 Feb 2026)
You've now set out to do 2 thing(s).
```

## Adding Events
Add an Event containing the description, start date, and end date.

Command format:  
`event [description] /from [YYYY-MM-DD] /to [YYYY-MM-DD]`

Example:  
`event holiday /from 2026-06-01 /to 2026-06-15`

Output:
```
Got it. I've added this task:
[E][ ] holiday (from: 1 Jun 2026 to: 15 Jun 2026)
You've now set out to do 3 thing(s).
```

## Listing tasks
Display all tasks currently in your list.

Command format:  
`list`


Output:
```
These are what you set out to do:
1. [T][ ] buy books
2. [D][ ] submit report (by: 28 Feb 2026)
3. [E][ ] holiday (from: 1 Jun 2026 to: 15 Jun 2026)
```

## Marking tasks as complete
Mark a task as done.

Command format:  
`mark [task number]`

Example:  
`mark 1`

Output:
```
Got it. I've marked "1. buy books" as done.
```

## Unmarking tasks as incomplete
Mark a task as undone.

Command format:  
`unmark [task number]`

Example:  
`unmark 1`

Output:
```
Got it. I've marked "1. buy books" as undone.
```

## Deleting tasks
Remove a task from your list.

Command format:  
`delete [task number]`

Example:  
`delete 2`

Output:
```
Got it. I've removed "2. submit report".
You've now set out to do 2 thing(s).
```

## Finding tasks
Search for tasks containing a keyword.

Command format:  
`find [keyword]`

Example:  
`find book`

Output:
```
Here are the matching tasks in your list:
1. [T][ ] buy books
```