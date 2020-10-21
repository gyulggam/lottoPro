package com.example.lottopro

import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.util.*

object LottoNumberMaker {

//    랜덤으로 추출하여 6개의 로또 번호를 만드는 함수
    fun getRandomLottoNumbers(): MutableList<Int> {
//        무작위로 생성된 로또 번호를 저장할 가변 리스트 생성
         val sLottoNumbers = mutableListOf<Int>()

//        6번 반복하는 for 문
        for (i in 1..6){
//            랜덤한 번호를 임시로 저장할 변수를 생성
            var sNumber = 0
            do {
//                랜덤한 번호를 추출해 number 변수에 저장
                  sNumber = getRandLottoNumber()

//                sLottoNumbers 에 sNumber 변수의 값이 없을때까지 반복
            } while (sLottoNumbers.contains(sNumber))

//            이미 뽑은 리스트에 없는 번호가 나올때까지 반복했으므로 중복이 없는 상태
//            추출된 번호를 뽑은 리스트에 추가
            sLottoNumbers.add(sNumber)
        }

        sLottoNumbers.sort()
        return sLottoNumbers
    }

//     랜덤으로 1 ~ 45 번호 중 하나의 번호를 생성하는 함수
    fun getRandLottoNumber(): Int {
        return Random().nextInt(45) + 1
    }


    //    shuffle 을 사용해 로또 번호 생성
    fun getShuffleLottoNumbers(): MutableList<Int> {

        val sList = mutableListOf<Int>()

        for(number in 1..45){
            sList.add(number)
        }

        sList.shuffle()


        return sList.subList(0, 6)
    }

    fun getNumber(): ArrayList<String>? {
        var arrayList: ArrayList<String>?
        arrayList = ArrayList()
        try {
            val doc: Document = Jsoup.connect("https://dhlottery.co.kr/common.do?method=main").get()
            var contents: Elements
            contents = doc.select("#lottoDrwNo")
//            arrayList.add(contents.text()) // 로또당첨 횟수
            for (i in 1..6) {
                contents = doc.select("#drwtNo$i")
                arrayList.add(contents.text()) // 당첨번호 1번 ~ 6번
                if(i == 6){
                    arrayList.add("0")
                }
            }
            contents = doc.select("#bnusNo")
            arrayList.add(contents.text()) // 보너스 번호
        } catch (e: IOException) {
            //e.printStackTrace();
//            Log.d("LottoNum의 getNumber()함수 에러 : ", e.getMessage())
        }
        return arrayList
    }

    fun getPreNumber(i: String?): ArrayList<String>? {
        var arrayList: ArrayList<String>?
        arrayList = ArrayList()
        try {

            val doc: Document = Jsoup.connect("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo="+i).get()
            Log.d("doc text:::::::::::::::::::", doc.text()  )

            val docString = doc.text()
            val docJson = JSONObject(docString)

//            var contents: Elements
            for (i in 1..6) {
                var contents = docJson.get("drwtNo$i")
//                contents = doc.select("#drwtNo$i")
                arrayList.add(contents.toString()) // 당첨번호 1번 ~ 6번
            }

        } catch (e: IOException) {
            //e.printStackTrace();
//            Log.d("LottoNum의 getNumber()함수 에러 : ", e.getMessage())
        }
        return arrayList
    }

    fun getPreNumberBonus(i: String?): ArrayList<String>? {
        var arrayList: ArrayList<String>?
        arrayList = ArrayList()
        try {
            val doc: Document = Jsoup.connect("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo="+i).get()

            val docString = doc.text()
            val docJson = JSONObject(docString)

            var contents = docJson.get("bnusNo")
            arrayList.add(contents.toString()) // 보너스 번호
        } catch (e: IOException) {
            //e.printStackTrace();
//            Log.d("LottoNum의 getNumber()함수 에러 : ", e.getMessage())
        }
        return arrayList
    }

    fun getPreCount(i: String?): ArrayList<String>? {
        var arrayList: ArrayList<String>?
        arrayList = ArrayList()
        try {
            val doc: Document = Jsoup.connect("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo="+i).get()

            val docString = doc.text()
            val docJson = JSONObject(docString)

            var contents = docJson.get("drwNo")
            arrayList.add(contents.toString()) // 보너스 번호
        } catch (e: IOException) {
            //e.printStackTrace();
//            Log.d("LottoNum의 getNumber()함수 에러 : ", e.getMessage())
        }
        return arrayList
    }

    fun getCount(): String? {
        var count =""
        try {
            val doc: Document = Jsoup.connect("https://dhlottery.co.kr/common.do?method=main").get()
            var contents: Elements
            contents = doc.select("#lottoDrwNo")
            count =contents.text() // 로또당첨 횟수

        } catch (e: IOException) {
            //e.printStackTrace();
//            Log.d("LottoNum의 getNumber()함수 에러 : ", e.getMessage())
        }
        return count
    }

    fun getDate(): String {
        var date =""
        try{
            val doc: Document = Jsoup.connect("https://dhlottery.co.kr/common.do?method=main").get()
            var contents: Elements
            contents = doc.select("#drwNoDate")
            date = contents.text()
        } catch (e: IOException) {
            //e.printStackTrace();
//            Log.d("LottoNum의 getNumber()함수 에러 : ", e.getMessage())
        }
        return date
    }
}