class ProgrammingTask(name: String, description: String, val language: String) :
    Task(name, description) {

    override fun displayInfo() {
        println("Programming: $name - $language (${timeSpent}h)")
    }
}
