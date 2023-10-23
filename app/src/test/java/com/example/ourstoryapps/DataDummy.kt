package com.example.ourstoryapps

import com.example.ourstoryapps.data.model.ListStoryItem

class DataDummy {


    companion object {
        fun generateDummyQuoteResponse(): List<ListStoryItem> {
            val items: MutableList<ListStoryItem> = arrayListOf()
            for (i in 0..100) {
                val quote = ListStoryItem(
                    i.toString(),
                    "author + $i",
                    "quote $i",
                )
                items.add(quote)
            }
            return items
        }
    }
}