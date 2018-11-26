package com.musasyihab.footballclub2.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.musasyihab.footballclub2.R
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.util.Constants.TYPE_NEXT
import com.musasyihab.footballclub2.util.Constants.TYPE_PAST
import com.musasyihab.footballclub2.util.Helper

class MatchListAdapter(private val mList: ArrayList<EventModel>,
                        context: Context, adapterListener: MatchListAdapter.Listener) : RecyclerView.Adapter<MatchListAdapter.ItemHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val adapterListener: MatchListAdapter.Listener = adapterListener
    private val listener: MatchListHolderListener

    init {
        listener = MatchListHolderListener()
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: MatchListAdapter.ItemHolder, position: Int) {
        val item = mList[position]
        val prevItem = if (position == 0) item else mList[position-1]
        val type = if (!Helper.isPastEvent(item)) TYPE_NEXT else TYPE_PAST
        val prevType = if (!Helper.isPastEvent(prevItem)) TYPE_NEXT else TYPE_PAST
        holder.bindItem(item, type, prevType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListAdapter.ItemHolder {
        val inflatedView = mInflater.inflate(R.layout.match_item_layout, parent,false)
        return ItemHolder(inflatedView, listener)
    }

    interface Listener {
        fun onItemClicked(item: EventModel, position: Int)
    }

    class ItemHolder(v: View, private var listener: Listener) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var item: EventModel? = null

        var label: TextView
        var homeClub: TextView
        var homeScore: TextView
        var awayClub: TextView
        var awayScore: TextView
        var matchDate: TextView
        var matchTime: TextView
        var matchLayout: ConstraintLayout

        init {
            label = view.findViewById(R.id.matchItemHomeLabel) as TextView
            homeClub = view.findViewById(R.id.matchItemHomeClub) as TextView
            homeScore = view.findViewById(R.id.matchItemHomeScore) as TextView
            awayClub = view.findViewById(R.id.matchItemAwayClub) as TextView
            awayScore = view.findViewById(R.id.matchItemAwayScore) as TextView
            matchDate = view.findViewById(R.id.matchItemDate) as TextView
            matchTime = view.findViewById(R.id.matchItemTime) as TextView
            matchLayout = view.findViewById(R.id.matchItemHomeLayout) as ConstraintLayout
        }

        fun bindItem(item: EventModel, type: String, prevType: String) {
            this.item = item
            if(adapterPosition == 0 || type != prevType) {
                if(type == TYPE_PAST)
                    label.text = view.context.getString(R.string.previous_matches)
                else
                    label.text = view.context.getString(R.string.next_matches)
                label.visibility = View.VISIBLE
            } else {
                label.visibility = View.GONE
            }

            homeClub.text = item.strHomeTeam
            homeScore.text = item.intHomeScore
            awayClub.text = item.strAwayTeam
            awayScore.text = item.intAwayScore
            matchDate.text = Helper.formatToReadableDate(item.dateEvent)
            matchTime.text = Helper.formatToReadableTime(item.strTime)

            matchLayout.setOnClickListener { listener.onItemClicked(item, adapterPosition) }

        }

        interface Listener {
            fun onItemClicked(item: EventModel, position: Int)
        }

    }

    private inner class MatchListHolderListener : ItemHolder.Listener {

        override fun onItemClicked(item: EventModel, position: Int) {
            adapterListener.onItemClicked(item, position)
        }
    }

}