package enums

enum class TaskType {
    PROGRAMMING,
    WRITING,
    DESIGN
}

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    ON_HOLD
}

enum class Priority(val level: Int, val displayName: String) {
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High"),
    URGENT(4, "Urgent")
}
