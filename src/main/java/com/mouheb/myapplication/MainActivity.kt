//package com.mouheb.myapplication
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.mouheb.myapplication.ui.theme.MyApplicationTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            MyApplicationTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Mouheb Fitness",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//        Greeting("Android")
//    }
//}




















































package com.mouheb.myapplication

import android.os.Bundle
import android.webkit.WebSettings
import androidx.compose.foundation.background
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Check

import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.vector.ImageVector
import com.mouheb.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch




import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView







class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                FitnessApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp) // Set the width for a partial drawer
            ) {
                DrawerContent(navController, drawerState)
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mouheb Fitness") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors()
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
            content = { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("home") { HomeScreen(navController) }
                    composable("workouts") { WorkoutScreen(navController) }
                    composable("tracking") { TrackingScreen() }
                    composable("exerciseDetails") { ExerciseDetailsScreen() }
                    composable("settings") { SettingsScreen() }
                    composable("exerciseVideos") { VideoPlayerPage() }
                    composable("profile") { UserProfileScreen() }
                }
            }
        )
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Workouts") },
            label = { Text("Workouts") },
            selected = navController.currentDestination?.route == "workouts",
            onClick = { navController.navigate("workouts") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Check, contentDescription = "Tracking") }, // Use Check as a placeholder
            label = { Text("Tracking") },
            selected = navController.currentDestination?.route == "tracking",
            onClick = { navController.navigate("tracking") }
        )
    }
}


@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) // Set a solid background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onSurface // Ensure text contrasts with the background
            )
            TextButton(onClick = {
                scope.launch { drawerState.close() } // Close the drawer when clicked
                navController.navigate("settings")
            }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Settings")
            }
            TextButton(onClick = {
                scope.launch { drawerState.close() } // Close the drawer when clicked
                navController.navigate("profile")
            }) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Profile")
            }
        }
    }
}



