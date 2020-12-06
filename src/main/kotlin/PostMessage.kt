package dev.dallagi.socialnetwork

class PostMessage(private val messagesRepository: MessagesRepository, private val clock: Clock) : CommandHandler {
    override fun canHandle(command: String): Boolean {
        return SEPARATOR in command
    }

    override fun handle(command: String) {
        val (user, messageBody) = parse(command)
        messagesRepository.addMessage(Message(user, messageBody, clock.now()))
    }

    private fun parse(command: String) = command.split(SEPARATOR, limit = 2)

    companion object {
        const val SEPARATOR = " -> "
    }
}