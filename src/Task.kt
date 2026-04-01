abstract class Task(
    var name: String,
    var description: String,
    var timeSpent: Float = 0f
) {
    abstract fun displayInfo()
}