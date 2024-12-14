package me.tbsten.prac.websocketdebugtool

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform