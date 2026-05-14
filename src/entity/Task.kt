package entity

import enums.Priority
import enums.TaskStatus
import enums.TaskType
import interfaces.TaskOperations
import interfaces.TimeEstimator
import interfaces.TimeTracking
import java.time.LocalDateTime

abstract class Task(
    var name: String,
    var description: String,
    var timeSpent: Double = 0.0,
    var status: TaskStatus = TaskStatus.PENDING,
    var priority: Priority = Priority.MEDIUM,
    val createdAt: LocalDateTime = LocalDateTime.now()
) : TimeTracking, TaskOperations, TimeEstimator {

    abstract val taskType: TaskType
    abstract override fun getEstimatedTime(): Double
    abstract fun displayInfo()

    override fun addTime(hours: Double) {
        try {
            if (validateTimeInput(hours)) {
                timeSpent += hours
            } else {
                throw IllegalArgumentException("Time must be between 0 and 24 hours, got: $hours")
            }
        } catch (e: IllegalArgumentException) {
            println("Error adding time: ${e.message}")
        } catch (e: Exception) {
            println("Unexpected error while adding time: ${e.message}")
        }
    }

    override fun validateTimeInput(hours: Double): Boolean {
        return hours > 0 && hours <= 24.0
    }

    override fun startTask() {
        try {
            if (status == TaskStatus.PENDING || status == TaskStatus.ON_HOLD) {
                status = TaskStatus.IN_PROGRESS
                println("entity.Task started: $name")
            } else {
                throw IllegalStateException("entity.Task '$name' is already $status")
            }
        } catch (e: IllegalStateException) {
            println("Cannot start task: ${e.message}")
        }
    }

    override fun completeTask() {
        try {
            if (status == TaskStatus.COMPLETED) {
                throw IllegalStateException("entity.Task '$name' is already completed")
            }
            status = TaskStatus.COMPLETED
            println("entity.Task completed: $name")
        } catch (e: IllegalStateException) {
            println("Cannot complete task: ${e.message}")
        }
    }

    override fun pauseTask() {
        try {
            if (status == TaskStatus.IN_PROGRESS) {
                status = TaskStatus.ON_HOLD
                println("entity.Task paused: $name")
            } else {
                throw IllegalStateException("entity.Task '$name' is not in progress (current status: $status)")
            }
        } catch (e: IllegalStateException) {
            println("Cannot pause task: ${e.message}")
        }
    }

    override fun getTaskSummary(): String {
        return "[$taskType] $name - $status - enums.Priority: ${priority.displayName} - Time: ${timeSpent}h"
    }

    fun getProgressPercentage(): Double {
        return try {
            val estimated = getEstimatedTime()
            if (estimated > 0) (timeSpent / estimated * 100).coerceAtMost(100.0) else 0.0
        } catch (e: Exception) {
            println("Error calculating progress: ${e.message}")
            0.0
        }
    }

    companion object {
        fun getProgressPercentage() {
            TODO("Not yet implemented")
        }
    }
}
