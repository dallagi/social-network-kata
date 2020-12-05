package dev.dallagi.socialnetwork

class ReadTimeline(
    private val timeHumanizer: TimeHumanizer,
    private val messagesRepository: MessagesRepository,
    private val console: Console
) : CommandHandler {
    override fun canHandle(command: String) = " " !in command

    override fun handle(command: String) {
        val user = command
        for (message in messagesForUserFromNewestToOldest(user)) {
            console.printLine(format(message))
        }
    }

    private fun format(message: Message) =
        "${message.body} (${timeHumanizer.humanize(message.time)})"

    private fun messagesForUserFromNewestToOldest(user: String) =
        messagesRepository
            .allMessagesOnTimeline(user)
            .sortedByDescending { it.time }
}