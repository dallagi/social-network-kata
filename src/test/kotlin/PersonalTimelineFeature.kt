import dev.dallagi.socialnetwork.Console
import dev.dallagi.socialnetwork.SocialNetwork
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test

class PersonalTimelineFeature {
    @Test
    fun `users can submit messages to personal timelines`() {
        val socialNetwork = SocialNetwork(emptyList())
        val console = mockk<Console>(relaxed = true)

        socialNetwork.send("Alice -> I love the weather today")
        socialNetwork.send("Bob -> Damn! We lost!")
        socialNetwork.send("Bob -> Good game though.")

        socialNetwork.send("Alice")
        verifySequence {
            console.printLine("I love the weather today (5 minutes ago)")
        }

        socialNetwork.send("Bob")
        verifySequence {
            console.printLine("Good game though. (1 minute ago)")
            console.printLine("Damn! We lost! (2 minutes ago)")
        }
    }
}