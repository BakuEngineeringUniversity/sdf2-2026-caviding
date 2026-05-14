package entity

import enums.Priority
import enums.TaskStatus
import enums.TaskType
import interfaces.TaskManager

class TaskTracker : TaskManager {
    private val tasks = mutableListOf<Task>()

    override fun addTask(task: Task) {
        try {
            if (task.name.isBlank()) {
                throw IllegalArgumentException("entity.Task name cannot be empty")
            }
            tasks.add(task)
            println("entity.Task added: ${task.name}")
        } catch (e: IllegalArgumentException) {
            println("Error adding task: ${e.message}")
        } catch (e: Exception) {
            println("Unexpected error while adding task: ${e.message}")
        }
    }

    override fun removeTask(index: Int) {
        try {
            if (index !in tasks.indices) {
                throw IndexOutOfBoundsException("Invalid task number: ${index + 1}")
            }
            val removed = tasks.removeAt(index)
            println("entity.Task removed: ${removed.name}")
        } catch (e: IndexOutOfBoundsException) {
            println("Error removing task: ${e.message}")
        } catch (e: Exception) {
            println("Unexpected error while removing task: ${e.message}")
        }
    }

    override fun getTask(index: Int): Task? {
        return try {
            if (index in tasks.indices) tasks[index] else null
        } catch (e: Exception) {
            println("Error retrieving task: ${e.message}")
            null
        }
    }

    override fun getAllTasks(): List<Task> = tasks.toList()

    override fun getTasksByStatus(status: TaskStatus): List<Task> {
        return tasks.filter { it.status == status }
    }

    override fun getTasksByPriority(priority: Priority): List<Task> {
        return tasks.filter { it.priority == priority }
    }

    override fun getTasksByType(type: TaskType): List<Task> {
        return tasks.filter { it.taskType == type }
    }

    override fun getTotalTime(): Double {
        return tasks.sumOf { it.timeSpent }
    }

    override fun displayTasks() {
        try {
            if (tasks.isEmpty()) {
                println("No tasks!")
                return
            }

            println("\n=== TASK LIST ===")
            println("Total Time: ${getTotalTime()}h")
            println("Total Tasks: ${tasks.size}")
            println()

            tasks.forEachIndexed { i, task ->
                println("${i + 1}. ${task.getTaskSummary()}")
                task.displayInfo()
                println()
            }
        } catch (e: Exception) {
            println("Error displaying tasks: ${e.message}")
        }
    }

    fun displayTasksByStatus() {
        TaskStatus.values().forEach { status ->
            val statusTasks = getTasksByStatus(status)
            if (statusTasks.isNotEmpty()) {
                println("\n$status (${statusTasks.size}):")
                statusTasks.forEachIndexed { i, task ->
                    println("  ${i + 1}. ${task.name} - ${task.timeSpent}h")
                }
            }
        }
    }

    fun displayTasksByPriority() {
        Priority.values().sortedByDescending { it.level }.forEach { priority ->
            val priorityTasks = getTasksByPriority(priority)
            if (priorityTasks.isNotEmpty()) {
                println("\n${priority.displayName} enums.Priority (${priorityTasks.size}):")
                priorityTasks.forEachIndexed { i, task ->
                    println("  ${i + 1}. ${task.name} - ${task.status}")
                }
            }
        }
    }

    fun getStatistics(): String {
        return try {
            val totalTasks = tasks.size
            val completedTasks = getTasksByStatus(TaskStatus.COMPLETED).size
            val inProgressTasks = getTasksByStatus(TaskStatus.IN_PROGRESS).size
            val totalTime = getTotalTime()

            """
            entity.Task Statistics:
            - Total Tasks: $totalTasks
            - Completed: $completedTasks
            - In Progress: $inProgressTasks
            - Total Time: ${"%.1f".format(totalTime)} hours
            - Completion Rate: ${if (totalTasks > 0) "%.1f".format(completedTasks.toDouble() / totalTasks * 100) else "0.0"}%
            """.trimIndent()
        } catch (e: Exception) {
            "Error generating statistics: ${e.message}"
        }
    }

    fun startTask(index: Int) {
        try {
            getTask(index)?.startTask() ?: throw IndexOutOfBoundsException("entity.Task not found at index: ${index + 1}")
        } catch (e: IndexOutOfBoundsException) {
            println("Error: ${e.message}")
        }
    }

    fun completeTask(index: Int) {
        try {
            getTask(index)?.completeTask() ?: throw IndexOutOfBoundsException("entity.Task not found at index: ${index + 1}")
        } catch (e: IndexOutOfBoundsException) {
            println("Error: ${e.message}")
        }
    }

    fun pauseTask(index: Int) {
        try {
            getTask(index)?.pauseTask() ?: throw IndexOutOfBoundsException("entity.Task not found at index: ${index + 1}")
        } catch (e: IndexOutOfBoundsException) {
            println("Error: ${e.message}")
        }
    }

    fun addTimeToTask(index: Int, hours: Double) {
        try {
            getTask(index)?.addTime(hours) ?: throw IndexOutOfBoundsException("entity.Task not found at index: ${index + 1}")
        } catch (e: IndexOutOfBoundsException) {
            println("Error: ${e.message}")
        }
    }
}
