import dev.dallagi.socialnetwork.*
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

class ReadWallTest {
    private val console = mockk<Console>(relaxed = true)
    private val messagesRepository = mockk<MessagesRepository>()
    private val followersRepository = mockk<FollowersRepository>()
    private val timeHumanizer = TimeHumanizer {
        when (it) {
            thirtySecondsAgo -> "30 seconds ago"
            oneMinuteAgo -> "1 minute ago"
            tenMinutesAgo -> "10 minutes ago"
            else -> ""
        }
    }

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `can handle "read wall" commands`() {
        assertTrue(ReadWall(console, messagesRepository, followersRepository, timeHumanizer).canHandle("Alice wall"))
    }

    @Test
    fun `does not handle any other command`() {
        assertFalse(ReadWall(console, messagesRepository, followersRepository, timeHumanizer).canHandle("Alice -> message"))
    }

    @Test
    fun `always prints messages for target user`() {
        val messagesForAlice = listOf(Message("Alice", "hi there!", oneMinuteAgo))
        every { messagesRepository.allMessagesOnTimeline("Alice") } returns messagesForAlice
        every { followersRepository.followersOf(any()) } returns emptyList()

        ReadWall(console, messagesRepository, followersRepository, timeHumanizer).handle("Alice wall")

        verify(exactly = 1) {
            console.printLine("Alice - hi there! (1 minute ago)")
        }
    }

    @Test
    fun `prints all messages for all people followed by the target user`() {
        val messagesForAlice = listOf(Message("Alice", "hello Alice!", oneMinuteAgo))
        val messagesForCharlie = listOf(
            Message("Charlie", "hello Charlie!", thirtySecondsAgo),
            Message("Charlie", "another message", tenMinutesAgo)
        )
        every { messagesRepository.allMessagesOnTimeline("Alice") } returns messagesForAlice
        every { messagesRepository.allMessagesOnTimeline("Charlie") } returns messagesForCharlie
        every { followersRepository.followersOf("Alice") } returns listOf("Charlie")

        ReadWall(console, messagesRepository, followersRepository, timeHumanizer).handle("Alice wall")

        verifySequence {
            console.printLine("Charlie - hello Charlie! (30 seconds ago)")
            console.printLine("Alice - hello Alice! (1 minute ago)")
            console.printLine("Charlie - another message (10 minutes ago)")
        }
    }

    companion object {
        private val thirtySecondsAgo = Instant.parse("2020-10-10T10:09:30Z")
        private val oneMinuteAgo = Instant.parse("2020-10-10T10:09:00Z")
        private val tenMinutesAgo = Instant.parse("2020-10-10T10:00:00Z")
    }
}