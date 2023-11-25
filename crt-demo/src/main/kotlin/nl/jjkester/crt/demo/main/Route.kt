package nl.jjkester.crt.demo.main

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface RouteTemplate {
    val routeTemplate: String
    val navArguments: List<NamedNavArgument>
}

interface NavigableRoute {
    val route: String
}

abstract class SingletonRoute(final override val route: String) : RouteTemplate, NavigableRoute {
    final override val routeTemplate: String = route
    final override val navArguments: List<NamedNavArgument> = emptyList()

    override fun toString(): String = "${javaClass.simpleName}()"
}

sealed interface Route : NavigableRoute {

    object Index : Route, SingletonRoute("index")

    object Readme : Route, SingletonRoute("readme")

    data class ShowcaseOverview(val showcaseIndex: Int) : Route {
        override val route: String = "showcase/$showcaseIndex"

        companion object : RouteTemplate {
            override val routeTemplate: String = "showcase/{showcaseIndex}"
            override val navArguments: List<NamedNavArgument> = listOf(
                navArgument("showcaseIndex") { type = NavType.IntType }
            )
        }
    }

    data class ShowcaseExample(val showcaseIndex: Int, val exampleIndex: Int) : Route {
        override val route: String = "showcase/$showcaseIndex/example/$exampleIndex"

        companion object : RouteTemplate {
            override val routeTemplate: String = "showcase/{showcaseIndex}/example/{exampleIndex}"
            override val navArguments: List<NamedNavArgument> = listOf(
                navArgument("showcaseIndex") { type = NavType.IntType },
                navArgument("exampleIndex") { type = NavType.IntType }
            )
        }
    }

    data class ShowcaseEditor(val showcaseIndex: Int) : Route {
        override val route: String = "showcase/$showcaseIndex/editor"

        companion object : RouteTemplate {
            override val routeTemplate: String = "showcase/{showcaseIndex}/editor"
            override val navArguments: List<NamedNavArgument> = listOf(
                navArgument("showcaseIndex") { type = NavType.IntType }
            )
        }
    }
}
