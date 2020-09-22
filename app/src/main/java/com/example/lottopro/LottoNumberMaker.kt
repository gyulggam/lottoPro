package com.example.lottopro

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

}