package com.amalip.testapp.domain.models

import android.os.Parcelable
import com.amalip.testapp.R
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class User(
    var id: Int = 0,
    var name: String = "",
    var age: Int = 0,
    var phone: Int = 0,
    var email: String = "",
    var relocation: Boolean = true,
    var image: Int = 0
) : Parcelable {

    fun setRandomImage() {
        image = when (Random.nextInt(1, 4)) {
            1 -> R.drawable.ic_random_1
            2 -> R.drawable.ic_random_2
            else -> R.drawable.ic_random_3
        }
    }

    fun changeRelocationAutomatically(): Boolean {
        val ranged = age !in 25..30
        relocation = ranged
        return ranged
    }

    fun validateName(): Boolean {
        val words = name.split(" ")
        if (words.count() < 4) return false
        if (words.any { it.length < 3 || it.length > 5 }) return false
        if (words[0].contains(Regex("[aoump]"))) return false
        if (words[1].contains(Regex("[^asdfghjklopuytem]"))) return false
        if (words[2].contains(Regex("[aeou]"))) return false
        if (!words[2].contains(Regex("[\\d]"))) return false
        if (!words[3].contains("t")) return false

        return true
    }

    fun validateAge(): Boolean {
        return age in 18..45
    }

    fun validateEmail(): Boolean {
        if (!email.contains(Regex("^[\\w_\\-.+]+@([\\w_\\-.+]+\\.)+(com|net|mx+\\.)+[\\w_\\-.+]*\$"))) return false

        val beforeAt = email.substringBefore("@")
        if (beforeAt.length > 3 && beforeAt.substring(0, 3)
                .contains(Regex("[_\\-.+]"))
        ) return false
        if (beforeAt.length > 20 && beforeAt.substring(19, beforeAt.length)
                .contains(Regex("[_\\-.+]"))
        ) return false

        var afterAtBeforeDot = email.substringAfter("@")
        afterAtBeforeDot = afterAtBeforeDot.substringBeforeLast(".com.")
        afterAtBeforeDot = afterAtBeforeDot.substringBeforeLast(".net.")
        afterAtBeforeDot = afterAtBeforeDot.substringBeforeLast(".mx.")
        if (afterAtBeforeDot.length > 4) {
            if (afterAtBeforeDot.length >= 10) {
                if (afterAtBeforeDot.substring(4, 10).contains(Regex("[_\\-.+]"))) return false
            } else if (afterAtBeforeDot.substring(4, afterAtBeforeDot.length)
                    .contains(Regex("[_\\-.+]"))
            ) return false
        }

        var afterDot = email.substringAfter(".com.")
        afterDot = afterDot.substringAfter(".net.")
        afterDot = afterDot.substringAfter(".mx.")
        if (afterDot.length >= 2)
            if (afterDot.length >= 3) {
                if (afterDot.substring(1, 3).contains(Regex("[_\\-.+]"))) return false
            } else if (afterDot.substring(1, 2).contains(Regex("[_\\-.+]"))) return false

        return true
    }

    fun validatePhone(): Boolean {
        return phone.toString().length == 10
    }

    companion object {

        fun getRandomUser(): User {

            val random = Random.nextInt(1, 4)

            val name = getRandomName()

            val age = Random.nextInt(18, 45)

            val phone: Int = when (random) {
                1 -> 1234567890
                2 -> 2134567890
                else -> 1234567899
            }

            val email = when (random) {
                1 -> "test_random@gmail.com.mx"
                2 -> "test.random@gmail.com._mx"
                else -> "test-random@gmail.com._mx_"
            }

            val user = User(
                name = name,
                age = age,
                phone = phone,
                email = email,
                relocation = getRelocationState(age)
            )

            user.setRandomImage()

            return user

        }

        private fun getRelocationState(age: Int) = age !in 25..30

        private fun getRandomName(): String {
            var random = Random.nextInt(1, 3)
            //Not valid letters AOUMP
            val word1 = when (random) {
                1 -> "Ely"
                2 -> "Lizz"
                else -> "Tite"
            }

            random = Random.nextInt(1, 3)
            //Valid letters asdfghjklopuytem
            val word2 = when (random) {
                1 -> "dad"
                2 -> "sad"
                else -> "poy"
            }

            random = Random.nextInt(1, 3)
            //Not valid letters a,e,o,u and include at least 1 number
            val word3 = when (random) {
                1 -> "R2D2"
                2 -> "D10s"
                else -> "Pin0"
            }

            random = Random.nextInt(1, 3)
            //One t lowercase
            val word4 = when (random) {
                1 -> "Sate"
                2 -> "Putin"
                else -> "Groot"
            }

            return "$word1 $word2 $word3 $word4"
        }
    }
}