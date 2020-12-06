import dev.dallagi.socialnetwork.InMemoryFollowersRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InMemoryFollowersRepositoryTest {
    @Test
    fun `by default, users have no followers`() {
        val followersRepository = InMemoryFollowersRepository()

        assertIterableEquals(emptyList<String>(), followersRepository.followersOf("AnyUser"))
    }

    @Test
    fun `stores followers for user`() {
        val followersRepository = InMemoryFollowersRepository()

        followersRepository.addFollower("Follower", "Followee")

        assertIterableEquals(listOf("Follower"), followersRepository.followersOf("Followee"))
    }
}