package no.bekk.power.application

interface QueryHandler<T, U> {

    fun run(query: T): U

}