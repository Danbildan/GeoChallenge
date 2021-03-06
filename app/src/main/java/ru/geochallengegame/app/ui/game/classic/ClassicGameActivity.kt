package ru.geochallengegame.app.ui.game.classic

import android.os.Bundle
import ru.geochallengegame.R
import ru.geochallengegame.app.AppDelegate
import ru.geochallengegame.app.ui.game.BaseGameMapActivity
import ru.geochallengegame.app.ui.game.BaseGameViewModel
import javax.inject.Inject

open class ClassicGameActivity : BaseGameMapActivity() {

    @Inject
    lateinit var fragment: ClassicGameInfoFragment

    @Inject
    lateinit var viewModel: ClassicGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.game_info_container, fragment)
            .commit()
    }

    private fun inject() {
        activityComponent = (application as AppDelegate)
            .gameComponent
            ?.gameActivityComponent()
            ?.create(this)
        activityComponent?.inject(this)
    }

    override fun getViewModel(): BaseGameViewModel {
        return viewModel
    }

}