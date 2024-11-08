package uz.gita.contactappfirestore.utils

// This is a custom event wrapper for handling one-time events in LiveData
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content if it hasn’t been handled and prevents further use.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
