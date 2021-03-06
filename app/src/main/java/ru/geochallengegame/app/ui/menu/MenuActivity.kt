package ru.geochallengegame.app.ui.menu


import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import ru.geochallengegame.R
import ru.geochallengegame.app.AppDelegate
import ru.geochallengegame.app.di.menu.MenuComponent
import ru.geochallengegame.app.game.GameInfo
import ru.geochallengegame.app.game.GameMap
import ru.geochallengegame.app.ui.game.classic.ClassicGameActivity
import ru.geochallengegame.app.ui.game.hundred.HungredGameActivity
import ru.geochallengegame.app.ui.game.immortal.ImmortalGameActivity
import ru.geochallengegame.app.ui.game.time.TimeLimitGameActivity
import ru.geochallengegame.app.ui.menu.vm.MenuMapsViewModel
import ru.geochallengegame.app.ui.records.RecordsActivity
import ru.geochallengegame.app.ui.splash.SplashActivity
import javax.inject.Inject

class MenuActivity : AppCompatActivity(),
    OnClickMapListener, SignOutable {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var blocked = false

    @Inject
    lateinit var viewModel: MenuMapsViewModel

    var menuComponent: MenuComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        menuComponent = (applicationContext as AppDelegate)
            .userComponent
            ?.menuComponent()
            ?.create(this)

        menuComponent?.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.ac_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        findNavController(R.id.nav_host_fragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                val mode = getCurrentMode(destination)
                if (mode != null) {
                    viewModel.selectMode(mode)
                }
            }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_solo,
                R.id.nav_time,
                R.id.nav_immortal,
                R.id.nav_hungred,
                R.id.nav_settings,
                R.id.nav_about,
                R.id.nav_signout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val actionBarDrawerToggle: ActionBarDrawerToggle = object :
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.app_name,
                R.string.app_name
            ) {}

        drawerLayout.setScrimColor(Color.TRANSPARENT)
        drawerLayout.drawerElevation = 0f
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        blocked = false

        viewModel.errorIsVisible.observe(
            this,
            Observer {
                if (it) Snackbar
                    .make(
                        findViewById(R.id.drawer_layout),
                        R.string.menu_error,
                        Snackbar.LENGTH_LONG
                    )
                    .show()
            }
        )

        viewModel.isSignOut.observe(
            this,
            Observer {
                if (it)
                    exitFromProfile()
            }
        )
        viewModel.gameInfoIsVisible.observe(
            this,
            Observer {
                if (it) showInfoDialog()
            })

    }

    override fun onRestart() {
        super.onRestart()
        blocked = false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showInfoDialog() {
        MessageDialog()
            .show(supportFragmentManager, "MessageDialog")

    }

    override fun onPause() {
        super.onPause()
        val dialog = supportFragmentManager
            .findFragmentByTag("MessageDialog") as? MessageDialog ?: return
        supportFragmentManager.beginTransaction().remove(dialog).commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        menuComponent = null
    }

    override fun signOut() {
        viewModel.logout()
    }

    private fun exitFromProfile() {
        (application as AppDelegate).destroyUserComponent()
        splash()
        finish()
    }
    private fun splash() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }

    override fun onClickGameMap(map: GameMap, lang: String) {
        if (blocked) return
        blocked = true
        val mode = getCurrentMode() ?: return
        startGame(map, mode, lang)
    }

    override fun onClickLeaderboard(map: GameMap, lang: String) {
        if (blocked) return
        blocked = true
        val mode = getCurrentMode() ?: return
        startRecords(mode, map, lang)
    }

    private fun startGame(map: GameMap, mode: String, lang: String) {
        val intent = when (mode) {
            "solo" -> Intent(this, ClassicGameActivity::class.java)
            "time" -> Intent(this, TimeLimitGameActivity::class.java)
            "immortal" -> Intent(this, ImmortalGameActivity::class.java)
            "fatal100" -> Intent(this, HungredGameActivity::class.java)
            else -> null
        } ?: return
        startActivity(intent)
        createGameComponent(mode, map, lang)
    }

    private fun startRecords(mode: String, map: GameMap, lang: String) {
        createGameComponent(mode, map, lang)
        startActivity(Intent(this, RecordsActivity::class.java))
    }

    private fun createGameComponent(mode: String, map: GameMap, lang: String) {
        val gameInfo = getGameInfo(mode, map.id, lang)
        (applicationContext as AppDelegate)
            .createGameComponent(gameInfo, map)
    }

    fun getCurrentMode(): String? {
        return getCurrentMode(findNavController(R.id.nav_host_fragment).currentDestination)
    }

    private fun getCurrentMode(distenation: NavDestination?): String? {
        return when (distenation?.id) {
            R.id.nav_solo -> "solo"
            R.id.nav_time -> "time"
            R.id.nav_immortal -> "immortal"
            R.id.nav_hungred -> "fatal100"
            else -> null
        }
    }

    private fun getGameInfo(mode: String, mapId: Int, lang: String) = GameInfo(mode, mapId, 5, lang)

    class MessageDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val context =
                (activity as? MenuActivity) ?: return super.onCreateDialog(savedInstanceState)
            val mode = context.getCurrentMode()
            val message = getInfoMessage(mode)
            return AlertDialog.Builder(context, R.style.DialogTheme)
                .setMessage(message)
                .setPositiveButton(R.string.ok) { _, _ ->
                    context.viewModel.iReadModeInfo()
                }
                .create()
        }

        override fun onCancel(dialog: DialogInterface) {
            super.onCancel(dialog)
            (context as MenuActivity).viewModel.iReadModeInfo()
        }

        private fun getInfoMessage(mode: String?): String? {
            return when (mode) {
                "solo" -> resources.getString(R.string.about_solo)
                "time" -> resources.getString(R.string.about_time)
                "fatal100" -> resources.getString(R.string.about_fatal)
                "immortal" -> resources.getString(R.string.about_immortal)
                else -> null
            }
        }
    }

}