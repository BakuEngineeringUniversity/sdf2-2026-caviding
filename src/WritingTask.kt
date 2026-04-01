class WritingTask(name: String, description: String, val wordCount: Int) :
    Task(name, description) {

    override fun displayInfo() {
        println("Writing: $name - $wordCount words (${timeSpent}h)")
    }
}