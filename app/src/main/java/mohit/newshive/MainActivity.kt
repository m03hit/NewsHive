package mohit.newshive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import mohit.newshive.data.repository.NewsRepository
import mohit.newshive.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var newsRepository: NewsRepository
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        //val appBarConfiguration = AppBarConfiguration(
        //    setOf(
        //        R.id.topNewsFragment,R.id.navigation_search_news
        //    )
        //)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.setupFragment||destination.id==R.id.newsViewFragment) {

                navView.visibility = View.GONE
            } else {

                navView.visibility = View.VISIBLE
            }
        }
        navView.setupWithNavController(navController)
    }
}