class ProgrammingTask(
    name: String,
    description: String,
    val language: String,
    val framework: String = "",
    val difficulty: Int = 1
) : Task(name, description) {

    override val taskType = TaskType.PROGRAMMING

    override fun getEstimatedTime(): Double {
        val baseTime = when (difficulty) {
            1 -> 8
            2 -> 16
            3 -> 24
            4 -> 40
            else -> 20
        }
        
        val languageMultiplier = when (language.lowercase()) {
            "python", "javascript" -> 0.8
            "kotlin", "java", "c#" -> 1.0
            "c++", "rust" -> 1.3
            else -> 1.0
        }
        
        val frameworkBonus = if (framework.isNotEmpty()) 1.2 else 1.0
        
        return baseTime * languageMultiplier * frameworkBonus * (1 + (priority.level - 2) * 0.2)
    }

    override fun displayInfo() {
        val progress = getProgressPercentage()
        val estimated = getEstimatedTime()
        val frameworkInfo = if (framework.isNotEmpty()) " ($framework)" else ""
        println("Programming: $name - $language$frameworkInfo - Difficulty: $difficulty/4 (${timeSpent}h / ${estimated}h) - %${"%.1f".format(progress)}")
    }

    fun getLanguageCategory(): String {
        return when (language.lowercase()) {
            "python", "javascript", "typescript" -> "Script"
            "java", "kotlin", "c#", "go" -> "Compiled"
            "c++", "rust", "c" -> "System"
            "html", "css" -> "Web"
            else -> "Other"
        }
    }
}
