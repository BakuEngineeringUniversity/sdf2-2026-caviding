fun main() {
    val tracker = TaskTracker()

    tracker.addTask(ProgrammingTask("Kotlin App", "Mobile app", "Kotlin"))
    tracker.addTask(WritingTask("Documentation", "API docs", 500))
    tracker.addTask(DesignTask("UI Design", "App interface", "UI", 10f))

    while (true) {
        println("\n1. View tasks  2.Add task  3. Add time   4. Remove task  5. Exit")
        print("Choice: ")

        when (readlnOrNull()) {
            "1" -> tracker.displayTasks()
            "2" -> {
                print("Which type of task (1. Programming, 2. Writing, 3. Design): ")
                val choice = readlnOrNull()?.toIntOrNull() ?: 0

                if (choice !in 1..3) {
                    println("Invalid choice!")
                    continue
                }
                when (choice) {
                    1 -> {
                        print("Name: ")
                        val name = readlnOrNull() ?: ""
                        print("Description: ")
                        val description = readlnOrNull() ?: ""
                        print("Language: ")
                        val language = readlnOrNull() ?: ""

                        tracker.addTask(ProgrammingTask(name, description, language))
                    }

                    2 -> {
                        print("Name: ")
                        val name = readlnOrNull() ?: ""
                        print("Description: ")
                        val description = readlnOrNull() ?: ""
                        print("Language: ")
                        val wordCount = readlnOrNull()?.toIntOrNull() ?: 0

                        tracker.addTask(WritingTask(name, description, wordCount))
                    }

                    3 -> {
                        print("Name: ")
                        val name = readlnOrNull() ?: ""
                        print("Description: ")
                        val description = readlnOrNull() ?: ""
                        print("Design type (UI, UX): ")
                        val designType = readlnOrNull() ?: ""
                        print("Base time (hours): ")
                        val baseTime = readlnOrNull()?.toFloatOrNull() ?: 0f

                        tracker.addTask(DesignTask(name, description, designType, baseTime))
                    }
                }

            }
            "3" -> {
                tracker.displayTasks()
                print("Task number: ")
                val index = readlnOrNull()?.toIntOrNull()?.minus(1) ?: -1
                if (index in tracker.tasks.indices) {
                    print("Hours to add: ")
                    val hours = readlnOrNull()?.toFloatOrNull() ?: 0f
                    tracker.tasks[index].timeSpent += hours
                    println("Time added!")
                }
            }
            "4" -> {
                tracker.displayTasks()
                print("Task number to remove: ")
                val index = readlnOrNull()?.toIntOrNull()?.minus(1) ?: -1
                tracker.removeTask(index)
            }
            "5" -> break
            else -> println("Invalid!")
        }
    }
}