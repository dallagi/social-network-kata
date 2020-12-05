package dev.dallagi.socialnetwork

interface CommandHandler {
    fun canHandle(command: String): Boolean
    fun handle(command: String)
}
