package dev.dallagi.socialnetwork

interface FollowersRepository {
    fun addFollower(follower: String, followee: String)
    fun followedBy(user: String): Collection<String>
}