@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Mouheb Fitness!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        // Workout Card
        DashboardCard(
            title = "Workouts",
            description = "View and manage your workouts",
            icon = Icons.Default.FitnessCenter,
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = { navController.navigate("workouts") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Tracking Card
        DashboardCard(
            title = "Track Progress",
            description = "Track your daily fitness progress",
            icon = Icons.Default.Check,
            backgroundColor = MaterialTheme.colorScheme.secondary,
            onClick = { navController.navigate("tracking") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Goals Card
        DashboardCard(
            title = "Set Goals",
            description = "Set your fitness goals",
            icon = Icons.Default.Person,  // You can replace this with a more appropriate icon
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            onClick = { /* Add action for setting goals */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise Videos Card
        DashboardCard(
            title = "Exercise Videos",
            description = "Watch exercise tutorials",
            icon = Icons.Default.VideoLibrary,  // You can replace this with a more appropriate icon
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            onClick = { navController.navigate("exerciseVideos") }  // Navigate to the exercise videos page
        )
    }
}


@Composable
fun DashboardCard(
    title: String,
    description: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}






@Composable
fun WorkoutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Workout List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Push-ups Card
        WorkoutCard(
            title = "Push-ups",
            description = "Great for upper body strength.",
            icon = Icons.Default.FitnessCenter,
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = { navController.navigate("exerciseDetails") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Squats Card
        WorkoutCard(
            title = "Squats",
            description = "Strengthen your legs and core.",
            icon = Icons.Default.FitnessCenter,  // Replace with leg icon
            backgroundColor = MaterialTheme.colorScheme.secondary,
            onClick = { navController.navigate("exerciseDetails") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Burpees Card
        WorkoutCard(
            title = "Burpees",
            description = "Full-body workout that burns calories.",
            icon = Icons.Default.FitnessCenter,  // Replace with full-body icon
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            onClick = { navController.navigate("exerciseDetails") }
        )
    }
}

@Composable
fun WorkoutCard(
    title: String,
    description: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}








@Composable
fun TrackingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Track Your Progress",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Progress Card for Push-ups
        ProgressTrackingCard(
            exercise = "Push-ups",
            progress = "20 reps",
            progressPercent = 0.8f, // 80% progress
            icon = Icons.Default.FitnessCenter,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Card for Squats
        ProgressTrackingCard(
            exercise = "Squats",
            progress = "15 reps",
            progressPercent = 0.6f, // 60% progress
            icon = Icons.Default.FitnessCenter,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Card for Burpees
        ProgressTrackingCard(
            exercise = "Burpees",
            progress = "10 reps",
            progressPercent = 0.4f, // 40% progress
            icon = Icons.Default.FitnessCenter,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun ProgressTrackingCard(
    exercise: String,
    progress: String,
    progressPercent: Float,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = exercise,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = progress,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar for Tracking
            LinearProgressIndicator(
                progress = progressPercent,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                trackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
            )
        }
    }
}


@Composable
fun ProgressCard(exercise: String, progress: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = exercise, style = MaterialTheme.typography.headlineSmall)
            Text(text = progress, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

@Composable
fun DailyGoalProgressBar(current: Int, goal: Int) {
    val progress = current.toFloat() / goal
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Daily Goal: $current / $goal")
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
    }
}




@Composable
fun VideoPlayerPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Exercise Video Tutorials",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        // Push-ups Video
        ExerciseVideoCard(
            videoTitle = "Push-ups Tutorial",
            videoUrl = "https://youtube.com/shorts/HHRDXEG1YCU?si=wsr0aAJG8BKYOvP5" // Replace with actual video URL
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Squats Video
        ExerciseVideoCard(
            videoTitle = "Squats Tutorial",
            videoUrl = "https://youtu.be/4KmY44Xsg2w?si=bHsAS2lSvesVvEQO" // Replace with actual video URL
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Burpees Video
        ExerciseVideoCard(
            videoTitle = "Burpees Tutorial",
            videoUrl = "https://youtu.be/xQdyIrSSFnE?si=HGbzrojHHXHqgAkz" // Replace with actual video URL
        )
    }
}

@Composable
fun ExerciseVideoCard(videoTitle: String, videoUrl: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = videoTitle,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ExerciseVideoScreen(videoUrl = videoUrl)
        }
    }
}

@Composable
fun ExerciseVideoScreen(videoUrl: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true // Enable DOM storage for YouTube
                settings.cacheMode = WebSettings.LOAD_DEFAULT // Use the default cache
//                settings.setAppCacheEnabled(true) // Enable caching

                // Configure the WebViewClient to handle video playback
                webViewClient = WebViewClient()
                loadUrl(videoUrl)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set video player height
    )
}



@Composable
fun ExerciseDetailsScreen() {
    var timeRemaining by remember { mutableStateOf(60) } // Initial countdown time in seconds
    var isRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val progress by derivedStateOf { timeRemaining / 60f } // Progress for the circular timer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Exercise Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Card for Exercise Information
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Name: Push-ups", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description: A basic exercise that works on the chest, shoulders, and triceps.", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Circular Timer Display
        Card(
            modifier = Modifier.size(200.dp),
            shape = RoundedCornerShape(100.dp), // Circular shape
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    progress = progress,
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 8.dp,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "$timeRemaining s",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Start/Stop Button
        Button(
            onClick = {
                if (!isRunning) {
                    isRunning = true
                    coroutineScope.launch {
                        while (timeRemaining > 0 && isRunning) {
                            delay(1000L) // Wait for 1 second
                            timeRemaining--
                        }
                        isRunning = false
                    }
                }
            },
            enabled = !isRunning, // Disable the button if the timer is already running
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(if (isRunning) "Running..." else "Start Timer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Button
        Button(
            onClick = {
                isRunning = false
                timeRemaining = 60 // Reset the timer to 60 seconds
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Reset Timer")
        }
    }
}


@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        // Add settings options such as notifications, reminders, theme preferences
        Text("Notification Settings: Enabled")
        Text("Theme: Light")
    }
}

@Composable
fun UserProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("User Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Name: Mouheb")
        Text("Goals: 100 Push-ups a day")
        // Add more profile information like achievements, badges, etc.
    }
}

@Preview(showBackground = true)
@Composable
fun FitnessAppPreview() {
    MyApplicationTheme {
        FitnessApp()
    }
}
