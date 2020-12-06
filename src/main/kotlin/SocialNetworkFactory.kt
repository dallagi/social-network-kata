package dev.dallagi.socialnetwork

import java.time.Instant

object SocialNetworkFactory {
    fun create(console: Console = Console(::println), clock: Clock = Clock(Instant::now)): SocialNetwork {
        val messagesRepository = InMemoryMessagesRepository()
        val followersRepository = InMemoryFollowersRepository()
        val timeHumanizer = TimeAgoHumanizer(clock)

        val commandHandlers = listOf(
            PostMessage(messagesRepository, clock),
            ReadTimeline(timeHumanizer, messagesRepository, console),
            FollowUser(followersRepository),
            ReadWall(console, messagesRepository, followersRepository, timeHumanizer)
        )

        return SocialNetwork(commandHandlers)
    }
}