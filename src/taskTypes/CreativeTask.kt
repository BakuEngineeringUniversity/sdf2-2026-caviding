package taskTypes

import entity.Task
import kotlin.text.format

abstract class CreativeTask(
    name: String,
    description: String,
    protected val complexityLevel: Int = 1
) : Task(name, description) {

    abstract fun getComplexityFactor(): Double

    override fun getEstimatedTime(): Double {
        val baseTime = getBaseEstimatedTime()
        return baseTime * getComplexityFactor() * (1 + (priority.level - 2) * 0.2f)
    }

    protected abstract fun getBaseEstimatedTime(): Double

    fun getComplexityDescription(): String {
        return when (complexityLevel) {
            1 -> "Simple"
            2 -> "Medium"
            3 -> "Complex"
            4 -> "Very Complex"
            else -> "Unknown"
        }
    }

    override fun displayInfo() {
        val progress = Task.getProgressPercentage()
        val estimated = getEstimatedTime()
        println("${getTaskTypeDisplay()}: $name - ${getComplexityDescription()} (${timeSpent}h / ${estimated}h) - %${"%.1f".format(progress)}")
    }

    protected abstract fun getTaskTypeDisplay(): String
}