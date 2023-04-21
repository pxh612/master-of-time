package com.example.master_of_time.module.dailyday

import com.example.master_of_time.database.table.DdEvent

class DdEventListSorter {

    companion object{
        val SORT_BY_DATE_CREATED = 0
        val SORT_BY_TARGET_DATE = 1
        val SORT_BY_NEAREST_EVENT = 2
        val SORT_BY_ALPHABET = 3

        val DEFAULT_SORT_METHOD = SORT_BY_TARGET_DATE
    }

    var sortMethod = DEFAULT_SORT_METHOD

    private var isReverseFlag = false


    fun sort(list: List<DdEvent>): List<DdEvent> {
        var resultList = list
        when(sortMethod){
            SORT_BY_DATE_CREATED -> {
                resultList = resultList.sortedBy { it.id }
            }
            SORT_BY_TARGET_DATE -> {
                resultList = resultList.sortedBy {
                    val ddEventCalculation = DdEventCalculation(it.calculationTypeId, it.date)
                    ddEventCalculation.targetDate
                }

            }
            SORT_BY_NEAREST_EVENT -> {
                resultList = resultList.sortedBy {
                    val targetDate = DdEventCalculation(it.calculationTypeId, it.date).targetDate
                    DdEventCalculation(targetDate).getDayDistanceFromPresentAbsolute()
                }
            }
            SORT_BY_ALPHABET -> {
                resultList = resultList.sortedBy { it.title }
            }
        }
        return resultList
    }

    fun cycleSortMethod() {
        sortMethod = when(sortMethod){
            SORT_BY_TARGET_DATE -> SORT_BY_ALPHABET
            SORT_BY_ALPHABET -> SORT_BY_TARGET_DATE
            else -> DEFAULT_SORT_METHOD
        }
    }
}