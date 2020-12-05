import dev.dallagi.socialnetwork.CommandHandler
import dev.dallagi.socialnetwork.SocialNetwork
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class SocialNetworkTest {
    @Test
    fun `delegates command processing to a command handler that supports that command`() {
        val commandToInvoke = mockk<CommandHandler>(relaxed = true)
        val commandNotToInvoke = mockk<CommandHandler>(relaxed = true)
        every { commandToInvoke.canHandle("my-command") }.returns(true)
        every { commandNotToInvoke.canHandle("my-command") }.returns(false)

        val socialNetwork = SocialNetwork(listOf(commandToInvoke, commandNotToInvoke))
        socialNetwork.send("my-command")

        verify(exactly = 1) { commandToInvoke.handle("my-command") }
        verify { commandNotToInvoke wasNot Called }
    }
}