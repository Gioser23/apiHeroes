import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiheroes.databinding.ItemheroesBinding
import com.example.superheroapp.modelo.interfaces.Heroes

class AdapterHeroes(
    private var heroes: MutableList<Heroes>?,
    private val onDeleteClickListener: OnDeleteClickListener,
    private val onAddClickListener: OnAddClickListener,
    private val actionFun: (heroe: Heroes, action: ACTION) -> Unit
) : RecyclerView.Adapter<AdapterHeroes.ViewHolder>() {

    enum class ACTION {
        DELETE,
        UPDATE
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(heroId: String)
    }

    interface OnAddClickListener {
        fun onAddButtonClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemheroesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = heroes?.get(position)
        hero?.let {
            holder.bind(it)
        }

        holder.binding.button14.setOnClickListener {
            actionFun.invoke(hero!!, ACTION.DELETE)
            heroes!!.remove(hero)
            notifyDataSetChanged()
        }

        holder.binding.button15.setOnClickListener {
            onAddClickListener.onAddButtonClick()
        }
    }

    override fun getItemCount(): Int {
        return heroes?.size ?: 0
    }

    fun actualizarLista(nuevaLista: MutableList<Heroes>?) {
        heroes = nuevaLista
        notifyDataSetChanged()
    }

    fun eliminarHeroePorId(id: String) {
        heroes?.removeIf { it.id.toString() == id }
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemheroesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Heroes) {
            binding.apply {
                textView.text = hero.id.toString()
                textView2.text = hero.nombre_heroe
                textView3.text = hero.nivel.toString()
            }
        }
    }
}









