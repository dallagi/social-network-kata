package dev.dallagi.socialnetwork

class InMemoryFollowersRepository : FollowersRepository {
    private var followers = mutableMapOf<String, MutableSet<String>>()

    override fun addFollower(follower: String, followee: String) {
        getFollowersFor(followee).add(follower)
    }

    override fun followersOf(user: String): Collection<String> {
        return followers.getOrDefault(user, emptySet())
    }

    private fun getFollowersFor(user: String): MutableSet<String> {
        followers[user] = followers.getOrDefault(user, mutableSetOf())
        return followers[user]!!
    }
}