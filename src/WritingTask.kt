class WritingTask(
    name: String,
    description: String,
    val wordCount: Int,
    val writingType: WritingType = WritingType.ARTICLE,
    complexityLevel: Int = 1
) : CreativeTask(name, description, complexityLevel) {

    override val taskType = TaskType.WRITING

    override fun getComplexityFactor(): Double {
        val baseFactor = when (complexityLevel) {
            1 -> 0.8
            2 -> 1.0
            3 -> 1.3
            4 -> 1.6
            else -> 1.0
        }
        
        val typeFactor = when (writingType) {
            WritingType.ARTICLE -> 1.0
            WritingType.BLOG_POST -> 0.7
            WritingType.DOCUMENTATION -> 1.4
            WritingType.REPORT -> 1.6
            WritingType.CREATIVE -> 1.2
        }
        
        return baseFactor * typeFactor
    }

    override fun getBaseEstimatedTime(): Double {
        val wordsPerHour = when (writingType) {
            WritingType.ARTICLE -> 500
            WritingType.BLOG_POST -> 600
            WritingType.DOCUMENTATION -> 300
            WritingType.REPORT -> 400
            WritingType.CREATIVE -> 350
        }
        
        return (wordCount.toDouble() / wordsPerHour)
    }

    override fun displayInfo() {
        val progress = getProgressPercentage()
        val estimated = getEstimatedTime()
        println("Writing: $name - ${writingType.displayName} - $wordCount words - ${getComplexityDescription()} (${timeSpent}h / ${estimated}h) - %${"%.1f".format(progress)}")
    }

    override fun getTaskTypeDisplay(): String = "Writing"

    fun getReadingTime(): String {
        val wordsPerMinute = 200
        val minutes = (wordCount / wordsPerMinute)
        return if (minutes < 60) "${minutes} minutes" else "${minutes / 60} hours ${minutes % 60} minutes"
    }
}

enum class WritingType(val displayName: String) {
    ARTICLE("Article"),
    BLOG_POST("Blog Post"),
    DOCUMENTATION("Documentation"),
    REPORT("Report"),
    CREATIVE("Creative Writing")
}