package com.minimize.android.rxrecycleradapter

class AdapterChanges<DataType> {
    var list: List<DataType> = ArrayList()
    var effectedItem: Int = ALL_ITEMS_EFFECTED
    var transactionType = TransactionTypes.REPLACE_ALL

    companion object {
        const val ALL_ITEMS_EFFECTED = -1
    }

    enum class TransactionTypes {
        REPLACE_ALL,
        DELETE,
        MODIFY,
        ADD
    }
}
