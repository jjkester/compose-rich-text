package nl.jjkester.crt.demo.main

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface DestinationInfo {
    val routeTemplate: String
    val navArguments: List<NamedNavArgument>
}

interface RouteInfo {
    val value: String
}

abstract class SingletonRoute(final override val value: String) : DestinationInfo, RouteInfo {
    final override val routeTemplate: String = value
    final override val navArguments: List<NamedNavArgument> = emptyList()

    override fun toString(): String = "${javaClass.simpleName}()"
}

sealed interface Route : RouteInfo {

    object Index : Route, SingletonRoute("index")

    object Readme : Route, SingletonRoute("readme")

    data class ShowcaseOverview(val showcaseIndex: Int) : Route {
        override val value: String = "showcase/$showcaseIndex"

        companion object : DestinationInfo {
            override val routeTemplate: String = "showcase/{showcaseIndex}"
            override val navArguments: List<NamedNavArgument> = listOf(
                navArgument("showcaseIndex") { type = NavType.IntType }
            )
        }
    }

    data class ShowcaseExample(val showcaseIndex: Int, val exampleIndex: Int) : Route {
        override val value: String = "showcase/$showcaseIndex/example/$exampleIndex"

        companion object : DestinationInfo {
            override val routeTemplate: String = "showcase/{showcaseIndex}/example/{exampleIndex}"
            override val navArguments: List<NamedNavArgument> = listOf(
                navArgument("showcaseIndex") { type = NavType.IntType },
                navArgument("exampleIndex") { type = NavType.IntType }
            )
        }
    }
}
