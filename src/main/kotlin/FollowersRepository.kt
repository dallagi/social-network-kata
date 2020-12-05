package dev.dallagi.socialnetwork

interface FollowersRepository {
    fun addFollower(follower: String, followee: String)
}
