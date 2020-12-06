package dev.dallagi.socialnetwork

interface MessagesRepository {
    fun addMessage(message: Message)
    fun allMessagesOnTimeline(user: String): Collection<Message>
}
