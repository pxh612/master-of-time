package com.example.master_of_time.module.dailyday

import com.example.master_of_time.database.table.DdEvent
import timber.log.Timber

class DdEventListSorter {

    companion object{
        val SORT_BY_DATE_CREATED = 0
        val SORT_BY_DATE = 1
        val SORT_BY_NEAREST_EVENT = 2
        val SORT_BY_ALPHABET = 3
    }

    private var sortMethodDefault = SORT_BY_DATE
    private var sortMethod = sortMethodDefault

    private var isReverseFlag = false


    fun sort(list: List<DdEvent>): List<DdEvent> {
        var resultList = list
        when(sortMethod){
            SORT_BY_DATE_CREATED -> {
                Timber.v("sort by date created")
                resultList = resultList.sortedBy { it.id }
            }
            SORT_BY_DATE -> {
                Timber.v("sort by target date")
                resultList = resultList.sortedBy {
                    val ddEventCalculation = DdEventCalculation(it.calculationTypeId, it.date)
                    ddEventCalculation.targetDate
                }

            }
            SORT_BY_NEAREST_EVENT -> {
                Timber.v("sort by nearest")
                resultList = resultList.sortedBy {
                    val ddEventCalculation = DdEventCalculation(it.calculationTypeId, it.date)
                    ddEventCalculation.getDayDistanceFromPresent()
                }
            }
            SORT_BY_ALPHABET -> {
                Timber.v("sort by alphabet")
                resultList = resultList.sortedBy { it.title }
            }
        }
        return resultList
    }

    fun cycleSortMethod() {
        sortMethod = when(sortMethod){
            SORT_BY_DATE -> SORT_BY_ALPHABET
            SORT_BY_ALPHABET -> SORT_BY_DATE
            else -> sortMethodDefault
        }
    }
}