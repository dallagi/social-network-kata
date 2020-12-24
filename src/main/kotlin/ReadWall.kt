package dev.dallagi.socialnetwork

class ReadWall(
    private val console: Console,
    private val messagesRepository: MessagesRepository,
    private val followersRepository: FollowersRepository,
    private val timeHumanizer: TimeHumanizer
) : CommandHandler {
    override fun canHandle(command: String) = command.endsWith(SUFFIX)

    override fun handle(command: String) {
        val user = userFrom(command)
        val timelinesToCheck = listOf(user) + followersRepository.followedBy(user)

        for (message in allMessagesFor(timelinesToCheck)) {
            console.printLine(format(message))
        }
    }

    private fun allMessagesFor(timelinesToCheck: Collection<String>) =
        timelinesToCheck
            .flatMap(messagesRepository::allMessagesOnTimeline)
            .sortedByDescending(Message::time)

    private fun userFrom(command: String) = command.removeSuffix(SUFFIX)

    private fun format(message: Message) =
        "${message.recipient} - ${message.body} (${timeHumanizer.humanize(message.time)})"

    companion object {
        private const val SUFFIX = " wall"
    }
}
