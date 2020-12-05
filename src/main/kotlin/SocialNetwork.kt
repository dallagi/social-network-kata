package dev.dallagi.socialnetwork

class SocialNetwork(private val commandHandlers: List<CommandHandler>) {
    fun send(command: String) {
        commandHandlers
            .find { it.canHandle(command) }
            ?.handle(command)
    }
}
