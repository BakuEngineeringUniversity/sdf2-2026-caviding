import DesignType

fun main() {
    val tracker = TaskTracker()

    tracker.addTask(ProgrammingTask("Kotlin Mobile App", "Android app development", "Kotlin", "Android Studio", 2))
    tracker.addTask(WritingTask("API Documentation", "REST API documentation", 1500, WritingType.DOCUMENTATION, 2))
    tracker.addTask(DesignTask("UI Design", "Mobile app interface", DesignType.UI, 15.0, listOf("Figma", "Adobe XD"), 2))

    while (true) {
        displayMainMenu()

        when (readlnOrNull()) {
            "1" -> tracker.displayTasks()
            "2" -> addTaskMenu(tracker)
            "3" -> taskOperationsMenu(tracker)
            "4" -> timeManagementMenu(tracker)
            "5" -> filterAndSortMenu(tracker)
            "6" -> println(tracker.getStatistics())
            "7" -> break
            else -> println("Invalid choice!")
        }
    }
}

fun displayMainMenu() {
    println("\n=== TASK TRACKING SYSTEM ===")
    println("1. View Tasks")
    println("2. Add New Task")
    println("3. Task Operations")
    println("4. Time Management")
    println("5. Filter and Sort")
    println("6. Statistics")
    println("7. Exit")
    print("Your choice: ")
}

fun addTaskMenu(tracker: TaskTracker) {
    println("\n--- ADD NEW TASK ---")
    println("1. Programming Task")
    println("2. Writing Task")
    println("3. Design Task")
    print("Task type: ")

    when (readlnOrNull()) {
        "1" -> addProgrammingTask(tracker)
        "2" -> addWritingTask(tracker)
        "3" -> addDesignTask(tracker)
        else -> println("Invalid task type!")
    }
}

fun addProgrammingTask(tracker: TaskTracker) {
    try {
        print("Task name: ")
        val name = readlnOrNull() ?: throw IllegalArgumentException("Name cannot be null")
        print("Description: ")
        val description = readlnOrNull() ?: ""
        print("Programming language: ")
        val language = readlnOrNull() ?: throw IllegalArgumentException("Language cannot be null")
        print("Framework (optional): ")
        val framework = readlnOrNull() ?: ""
        print("Difficulty (1-4): ")
        val difficulty = readlnOrNull()?.toIntOrNull()?.takeIf { it in 1..4 }
            ?: throw IllegalArgumentException("Difficulty must be between 1 and 4")
        print("Priority (1-Low, 2-Medium, 3-High, 4-Urgent): ")
        val priorityLevel = readlnOrNull()?.toIntOrNull() ?: 2

        val task = ProgrammingTask(name, description, language, framework, difficulty)
        task.priority = Priority.values().find { it.level == priorityLevel } ?: Priority.MEDIUM
        tracker.addTask(task)
    } catch (e: IllegalArgumentException) {
        println("Error creating programming task: ${e.message}")
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
    }
}

fun addWritingTask(tracker: TaskTracker) {
    try {
        print("Task name: ")
        val name = readlnOrNull() ?: throw IllegalArgumentException("Name cannot be null")
        print("Description: ")
        val description = readlnOrNull() ?: ""
        print("Word count: ")
        val wordCount = readlnOrNull()?.toIntOrNull()?.takeIf { it > 0 }
            ?: throw IllegalArgumentException("Word count must be a positive number")
        println("Writing type:")
        WritingType.values().forEachIndexed { i, type ->
            println("${i + 1}. ${type.displayName}")
        }
        print("Your choice: ")
        val writingTypeIndex = readlnOrNull()?.toIntOrNull()?.minus(1) ?: 0
        val writingType = WritingType.values().getOrNull(writingTypeIndex) ?: WritingType.ARTICLE
        print("Complexity (1-4): ")
        val complexity = readlnOrNull()?.toIntOrNull()?.takeIf { it in 1..4 } ?: 1
        print("Priority (1-Low, 2-Medium, 3-High, 4-Urgent): ")
        val priorityLevel = readlnOrNull()?.toIntOrNull() ?: 2

        val task = WritingTask(name, description, wordCount, writingType, complexity)
        task.priority = Priority.values().find { it.level == priorityLevel } ?: Priority.MEDIUM
        tracker.addTask(task)
    } catch (e: IllegalArgumentException) {
        println("Error creating writing task: ${e.message}")
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
    }
}

