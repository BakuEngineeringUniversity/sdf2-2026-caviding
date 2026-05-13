import DesignType

class DesignTask(
    name: String,
    description: String,
    val designType: DesignType,
    private val baseTime: Double,
    val tools: List<String> = emptyList(),
    complexityLevel: Int = 1
) : CreativeTask(name, description, complexityLevel) {

    override val taskType = TaskType.DESIGN

    override fun getComplexityFactor(): Double {
        val baseFactor = when (complexityLevel) {
            1 -> 0.7
            2 -> 1.0
            3 -> 1.4
            4 -> 2.0
            else -> 1.0
        }
        
        val typeFactor = when (designType) {
            DesignType.UI -> 1.0
            DesignType.UX -> 1.3
            DesignType.GRAPHIC -> 0.8
            DesignType.ANIMATION -> 1.5
            DesignType.PROTOTYPE -> 1.2
        }
        
        val toolsBonus = when {
            tools.size >= 3 -> 1.3f
            tools.size >= 2 -> 1.15f
            tools.size >= 1 -> 1.05f
            else -> 1.0f
        }
        
        return baseFactor * typeFactor * toolsBonus
    }

    override fun getBaseEstimatedTime(): Double {
        return baseTime
    }

    override fun displayInfo() {
        val progress = getProgressPercentage()
        val estimated = getEstimatedTime()
        val toolsInfo = if (tools.isNotEmpty()) " - Tools: ${tools.joinToString(", ")}" else ""
        println("Design: $name - ${designType.displayName} - ${getComplexityDescription()} (${timeSpent}h / ${estimated}h) - %${"%.1f".format(progress)}$toolsInfo")
    }

    override fun getTaskTypeDisplay(): String = "Design"

    fun getDesignProcess(): List<String> {
        return when (designType) {
            DesignType.UI -> listOf("Wireframe", "Mockup", "Style Guide", "Components")
            DesignType.UX -> listOf("Research", "Personas", "User Flows", "Testing")
            DesignType.GRAPHIC -> listOf("Brief", "Concept", "Design", "Revision")
            DesignType.ANIMATION -> listOf("Storyboard", "Keyframes", "Timing", "Rendering")
            DesignType.PROTOTYPE -> listOf("Concept", "Wireframe", "Interactive", "Testing")
        }
    }
}

