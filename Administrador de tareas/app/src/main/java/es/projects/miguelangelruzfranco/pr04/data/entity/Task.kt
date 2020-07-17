package es.projects.miguelangelruzfranco.pr04.data.entity

data class Task (val id: Long, val concept: String, val createdAt: String, var completed: Boolean, var completedAt: String) {

    fun getText(): String {
        return if (!completed) {
            createdAt
        } else {
            completedAt
        }
    }
}