class TaskTracker {
    val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
        println("Task added: ${task.name}")
    }

    fun removeTask(index: Int) {
        if (index in tasks.indices) {
            val removed = tasks.removeAt(index)
            println("Task removed: ${removed.name}")
        }
    }

    fun getTotalTime(): Float {
        return tasks.sumOf { it.timeSpent.toDouble() }.toFloat()
    }

    fun displayTasks() {
        if (tasks.isEmpty()) {
            println("No tasks!")
            return
        }
        println("\nTasks (Total: ${getTotalTime()}h):")
        tasks.forEachIndexed { i, task ->
            println("${i + 1}. ")
            task.displayInfo()
        }
    }
}