package anz.project.domain.repository

import anz.project.domain.model.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserDetailById(id: Int): User
}