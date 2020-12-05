package dev.dallagi.socialnetwork

interface MessagesRepository {
    fun addMessageToTimeline(recipient: String, message: String)
    fun allMessagesOnTimeline(user: String): Collection<Message>
}