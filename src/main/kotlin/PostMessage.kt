package dev.dallagi.socialnetwork

class PostMessage(private val messagesRepository: MessagesRepository, private val clock: Clock) : CommandHandler {
    override fun canHandle(command: String): Boolean {
        return " -> " in command
    }

    override fun handle(command: String) {
        val (user, messageBody) = parse(command)
        messagesRepository.addMessageToTimeline(user, Message(messageBody, clock.now()))
    }

    private fun parse(command: String) = command.split(" -> ", limit = 2)
}