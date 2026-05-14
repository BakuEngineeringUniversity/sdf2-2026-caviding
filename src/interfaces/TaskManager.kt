package interfaces

import entity.Task
import enums.Priority
import enums.TaskStatus
import enums.TaskType

interface TaskManager {
    fun addTask(task: Task)
    fun removeTask(index: Int)
    fun getTask(index: Int): Task?
    fun getAllTasks(): List<Task>
    fun getTasksByStatus(status: TaskStatus): List<Task>
    fun getTasksByPriority(priority: Priority): List<Task>
    fun getTasksByType(type: TaskType): List<Task>
    fun getTotalTime(): Double
    fun displayTasks()
}

interface TimeTracking {
    fun addTime(hours: Double)
    fun validateTimeInput(hours: Double): Boolean
}

interface TaskOperations {
    fun startTask()
    fun completeTask()
    fun pauseTask()
    fun getTaskSummary(): String
}
