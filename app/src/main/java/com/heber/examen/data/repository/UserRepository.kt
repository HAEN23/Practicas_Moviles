package com.heber.examen.data.repository

import com.heber.examen.data.dao.UserDao
import com.heber.examen.data.Class.User
import kotlinx.coroutines.flow.Flow
class UserRepository(
    private val userDao: UserDao
) {
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun getUserById(id: Int): User? = userDao.getUserById(id)

    suspend fun insertUser(user: User): Long = userDao.insertUser(user)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    suspend fun deleteAllUsers() = userDao.deleteAllUsers()
}
