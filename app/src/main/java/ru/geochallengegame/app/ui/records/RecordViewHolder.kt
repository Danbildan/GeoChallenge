package ru.geochallengegame.app.ui.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.geochallengegame.R
import ru.geochallengegame.app.game.Record

class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val pointsTv: TextView = itemView.findViewById(R.id.recordText)
    private val placeTv: TextView = itemView.findViewById(R.id.placeText)
    private val userTv: TextView = itemView.findViewById(R.id.userText)

    fun bind(record: Record, isMyRecord: Boolean) {
        if (record.order == 1)
            placeTv.setBackgroundResource(R.drawable.ic_gold_medal)

        if (record.order == 2)
            placeTv.setBackgroundResource(R.drawable.ic_silver_medal)

        if (record.order == 3)
            placeTv.setBackgroundResource(R.drawable.ic_bronze_medal)

        if (isMyRecord)
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.colorPrimaryDark
                )
            )

        pointsTv.text = record.score.toString()
        placeTv.text = record.order.toString()
        userTv.text = record.username
    }

    companion object {
        fun create(parent: ViewGroup): RecordViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.li_record, parent, false)
            return RecordViewHolder(view)
        }
    }

}