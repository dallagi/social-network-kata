package dev.dallagi.socialnetwork

class FollowUser(private val followersRepository: FollowersRepository) : CommandHandler {
    override fun canHandle(command: String) = SEPARATOR in command

    override fun handle(command: String) {
        val (follower, followee) = command.split(SEPARATOR, limit = 2)
        followersRepository.addFollower(follower, followee)
    }

    companion object {
        const val SEPARATOR = " follows "
    }
}