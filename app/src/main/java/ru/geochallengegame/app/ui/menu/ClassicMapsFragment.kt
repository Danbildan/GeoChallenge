package ru.geochallengegame.app.ui.menu


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import ru.geochallengegame.R

class ClassicMapsFragment : BaseMapsFragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MenuActivity).supportActionBar?.title = getString(R.string.solo_title)
        (activity as MenuActivity).supportActionBar?.subtitle = getString(R.string.solo_subtitle)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun getLayout(): Int {
        return R.layout.fr_menu_maps
    }
}