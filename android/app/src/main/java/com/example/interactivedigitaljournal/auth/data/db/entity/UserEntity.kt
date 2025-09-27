package com.example.interactivedigitaljournal.auth.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.interactivedigitaljournal.auth.domain.models.User
import com.example.interactivedigitaljournal.auth.domain.models.UserRole

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val surname: String,
    val patronymic: String,
    val firstName: String,
    val role: UserRole,
) {
    companion object {
        fun fromDomain(user: User) =
            UserEntity(
                -1,
                user.email,
                user.surname,
                user.patronymic,
                user.firstName,
                user.role,
            )
    }
    fun toDomain() =
        User(
            email,
            surname,
            patronymic,
            firstName,
            role,
        )
}