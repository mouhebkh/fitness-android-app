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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.TopAppBarDefaults
import com.mouheb.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch


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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Mouheb Fitness!")
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate("workouts") },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("View Workouts")
        }
        Button(
            onClick = { navController.navigate("tracking") },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Track Your Progress")
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
        Text("Workout List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        WorkoutCard("Push-ups", "Great for upper body strength.") {
            navController.navigate("exerciseDetails")
        }
        WorkoutCard("Squats", "Strengthen your legs and core.") {
            navController.navigate("exerciseDetails")
        }
        WorkoutCard("Burpees", "Full-body workout that burns calories.") {
            navController.navigate("exerciseDetails")
        }
    }
}

@Composable
fun WorkoutCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
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
        Text("Track Your Progress", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ProgressCard("Push-ups", "20 reps today")
        ProgressCard("Squats", "15 reps today")
        ProgressCard("Burpees", "10 reps today")
        DailyGoalProgressBar(current = 50, goal = 100) // Example of a progress bar
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

//@Composable
//fun ExerciseDetailsScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text("Exercise Details", style = MaterialTheme.typography.headlineMedium)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text("Name: Push-ups")
//        Text("Description: A basic exercise that works on the chest, shoulders, and triceps.")
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { /* Implement timer functionality */ }) {
//            Text("Start Timer")
//        }
//    }
//}


@Composable
fun ExerciseDetailsScreen() {
    var timeRemaining by remember { mutableStateOf(60) } // Initial countdown time in seconds
    var isRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Exercise Details", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Name: Push-ups")
        Text("Description: A basic exercise that works on the chest, shoulders, and triceps.")
        Spacer(modifier = Modifier.height(16.dp))

        // Timer display
        Text(
            text = "Time Remaining: ${timeRemaining}s",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            enabled = !isRunning // Disable the button if the timer is already running
        ) {
            Text(if (isRunning) "Running..." else "Start Timer")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Reset Button
        Button(
            onClick = {
                isRunning = false
                timeRemaining = 60 // Reset the timer to 60 seconds
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
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
