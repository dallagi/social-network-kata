package dev.dallagi.socialnetwork

class InMemoryFollowersRepository : FollowersRepository {
    private var followedBy = mutableMapOf<String, MutableSet<String>>()

    override fun addFollower(follower: String, followee: String) {
        getFollowedBy(follower).add(followee)
    }

    override fun followedBy(user: String): Collection<String> {
        return followedBy.getOrDefault(user, emptySet())
    }

    private fun getFollowedBy(user: String): MutableSet<String> {
        followedBy[user] = followedBy.getOrDefault(user, mutableSetOf())
        return followedBy[user]!!
    }
}