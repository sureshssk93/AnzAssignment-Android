package anz.project.data.network

import anz.project.data.db.UserEntity
import anz.project.domain.model.User


data class UserDto(
    val id: Int,
    val name: String,
    val company: String,
    val username: String,
    val email: String,
    val address: String,
    val zip: String,
    val state: String,
    val country: String,
    val phone: String,
    val photo: String,
) {
    fun toUserEntity() = UserEntity(
        id = id,
        name = name,
        company = company,
        username = username,
        email = email,
        address = address,
        zip = zip,
        state = state,
        country = country,
        phone = phone,
        photo = photo
    )

    fun toUser() = User(
        id = id,
        name = name,
        company = company,
        username = username,
        email = email,
        address = address,
        zip = zip,
        state = state,
        country = country,
        phone = phone,
        photo = photo
    )
}