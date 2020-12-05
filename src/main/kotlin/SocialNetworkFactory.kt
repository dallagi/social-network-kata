package dev.dallagi.socialnetwork

import java.time.Instant

object SocialNetworkFactory {
    fun create(
        console: Console = Console(::println),
        writeClock: Clock = Clock(Instant::now),
        readClock: Clock = Clock(Instant::now)
    ): SocialNetwork {
        val messagesRepository = InMemoryMessagesRepository()
        val readTimeline = ReadTimeline(NMinutesAgoTimeHumanizer(readClock), messagesRepository, console)
        val postMessage = PostMessage(messagesRepository, writeClock)

        return SocialNetwork(listOf(postMessage, readTimeline))
    }
}