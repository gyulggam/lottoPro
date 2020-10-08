package com.example.lottopro.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.lottopro.Str.PatternSelNum
import com.example.lottopro.Str.PatternType

class PatternTypeSql(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        private const val DATABASE_VER  = 3
        private const val DATABASE_NAME = "LOTTOPATTERNTYPE.db"

        private const val TABLE_NAME = "PatternType"
        private const val LOTTO_ID   = "id"
        private const val LOTTO_NUM  = "type"
    }

    override fun onCreate(aDb: SQLiteDatabase?) {
        var sSql = ("CREATE TABLE $TABLE_NAME ($LOTTO_ID INTEGER PRIMARY KEY AUTOINCREMENT, $LOTTO_NUM TEXT)")
        aDb!!.execSQL(sSql)
    }

    override fun onUpgrade(aDb: SQLiteDatabase?, aOldVer: Int, aNewVer: Int) {
        aDb!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(aDb!!)
    }

    val gPatternType:List<PatternType>
        get() {
            val sLottoNum  = ArrayList<PatternType>()
            val sSqlHelper = "SELECT * FROM $TABLE_NAME"
            val sDb = this.writableDatabase
            val cursor = sDb.rawQuery(sSqlHelper, null)
            if (cursor.moveToFirst()) {
                do {
                    val sLotto = PatternType()
                    sLotto.id = cursor.getInt(cursor.getColumnIndex(LOTTO_ID))
                    sLotto.type = cursor.getString(cursor.getColumnIndex(LOTTO_NUM))

                    sLottoNum.add(sLotto)
                } while(cursor.moveToNext())
            }
            sDb.close()
            return sLottoNum
        }

    fun addPatternType(aLottoNum: PatternType) {
        val sDb = this.writableDatabase
        val sVal = ContentValues()
        sVal.put(LOTTO_NUM, aLottoNum.type)

        sDb.insert(TABLE_NAME, null, sVal)
        sDb.close()
    }

    fun upDatePatternType(aLottoNum: PatternType):Int {
        val sDb = this.writableDatabase
        val sVal = ContentValues()
        sVal.put(LOTTO_ID, aLottoNum.id)
        sVal.put(LOTTO_NUM, aLottoNum.type)

        return sDb.update(TABLE_NAME,sVal,"$LOTTO_ID=?", arrayOf(aLottoNum.id.toString()))
    }

    fun deletePatternType(aLottoNum: PatternType) {
        val sDb = this.writableDatabase

        sDb.delete(TABLE_NAME, "$LOTTO_ID=?", arrayOf(aLottoNum.id.toString()))
        sDb.close()
    }
}