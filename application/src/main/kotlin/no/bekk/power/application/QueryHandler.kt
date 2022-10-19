package no.bekk.power.application

interface QueryHandler<T, U> {

    fun handle(query: T): U

}
