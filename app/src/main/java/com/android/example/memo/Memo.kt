package com.android.example.memo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Memo : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var means: String = ""
}