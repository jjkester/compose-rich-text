package nl.jjkester.crt.demo.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.jjkester.crt.demo.R
import nl.jjkester.crt.demo.markdown.LazyMarkdown
import nl.jjkester.crt.demo.openRawResource
import nl.jjkester.crt.demo.showcases.Showcase
import nl.jjkester.crt.demo.showcases.ShowcaseOverview
import nl.jjkester.crt.demo.showcases.ShowcaseScaffold

@Composable
fun MainNavigation(showcases: List<Showcase>) {
    val navController = rememberNavController()

    NavHost(navController = navController, Route.Index.value) {
        composable(Route.Index.value) {
            MainScreen(
                showcases = showcases,
                onNavigate = navController::navigateForward
            )
        }

        composable(Route.Readme.value) {
            ShowcaseScaffold(
                title = "Project information",
                onNavigateBack = { navController.navigateUp() }
            ) {
                val uriHandler = LocalUriHandler.current
                LazyMarkdown(
                    text = openRawResource(id = R.raw.main_readme),
                    contentPadding = PaddingValues(16.dp),
                    onClick = uriHandler::openUri
                )
            }
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
                onNavigateBack = { navController.navigateUp() }
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
                onNavigateBack = { navController.navigateUp() },
                content = example.content
            )
        }
    }
}

private fun NavController.navigateForward(route: Route) {
    navigate(route.value)
}
