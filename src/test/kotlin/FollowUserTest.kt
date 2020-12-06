package dev.dallagi.socialnetwork

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FollowUserTest {
    private val followersRepository = mockk<FollowersRepository>(relaxed = true)

    @Test
    fun `can handle "follow user" commands`() {
        assertTrue(FollowUser(followersRepository).canHandle("Charlie follows Bob"))
    }

    @Test
    fun `does not handle any other command`() {
        assertFalse(FollowUser(followersRepository).canHandle("any other message"))
    }

    @Test
    fun `register user as follower for the target user`() {
        FollowUser(followersRepository).handle("Charlie follows Bob")

        verify {
            followersRepository.addFollower(follower = "Charlie", followee = "Bob")
        }
    }
}