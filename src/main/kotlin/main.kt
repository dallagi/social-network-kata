package dev.dallagi.socialnetwork

fun main(args: Array<String>) {
    println("+++ Social network started +++")

    val socialNetwork = SocialNetworkFactory.create()
    while(true) {
        print("> ")
        readLine()?.let(socialNetwork::send)
    }
}