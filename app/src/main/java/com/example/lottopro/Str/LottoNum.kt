package com.example.lottopro.Str

import org.json.JSONObject

class LottoNum {
    var id     : Int = 0
    var number : String? = null

    constructor(){}

    constructor(aId : Int, aNumber : String) {
        this.id     = aId
        this.number = aNumber
    }
}

class PatternSelNum {
    var id     : Int = 0
    var number : String? = null

    constructor(){}

    constructor(aId : Int, aNumber : String) {
        this.id     = aId
        this.number = aNumber
    }
}

class PatternType {
    var id     : Int = 0
    var type   : String = ""

    constructor(){}

    constructor(aId : Int, aType : String) {
        this.id     = aId
        this.type   = aType
    }
}