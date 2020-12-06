package dev.dallagi.socialnetwork

import java.time.Instant

object SocialNetworkFactory {
    fun create(console: Console = Console(::println), clock: Clock = Clock(Instant::now)): SocialNetwork {
        val messagesRepository = InMemoryMessagesRepository()
        val readTimeline = ReadTimeline(TimeAgoHumanizer(clock), messagesRepository, console)
        val postMessage = PostMessage(messagesRepository, clock)

        return SocialNetwork(listOf(postMessage, readTimeline))
    }
}