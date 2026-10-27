Implement a task scheduler that decides the order in which a set of tasks is output. The interviewer's specification was long and full of detailed conditions; the problem itself is not hard, but the details are easy to miss. The reported core requirements are:

Tasks can depend on other tasks, and a task may be output only after all of its dependencies have been output.
Tasks carry attributes such as importance or priority that affect the order.
Filter requirements remove some tasks from the output.
AI assistance was optional in this round; candidates who used it had to share their screen.

Constraints and Clarifications
The exact input format was not reported. Assume each task has an id, a list of dependency ids, a priority, and other attributes that filters can test, and adapt if the interviewer gives a different format.
Several ordering and filtering policies are not specified; settle them with the interviewer (see below) and encode each one as an explicit, testable rule.
Clarifying QuestionsGuidance
When several tasks are ready at the same time, does a higher priority always go first? Is a larger number more important? How are equal priorities ordered: by input order, by id, or by another attribute?
If a high-priority task depends on a low-priority one, should the dependency be pulled forward ahead of other ready tasks?
If a filter removes a task that a kept task depends on, should the kept task still wait for the removed task's own dependencies, be removed as well, or should the removed dependency be output anyway?
Are several filters combined with AND or OR, and are they applied before or after ordering?
What should happen on a dependency cycle, a dependency on an unknown task id, or a duplicate task id?
Is the output one flat list, or stages of tasks that could run in parallel?
Part 1 — Dependencies first
Output every task so that each task appears after all of its dependencies.


What can go next
What This Part Should CoverGuidance
A correct order for any acyclic input, including tasks without dependencies and dependencies shared by several tasks
Detection and reporting of cycles, unknown ids and duplicate ids
Running time linear in the number of tasks and dependency edges
A deterministic order when several tasks are eligible
Part 2 — Priority and importance
Among the tasks whose dependencies are satisfied, choose the next one according to priority and the other importance attributes, using the rule you clarified.


Keep the candidates ordered
What This Part Should CoverGuidance
An exact comparison rule: priority, any secondary attributes, then a stable final tie-break
Efficient selection of the next task
The interaction between priority and dependencies, including a high-priority task blocked by a low-priority dependency
Tests for orderings where priority and dependencies conflict
Part 3 — Filters
Apply filter requirements that remove some tasks from the output while keeping the ordering guarantees for the tasks that remain.


A removed task in the middle
What This Part Should CoverGuidance
A composable filter representation
The clarified policy for dependencies on removed tasks, applied transitively
Ordering guarantees that still hold after filtering
Code structure that absorbs new requirements without a rewrite
What a Strong Answer CoversGuidance
Requirements clarified up front and written down as a checklist of rules
A clean pipeline: validate the input, build the graph, apply filters, order
A correct dependency order with a deterministic priority rule
Explicit handling of cycles, unknown ids, duplicates and empty input
Tests that cover each rule and the interactions between rules
Follow-up QuestionsGuidance
A low-priority task is a dependency of the highest-priority task. How would you make the dependency go ahead of other ready tasks with higher priority, and how do you compute that efficiently?
How would you output tasks in stages, where each stage contains tasks that can run in parallel?
Tasks are added and priorities change while the scheduler runs. What changes in your design?
With an AI assistant available, how would you make sure none of the many detailed requirements is dropped?