package com.example.tweetify

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tweetify.api.TweetifyApi
import com.example.tweetify.screens.BookmarkScreen
import com.example.tweetify.screens.CategoryScreen
import com.example.tweetify.screens.DetailScreen
import com.example.tweetify.ui.theme.TweetifyTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tweetifyApi : TweetifyApi

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        GlobalScope.launch {
//            val response = tweetifyApi.getCategories()
//            Log.d("Yoohi", response.body()?.distinct().toString())
//        }

        enableEdgeToEdge()
        setContent {
            TweetifyTheme {
                // A surface container using the 'background' color from the theme
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                val navController = rememberNavController()
                val currentRoute by navController.currentBackStackEntryAsState()
                //Nav Drawer
                val coroutine = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        ModalDrawerSheet (
                            drawerContainerColor = Color(0xFFA1BE95),
                        ){
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFFA1BE95))
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {
                                Text(text = "")
                            }
                            HorizontalDivider()
                            NavigationDrawerItem(label = { Text("Home", color = Color.Black) },
                                selected = false,
                                icon = {
                                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.Black)
                                },
                                onClick = {
                                    coroutine.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate("category") {
                                        popUpTo(0)
                                    }
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = Color(0xFFA1BE95),
                                    unselectedContainerColor = Color(0xFFA1BE95),
                                )
                            )

                            NavigationDrawerItem(label = { Text("Bookmarks", color = Color.Black) },
                                selected = false,
                                icon = {
                                    Icon(Icons.Default.Favorite, contentDescription = "Bookmarks", tint = Color.Black)
                                },
                                onClick = {
                                    coroutine.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate("bookmarks") {
                                    }
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = Color(0xFFA1BE95),
                                    unselectedContainerColor = Color(0xFFA1BE95),
                                )
                            )
                        }
                    },
                ) {
                    Scaffold(
                        topBar = {
                            val coroutine = rememberCoroutineScope()
                            TopAppBar(
                                title = {
                                    Text(text = "Tweetify", fontWeight = FontWeight.Bold, color = Color(0xFF000000))
                                },
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFA1BE95)),
                                navigationIcon = @androidx.compose.runtime.Composable {
                                    val icon = when (currentRoute?.destination?.route){
                                        "category" -> Icons.Filled.Menu
                                        else -> Icons.AutoMirrored.Filled.ArrowBack
                                    }
                                    IconButton(onClick = {
                                        if(currentRoute?.destination?.route == "category"){
                                            // Open drawer
                                            coroutine.launch {
                                                drawerState.open()
                                            }
                                        }
                                        else{
                                            navController.popBackStack()
                                        }
                                    }) {
                                        Icon(icon, contentDescription = "category", tint = Color(0xFF000000))
                                    }
                                },
                                scrollBehavior = scrollBehavior,
                            )
                        }
                    ) {
                        Box(modifier = Modifier.padding(it)){
                            App(navController)
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun App(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "category" ) {
      composable(route = "category"){
        CategoryScreen {
            navController.navigate("detail/$it")
        }
      }
        composable(route = "bookmarks"){
            BookmarkScreen()
        }

        composable(route = "detail/{category}",
            arguments = listOf(
                navArgument("category"){
                    type = NavType.StringType
                }
            )
        ){
            DetailScreen()
        }
    }
}

