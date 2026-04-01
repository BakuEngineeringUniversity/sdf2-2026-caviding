class DesignTask(name: String, description: String, val designType: String,
                 private val baseTime: Float) : Task(name, description), TimeEstimator {

    override fun getEstimatedTime(): Float {
        return when (designType.lowercase()) {
            "ui" -> baseTime * 1.2f
            "ux" -> baseTime * 1.5f
            else -> baseTime
        }
    }

    override fun displayInfo() {
        println("Design: $name - $designType (${timeSpent}h / ${getEstimatedTime()}h)")
    }
}