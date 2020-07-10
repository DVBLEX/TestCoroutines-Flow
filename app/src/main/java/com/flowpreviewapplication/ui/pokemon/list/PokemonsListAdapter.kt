package com.flowpreviewapplication.ui.pokemon.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.flowpreviewapplication.R
import com.flowpreviewapplication.domain.model.Pokemon
import kotlinx.android.synthetic.main.item_pokemon.view.*

class PokemonsListAdapter(
    private var items: List<Pokemon> = emptyList(),
    private val listener: Listener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
            )
            else -> throw IllegalArgumentException("ViewHolder not supported")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE
    }

    override fun getItemId(position: Int): Long {
        return items[position].id ?: position.toLong()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_VIEW_TYPE -> {
                val viewHolder = holder as ItemViewHolder
                viewHolder.bind(items[position], listener)
            }
            else -> throw IllegalArgumentException("ViewHolder not supported")
        }
    }

    fun updateItems(items: List<Pokemon>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: Pokemon, listener: Listener?) = with(itemView) {
            item_pokemon_name.text = item.name
            item_pokemon_level.text = context.getString(R.string.pokemon_level, item.level)
            item_pokemon_species.text = item.species?.speciesName ?: ""
            item_pokemon_image.load(item.species?.imageUrl)
            setOnClickListener {
                listener?.onItemClicked(item)
            }
        }

    }

    interface Listener {

        fun onItemClicked(item: Pokemon)

    }

    companion object {
        private const val ITEM_VIEW_TYPE = 1
    }

}