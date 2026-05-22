package nl.jjkester.crt.demo.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.jjkester.crt.demo.R
import nl.jjkester.crt.demo.editor.EditorScreen
import nl.jjkester.crt.demo.markdown.LazyMarkdown
import nl.jjkester.crt.demo.openRawResource
import nl.jjkester.crt.demo.showcases.Showcase
import nl.jjkester.crt.demo.showcases.ShowcaseOverview
import nl.jjkester.crt.demo.showcases.ShowcaseScaffold
import nl.jjkester.crt.demo.showcases.showcaseContentPadding

@Composable
fun MainNavigation(showcases: List<Showcase>, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Index.route,
        modifier = modifier
    ) {
        composable(Route.Index.route) {
            MainScreen(
                showcases = showcases,
                onNavigate = navController::navigateForward
            )
        }

        composable(Route.Readme.route) {
            ShowcaseScaffold(
                title = "Project information",
                onNavigateBack = { navController.navigateUp() }
            ) {
                LazyMarkdown(
                    text = openRawResource(id = R.raw.main_readme),
                    contentPadding = showcaseContentPadding
                )
            }
        }

        composable(
            route = Route.ShowcaseOverview.routeTemplate,
            arguments = Route.ShowcaseOverview.navArguments
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
                    },
                    onEditorClick = { navController.navigateForward(Route.ShowcaseEditor(showcaseIndex)) }
                )
            }
        }

        composable(
            route = Route.ShowcaseExample.routeTemplate,
            arguments = Route.ShowcaseExample.navArguments
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

        composable(
            route = Route.ShowcaseEditor.routeTemplate,
            arguments = Route.ShowcaseEditor.navArguments
        ) { backStackEntry ->
            val showcaseIndex = backStackEntry.arguments!!.getInt("showcaseIndex")
            val showcase = showcases[showcaseIndex]

            showcase.editorFormat?.also { editorFormat ->
                ShowcaseScaffold(
                    title = "${showcase.name} editor",
                    onNavigateBack = { navController.navigateUp() }
                ) {
                    EditorScreen(editorFormat)
                }
            }
        }
    }
}

private fun NavController.navigateForward(route: Route) {
    navigate(route.route)
}
