package dev.dallagi.socialnetwork

class PostMessage(private val messagesRepository: MessagesRepository) : CommandHandler {
    override fun canHandle(command: String): Boolean {
        return " -> " in command
    }

    override fun handle(command: String) {
        val (user, message) = parse(command)
        messagesRepository.addMessageToTimeline(user, message)
    }

    private fun parse(command: String) = command.split(" -> ", limit = 2)
}