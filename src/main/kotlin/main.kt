package dev.dallagi.socialnetwork

fun main(args: Array<String>) {
    println("Starting social network...")
    val socialNetwork = SocialNetworkFactory.create()

    while(true) {
        print("> ")
        readLine()?.let(socialNetwork::send)
    }
}