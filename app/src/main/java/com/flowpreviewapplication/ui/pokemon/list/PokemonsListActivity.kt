package com.flowpreviewapplication.ui.pokemon.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.flowpreviewapplication.R
import com.flowpreviewapplication.domain.model.Pokemon
import com.flowpreviewapplication.ui.pokemon.list.model.PokemonsListResult
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonsListActivity : AppCompatActivity() {

    private val pokemonsListViewModel: PokemonsListViewModel by viewModel()

    private lateinit var adapter: PokemonsListAdapter
    private val listener = object : PokemonsListAdapter.Listener {

        override fun onItemClicked(item: Pokemon) {
            pokemonsListViewModel.onPokemonSelected(item)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        setupItemsUI()
        setupListeners()
        setupObservers()
    }

    private fun setupItemsUI() {
        adapter = PokemonsListAdapter(listener = listener)
        pokemons_list_items_rv.also { rv ->
            rv.adapter = adapter
        }
    }

    private fun setupListeners() {
        pokemons_list_catch_pokemon_button.setOnClickListener {
            pokemonsListViewModel.onCatchPokemonAction()
        }
        pokemons_list_release_all_pokemons_button.setOnClickListener {
            pokemonsListViewModel.onReleaseAllPokemonsAction()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted{


        }
        pokemonsListViewModel.pokemonsListLiveData.observe(this,
            Observer { result ->
                handlePokemonListResult(result)
            })
    }

    private fun handlePokemonListResult(result: PokemonsListResult) {
        when (result) {
            is PokemonsListResult.Loading -> {
                showOrHideProgress(visible = true)
            }
            is PokemonsListResult.Success -> {
                showOrHideProgress(visible = false)
                adapter.updateItems(result.items)
                Toast.makeText(this, R.string.pokemons_list_updated, Toast.LENGTH_SHORT)
                    .show()
            }
            is PokemonsListResult.Error -> {
                showOrHideProgress(visible = false)
                Toast.makeText(this, result.exception.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showOrHideProgress(visible: Boolean) {
        pokemons_list_progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
    }

}
