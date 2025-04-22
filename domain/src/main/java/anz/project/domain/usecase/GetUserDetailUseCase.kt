package anz.project.domain.usecase

import anz.project.domain.repository.UserRepository
import anz.project.domain.model.User
import javax.inject.Inject


class GetUserDetailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int): User {
        return repository.getUserDetailById(id)
    }
}