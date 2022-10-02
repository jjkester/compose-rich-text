package nl.jjkester.crt.demo.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.jjkester.crt.demo.showcases.Showcase
import nl.jjkester.crt.demo.showcases.ShowcaseExample
import nl.jjkester.crt.demo.showcases.ShowcaseOverview
import nl.jjkester.crt.demo.showcases.ShowcaseScaffold

@Composable
fun MainNavigation(showcases: List<Showcase>) {
    val navController = rememberNavController()

    NavHost(navController = navController, Route.Index.value) {
        composable(Route.Index.value) {
            MainScreen(
                onNavigate = navController::navigateForward
            )
        }

        composable(
            route = "showcase/{showcaseIndex}",
            arguments = listOf(
                navArgument("showcaseIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val showcaseIndex = backStackEntry.arguments!!.getInt("showcaseIndex")
            val showcase = showcases[showcaseIndex]

            ShowcaseScaffold(
                title = showcase.name,
                onNavigateBack = { navController.navigateBackward(Route.Index) }
            ) {
                ShowcaseOverview(
                    showcase = showcase,
                    onExampleClick = { example ->
                        val exampleIndex = showcase.examples.indexOf(example)
                        navController.navigateForward(Route.ShowcaseExample(showcaseIndex, exampleIndex))
                    }
                )
            }
        }

        composable(
            route = "showcase/{showcaseIndex}/example/{exampleIndex}",
            arguments = listOf(
                navArgument("showcaseIndex") { type = NavType.IntType },
                navArgument("exampleIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val showcaseIndex = backStackEntry.arguments!!.getInt("showcaseIndex")
            val exampleIndex = backStackEntry.arguments!!.getInt("exampleIndex")
            val showcase = showcases[showcaseIndex]
            val example = showcase.examples[exampleIndex]

            ShowcaseScaffold(
                title = example.name,
                onNavigateBack = { navController.navigateBackward(Route.ShowcaseOverview(showcaseIndex)) }
            ) {
                ShowcaseExample(
                    example = example
                )
            }
        }
    }
}

private fun NavController.navigateForward(route: Route) {
    navigate(route.value)
}

private fun NavController.navigateBackward(route: Route) {
    navigate(route.value) {
        popUpTo(route.value)
        launchSingleTop = true
        restoreState = true
    }
}
