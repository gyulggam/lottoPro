package com.example.lottopro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.lottopro.Str.LottoNum

class SqlHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        private const val DATABASE_VER  = 1
        private const val DATABASE_NAME = "LOTTONUMSAVE.db"

        private const val TABLE_NAME = "LottoNum"
        private const val LOTTO_ID   = "id"
        private const val LOTTO_NUM  = "number"
    }

    override fun onCreate(aDb: SQLiteDatabase?) {
        var sSql = ("CREATE TABLE $TABLE_NAME ($LOTTO_ID INTEGER PRIMARY KEY AUTOINCREMENT, $LOTTO_NUM TEXT)")
        aDb!!.execSQL(sSql)
    }

    override fun onUpgrade(aDb: SQLiteDatabase?, aOldVer: Int, aNewVer: Int) {
        aDb!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(aDb!!)
    }

    val gAllLottoNum:List<LottoNum>
        get() {
            val sLottoNum  = ArrayList<LottoNum>()
            val sSqlHelper = "SELECT * FROM $TABLE_NAME"
            val sDb = this.writableDatabase
            val cursor = sDb.rawQuery(sSqlHelper, null)
            if (cursor.moveToFirst()) {
                do {
                    val sLotto = LottoNum()
                    sLotto.id = cursor.getInt(cursor.getColumnIndex(LOTTO_ID))
                    sLotto.number = cursor.getString(cursor.getColumnIndex(LOTTO_NUM))

                    sLottoNum.add(sLotto)
                } while(cursor.moveToNext())
            }
            sDb.close()
            return sLottoNum
        }

    fun addLottoNum(aLottoNum: LottoNum) {
        val sDb = this.writableDatabase
        val sVal = ContentValues()
        sVal.put(LOTTO_NUM, aLottoNum.number)

        sDb.insert(TABLE_NAME, null, sVal)
        sDb.close()
    }

    fun upDateLottoNum(aLottoNum: LottoNum):Int {
        val sDb = this.writableDatabase
        val sVal = ContentValues()
        sVal.put(LOTTO_ID, aLottoNum.id)
        sVal.put(LOTTO_NUM, aLottoNum.number)

        return sDb.update(TABLE_NAME,sVal,"$LOTTO_ID=?", arrayOf(aLottoNum.id.toString()))
    }

    fun deleteLottoNum(aLottoNum: LottoNum) {
        val sDb = this.writableDatabase

        println("aLottoNum.id ${aLottoNum.id}")
        sDb.delete(TABLE_NAME, "$LOTTO_ID=?", arrayOf(aLottoNum.id.toString()))
        sDb.close()
    }
}