fun addDesignTask(tracker: TaskTracker) {
    try {
        print("Task name: ")
        val name = readlnOrNull() ?: throw IllegalArgumentException("Name cannot be null")
        print("Description: ")
        val description = readlnOrNull() ?: ""
        println("Design type:")
        DesignType.values().forEachIndexed { i, type ->
            println("${i + 1}. ${type.displayName}")
        }
        print("Your choice: ")
        val designTypeIndex = readlnOrNull()?.toIntOrNull()?.minus(1) ?: 0
        val designType = DesignType.values().getOrNull(designTypeIndex) ?: DesignType.UI
        print("Estimated time (hours): ")
        val baseTime = readlnOrNull()?.toDoubleOrNull()?.takeIf { it > 0 }
            ?: throw IllegalArgumentException("Base time must be a positive number")
        print("Tools (comma separated): ")
        val toolsInput = readlnOrNull() ?: ""
        val tools = toolsInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        print("Complexity (1-4): ")
        val complexity = readlnOrNull()?.toIntOrNull()?.takeIf { it in 1..4 } ?: 1
        print("Priority (1-Low, 2-Medium, 3-High, 4-Urgent): ")
        val priorityLevel = readlnOrNull()?.toIntOrNull() ?: 2

        val task = DesignTask(name, description, designType, baseTime, tools, complexity)
        task.priority = Priority.values().find { it.level == priorityLevel } ?: Priority.MEDIUM
        tracker.addTask(task)
    } catch (e: IllegalArgumentException) {
        println("Error creating design task: ${e.message}")
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
    }
}

fun taskOperationsMenu(tracker: TaskTracker) {
    try {
        tracker.displayTasks()
        print("Task number: ")
        val index = readlnOrNull()?.toIntOrNull()?.minus(1)
            ?: throw IllegalArgumentException("Please enter a valid number")

        if (index < 0 || index >= tracker.getAllTasks().size) {
            throw IndexOutOfBoundsException("Invalid task number: ${index + 1}")
        }

        println("\n--- TASK OPERATIONS ---")
        println("1. Start Task")
        println("2. Complete Task")
        println("3. Pause Task")
        println("4. Remove Task")
        print("Your choice: ")

        when (readlnOrNull()) {
            "1" -> tracker.startTask(index)
            "2" -> tracker.completeTask(index)
            "3" -> tracker.pauseTask(index)
            "4" -> tracker.removeTask(index)
            else -> println("Invalid choice!")
        }
    } catch (e: IllegalArgumentException) {
        println("Input error: ${e.message}")
    } catch (e: IndexOutOfBoundsException) {
        println("Error: ${e.message}")
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
    }
}

fun timeManagementMenu(tracker: TaskTracker) {
    try {
        tracker.displayTasks()
        print("Task number: ")
        val index = readlnOrNull()?.toIntOrNull()?.minus(1)
            ?: throw IllegalArgumentException("Please enter a valid number")

        if (index < 0 || index >= tracker.getAllTasks().size) {
            throw IndexOutOfBoundsException("Invalid task number: ${index + 1}")
        }

        print("Hours to add: ")
        val hours = readlnOrNull()?.toDoubleOrNull()
            ?: throw IllegalArgumentException("Please enter a valid number of hours")

        tracker.addTimeToTask(index, hours)
    } catch (e: IllegalArgumentException) {
        println("Input error: ${e.message}")
    } catch (e: IndexOutOfBoundsException) {
        println("Error: ${e.message}")
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
    }
}

fun filterAndSortMenu(tracker: TaskTracker) {
    try {
        println("\n--- FILTER AND SORT ---")
        println("1. Filter by Status")
        println("2. Filter by Priority")
        println("3. Filter by Task Type")
        println("4. Show All Tasks")
        print("Your choice: ")

        when (readlnOrNull()) {
            "1" -> {
                println("\nSelect status:")
                TaskStatus.values().forEachIndexed { i, status ->
                    println("${i + 1}. $status")
                }
                print("Your choice: ")
                val statusIndex = readlnOrNull()?.toIntOrNull()?.minus(1) ?: 0
                val status = TaskStatus.values().getOrNull(statusIndex)
                    ?: throw IllegalArgumentException("Invalid status selection")
                val tasks = tracker.getTasksByStatus(status)
                println("\n$status Tasks:")
                tasks.forEachIndexed { i, task ->
                    println("${i + 1}. ${task.name} - ${task.timeSpent}h")
                }
            }
            "2" -> tracker.displayTasksByPriority()
            "3" -> {
                println("\nSelect task type:")
                TaskType.values().forEachIndexed { i, type ->
                    println("${i + 1}. $type")
                }
                print("Your choice: ")
                val typeIndex = readlnOrNull()?.toIntOrNull()?.minus(1) ?: 0
                val type = TaskType.values().getOrNull(typeIndex)
                    ?: throw IllegalArgumentException("Invalid task type selection")
                val tasks = tracker.getTasksByType(type)
                println("\n$type Tasks:")
                tasks.forEachIndexed { i, task ->
                    println("${i + 1}. ${task.name} - ${task.status}")
                }
            }
            "4" -> tracker.displayTasks()
            else -> println("Invalid choice!")
        }
    } catch (e: IllegalArgumentException) {
        println("Input error: ${e.message}")
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
    }
}
