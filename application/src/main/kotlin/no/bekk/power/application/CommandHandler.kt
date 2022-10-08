package no.bekk.power.application

interface CommandHandler<T> {

    fun handle(command: T)
}