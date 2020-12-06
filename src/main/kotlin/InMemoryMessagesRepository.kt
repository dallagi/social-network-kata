package dev.dallagi.socialnetwork

class InMemoryMessagesRepository : MessagesRepository {
    private val timelines = mutableMapOf<String, MutableList<Message>>()

    override fun addMessage(message: Message) {
        getTimelineFor(message.recipient).add(message)
    }

    override fun allMessagesOnTimeline(user: String): Collection<Message> {
        return timelines.getOrDefault(user, mutableListOf())
    }

    private fun getTimelineFor(user: String): MutableList<Message> {
        timelines[user] = timelines.getOrDefault(user, mutableListOf())
        return timelines[user]!!
    }
}