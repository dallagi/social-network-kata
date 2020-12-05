package dev.dallagi.socialnetwork

class ReadTimeline(
    private val timeHumanizer: TimeHumanizer,
    private val messagesRepository: MessagesRepository,
    private val console: Console
) : CommandHandler {
    override fun canHandle(command: String): Boolean {
        return " " !in command
    }

    override fun handle(command: String) {
        val user = command
        for (message in messagesRepository.allMessagesOnTimeline(user).sortedByDescending { it.time }) {
            console.printLine("${message.body} (${timeHumanizer.humanize(message.time)})")
        }
    }
}