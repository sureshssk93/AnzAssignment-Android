package anz.project.data.repository

import anz.project.data.db.UserDao
import anz.project.data.network.ApiService
import anz.project.domain.model.User
import anz.project.domain.repository.UserRepository
import javax.inject.Inject

const val USER_NOT_FOUND = "User not found"

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getAllUsers(): List<User> {
        return try {
            val users = apiService.getUsers()
            userDao.insertUsers(users.map { it.toUserEntity() })
            users.map { it.toUser() }
        } catch (e: Exception) {
            userDao.getUsers().map {
                User(
                    id = it.id,
                    name = it.name,
                    company = it.company,
                    username = it.username,
                    email = it.email,
                    address = it.address,
                    zip = it.zip,
                    state = it.state,
                    country = it.country,
                    phone = it.phone,
                    photo = it.photo
                )
            }
        }
    }

    override suspend fun getUserDetailById(id: Int): User {
        return try {
            val user = apiService.getUserById(id)
            userDao.insertUsers(listOf(user.toUserEntity()))
            user.toUser()
        } catch (e: Exception) {
            userDao.getUserById(id)?.let {
                User(
                    id = it.id,
                    name = it.name,
                    company = it.company,
                    username = it.username,
                    email = it.email,
                    address = it.address,
                    zip = it.zip,
                    state = it.state,
                    country = it.country,
                    phone = it.phone,
                    photo = it.photo
                )
            } ?: throw Exception(USER_NOT_FOUND)
        }
    }
}